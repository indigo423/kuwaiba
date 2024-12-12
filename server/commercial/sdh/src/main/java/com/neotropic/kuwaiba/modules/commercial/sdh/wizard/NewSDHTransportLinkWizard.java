/*
 *  Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://apache.org/licenses/LICENSE-2.0.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.neotropic.kuwaiba.modules.commercial.sdh.wizard;

import com.neotropic.kuwaiba.modules.commercial.sdh.SdhService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InventoryException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadataLight;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.navigation.navtree.InventoryObjectNodeTreeGrid;
import org.neotropic.kuwaiba.modules.core.navigation.navtree.nodes.InventoryObjectNode;
import org.neotropic.kuwaiba.modules.core.navigation.navtree.provider.ObjectChildrenProvider;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.neotropic.util.visual.wizard.Wizard;

/**
 * Wizard component to create transport links (STMX)
 * @author Orlando Paz  {@literal <orlando.paz@kuwaiba.org>} 
 */
public class NewSDHTransportLinkWizard extends Wizard {
    
    /**
     * Source equipment.
     */
    private BusinessObjectLight equipmentA;
    /**
     * Target equipment.
     */
    private BusinessObjectLight equipmentB;
    /**
     * Reference to the Business Entity Manager.
     */
    private BusinessEntityManager bem;
    /**
     * Reference to the Metadata Entity Manager.
     */
    private MetadataEntityManager mem;
    /**
     * Reference to the Resource Factory.
     */
    private ResourceFactory rs;
    /**
     * Reference to the Sdh Service.
     */
    private SdhService sdhService;

    public NewSDHTransportLinkWizard(TranslationService ts) {
        super(ts);
    }

    public NewSDHTransportLinkWizard(BusinessObjectLight equipmentA, BusinessObjectLight equipmentB,
            BusinessEntityManager bem, MetadataEntityManager mem, ResourceFactory rs,
            SdhService sdhService, TranslationService ts) {
        super(ts);
        this.equipmentA = equipmentA;
        this.equipmentB = equipmentB;
        this.mem = mem;
        this.bem = bem;
        this.rs = rs;
        this.sdhService = sdhService;
        build(new GeneralInfoStep());
    }

    public class GeneralInfoStep extends Step {
        /**
         * The name of the new connection.
         */
        private final TextField txtName;
        /**
         * The connection type (the class the new connection will be spawned from).
         */
        private final ComboBox<ClassMetadataLight> cmbConnectionClass;
        /**
         * Own properties.
         */
        private final Properties properties;
              
        public GeneralInfoStep() {

            properties = new Properties();
            properties.put("title", ts.getTranslatedString("module.visualization.connection-wizard.general-info"));

            txtName = new TextField(ts.getTranslatedString("module.general.labels.name"));
            txtName.setRequiredIndicatorVisible(true);
            txtName.setClassName("width300px");
            List<ClassMetadataLight> transportLinkClasses = new ArrayList<>();
            try {
                transportLinkClasses = mem.getSubClassesLight("GenericSDHTransportLink", false, false);
            } catch (MetadataObjectNotFoundException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getMessage(),
                        AbstractNotification.NotificationType.ERROR, ts).open();
            }
            cmbConnectionClass = new ComboBox<>(ts.getTranslatedString("module.visualization.connection-wizard-connection-class"));
            cmbConnectionClass.setAllowCustomValue(false);
            cmbConnectionClass.setItems(transportLinkClasses);
            cmbConnectionClass.setRequiredIndicatorVisible(true);
            cmbConnectionClass.setLabel(ts.getTranslatedString("module.visualization.connection-wizard-select-connection-class"));
            cmbConnectionClass.setClassName("width300px");

            add(txtName, cmbConnectionClass);
            setSizeFull();
        }

        @Override
        public Step next() throws InvalidArgumentException  {
            if (txtName.getValue().trim().isEmpty()  || cmbConnectionClass.getValue() == null)
                throw new InvalidArgumentException(ts.getTranslatedString("module.visualization.connection-wizard-fill-fields"));
            properties.put("name", txtName.getValue());
            properties.put("class", cmbConnectionClass.getValue().getName());
            properties.put("equipmentA", equipmentA);
            properties.put("equipmentB", equipmentB);

            return new SelectLinkEndpointsStep(properties);
        }

        @Override
        public boolean isFinal() {
            return false;
        }

        @Override
        public Properties getProperties() {
            return properties;
        }
    }
    
    /**
     * Step to select the endpoints.
     */
    public class SelectLinkEndpointsStep extends Step {
        /**
         * The tree on the left side of the wizard.
         */
        private InventoryObjectNodeTreeGrid<InventoryObjectNode> aSideTree;
        /**
         * The tree on the right side of the wizard.
         */
        private InventoryObjectNodeTreeGrid<InventoryObjectNode> bSideTree;
        /**
         * Own properties.
         */
        private final Properties properties;
        /**
         * Saves the selected object from aSideTree.
         */
        private BusinessObjectLight selectedEndPointA;
        /**
         * Saves the selected object from bSideTree.
         */
        private BusinessObjectLight selectedEndPointB;
        
        public SelectLinkEndpointsStep(Properties properties) {
            
            this.properties = properties;
            this.properties.put("title", ts.getTranslatedString("module.visualization.connection-wizard-select-link-endpoints"));
                    
            if (equipmentA != null && equipmentB != null) {
                // equipmentA grid
                aSideTree = new InventoryObjectNodeTreeGrid<>();
                aSideTree.createDataProvider(rs, new ObjectChildrenProvider(bem, ts),
                        new InventoryObjectNode(equipmentA), true);
                aSideTree.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS,
                        GridVariant.LUMO_COMPACT, GridVariant.LUMO_WRAP_CELL_CONTENT);
                aSideTree.setSelectionMode(Grid.SelectionMode.SINGLE);
                aSideTree.setAllRowsVisible(true);
                aSideTree.addItemClickListener(item -> selectedEndPointA = item.getItem().getObject());
                
                // equipmentB grid
                bSideTree = new InventoryObjectNodeTreeGrid<>();
                bSideTree.createDataProvider(rs, new ObjectChildrenProvider(bem, ts),
                        new InventoryObjectNode(equipmentB), true);
                bSideTree.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS,
                        GridVariant.LUMO_COMPACT, GridVariant.LUMO_WRAP_CELL_CONTENT);
                bSideTree.setSelectionMode(Grid.SelectionMode.SINGLE);
                bSideTree.setAllRowsVisible(true);
                bSideTree.addItemClickListener(item -> selectedEndPointB = item.getItem().getObject());
                
                HorizontalLayout lytTrees = new HorizontalLayout(aSideTree, bSideTree);
                lytTrees.setMaxHeight("360px");
                lytTrees.setWidthFull();
                lytTrees.setMargin(false);
                lytTrees.setSpacing(true);
                this.add(lytTrees);
            }
            this.setSpacing(true);
            this.setWidthFull();
        }

        @Override
        public Step next() throws InvalidArgumentException {
            if (aSideTree.getSelectedItems().isEmpty() || bSideTree.getSelectedItems().isEmpty())
                throw new InvalidArgumentException(ts.getTranslatedString("module.visualization.connection-wizard-select-both-endpoints"));
                       
            try {
                if (!mem.isSubclassOf(Constants.CLASS_GENERICPORT, selectedEndPointA.getClassName())
                        || !mem.isSubclassOf(Constants.CLASS_GENERICPORT, selectedEndPointB.getClassName()))
                    throw new InvalidArgumentException(ts.getTranslatedString("module.visualization.connection-wizard-only-ports-can-be-connected-using-links"));
                else {
                    properties.put("aSide", selectedEndPointA);
                    properties.put("bSide", selectedEndPointB);
                    properties.put("equipmentA", equipmentA);
                    properties.put("equipmentB", equipmentB);
                    
                    String newConnection = sdhService.createSDHTransportLink(
                            selectedEndPointA.getClassName(), 
                            selectedEndPointA.getId(), 
                            selectedEndPointB.getClassName(), 
                            selectedEndPointB.getId(), 
                            properties.getProperty("class"),
                            properties.getProperty("name")
                    );
                    
                    properties.put("connection", new BusinessObjectLight(properties.getProperty("class"),
                            newConnection, properties.getProperty("name")));
                    
                    return null;
                }
            } catch (IllegalStateException | InventoryException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                throw new InvalidArgumentException(ex.getLocalizedMessage());
            }
        }
        
        @Override
        public boolean isFinal() {
            return true;
        }
        
        @Override
        public Properties getProperties() {
            return properties;
        }
    }
}