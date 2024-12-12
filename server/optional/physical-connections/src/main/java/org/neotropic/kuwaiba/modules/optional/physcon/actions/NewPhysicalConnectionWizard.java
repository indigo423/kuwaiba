/*
 *  Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.neotropic.kuwaiba.modules.optional.physcon.actions;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.Session;
import org.neotropic.kuwaiba.core.apis.persistence.application.TemplateObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.OperationNotPermittedException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadataLight;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.navigation.navtree.InventoryObjectNodeTreeGrid;
import org.neotropic.kuwaiba.modules.core.navigation.navtree.nodes.InventoryObjectNode;
import org.neotropic.kuwaiba.modules.core.navigation.navtree.provider.ObjectChildrenProvider;
import org.neotropic.kuwaiba.modules.optional.physcon.PhysicalConnectionsService;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.neotropic.util.visual.general.BoldLabel;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.neotropic.util.visual.wizard.Wizard;

import java.util.Arrays;
import java.util.Properties;
import org.neotropic.kuwaiba.modules.core.logging.LoggerType;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;


/**
 * A wizard that given two initial objects, guides the user through the creation of a physical connection (link or container)
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 * @author Orlando Paz  {@literal <orlando.paz@kuwaiba.org>} 
 */
public class NewPhysicalConnectionWizard extends Wizard {
    
    private PhysicalConnectionsService physicalConnectionsService;
    private BusinessEntityManager bem;
    private ApplicationEntityManager aem;
    private MetadataEntityManager mem;
    private ResourceFactory rs;
    private LoggingService log;

    public NewPhysicalConnectionWizard(BusinessObjectLight rootASide, BusinessObjectLight rootBSide,
                                       BusinessEntityManager bem, ApplicationEntityManager aem,
                                       MetadataEntityManager mem, PhysicalConnectionsService physicalConnectionsService,
                                       ResourceFactory rs, TranslationService ts, LoggingService log) {
        super(ts);
        this.aem = aem;
        this.bem = bem;
        this.mem = mem;
        this.rs = rs;
        this.ts = ts;
        this.physicalConnectionsService = physicalConnectionsService;
        this.log = log;
        build(new GeneralInfoStep(rootASide, rootBSide, bem, aem, mem, physicalConnectionsService, rs, ts, log));
    }
    
    /**
     * The user must choose if he/she wants to create a link or a container and what template (if any) 
     * should be used and provide general information like the name of the new connection and what class 
     * and template should be used for the new object
     */
    public class GeneralInfoStep extends Step {
        /**
         * The name of the new connection
         */
        private TextField txtName;
        /**
         * If the connection is a container or a link
         */
        private ComboBox<ConnectionType> cmbConnectionType;
        /**
         * The connection type (the class the new connection will be spawned from)
         */
        private ComboBox<ClassMetadataLight> cmbConnectionClass;
        /**
         * The list of available templates
         */
        private ComboBox<TemplateObjectLight> cmbTemplates;
        /**
         * Should the connection be created from a template
         */
        private Checkbox chkHasTemplate;
        /**
         * Own properties
         */
        private Properties properties;

        /**
         * Reference to the Application Entity Manager
         */
        private ApplicationEntityManager aem;
        /**
         * Reference to the Business Entity Manager
         */
        private BusinessEntityManager bem;
        /**
         * Reference to the Metadata Entity Manager
         */
        private MetadataEntityManager mem;

        private ResourceFactory rs;

        private TranslationService ts;

        private PhysicalConnectionsService physicalConnectionsService;
        
        private LoggingService log;

        public GeneralInfoStep(BusinessObjectLight rootASide, BusinessObjectLight rootBSide,
                               BusinessEntityManager bem, ApplicationEntityManager aem,
                               MetadataEntityManager mem, PhysicalConnectionsService physicalConnectionsService,
                               ResourceFactory rs, TranslationService ts, LoggingService log) {

            this.aem = aem;
            this.bem = bem;
            this.mem = mem;
            this.rs = rs;
            this.ts = ts;
            this.physicalConnectionsService = physicalConnectionsService;
            this.log = log;
            properties = new Properties();
            properties.put("title", ts.getTranslatedString("module.visualization.connection-wizard.general-info"));
            properties.put("rootASide", rootASide);
            properties.put("rootBSide", rootBSide);

            txtName = new TextField(ts.getTranslatedString("module.general.labels.name"));
            txtName.setRequiredIndicatorVisible(true);
            txtName.setClassName("width300px");
            
            final ConnectionType connUsingLink = new ConnectionType(2, ts.getTranslatedString("module.visualization.connection-wizard-connect-using-link"));
            cmbConnectionType = new ComboBox<>(ts.getTranslatedString("module.visualization.connection-wizard-connection-type"), 
                    Arrays.asList(new ConnectionType(1, "Connect Using a Container"), connUsingLink));
            cmbConnectionType.setAllowCustomValue(false);
            cmbConnectionType.setRequiredIndicatorVisible(true);
            cmbConnectionType.setLabel(ts.getTranslatedString("module.visualization.connection-wizard-select-connection-type"));
            cmbConnectionType.setClassName("width300px");
            cmbConnectionType.setValue(connUsingLink);

            cmbConnectionType.addValueChangeListener((newSelection) -> {
                try {
                    if (newSelection.getValue() != null) {
                        if (newSelection.getValue().getType() == 1)
                            cmbConnectionClass.setItems(this.mem.getSubClassesLight(
                                    Constants.CLASS_GENERICPHYSICALCONTAINER, 
                                    false, false));
                        else
                            cmbConnectionClass.setItems(this.mem.getSubClassesLight(
                                    Constants.CLASS_GENERICPHYSICALLINK,
                                    false, false));
                    }
                } catch (MetadataObjectNotFoundException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                }
            });

            cmbConnectionClass = new ComboBox<>(ts.getTranslatedString("module.visualization.connection-wizard-connection-class"));
            cmbConnectionClass.setAllowCustomValue(false);
            cmbConnectionClass.setRequiredIndicatorVisible(true);
            cmbConnectionClass.setLabel(ts.getTranslatedString("module.visualization.connection-wizard-select-connection-class"));
            cmbConnectionClass.setClassName("width300px");
            cmbConnectionClass.addValueChangeListener((newSelection) -> {
                try {
                    if (newSelection.getValue() != null)
                        cmbTemplates.setItems(this.aem.getTemplatesForClass(newSelection.getValue().getName()));

                } catch (MetadataObjectNotFoundException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getMessage(),
                            AbstractNotification.NotificationType.ERROR, ts).open();
                }
            });
            try { // load link classes by default
                cmbConnectionClass.setItems(this.mem.getSubClassesLight(Constants.CLASS_GENERICPHYSICALLINK, false, false));
            } catch (MetadataObjectNotFoundException ex) {
                log.writeLogMessage(LoggerType.ERROR, NewPhysicalConnectionWizard.class, "", ex);
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getMessage(),
                        AbstractNotification.NotificationType.ERROR, ts).open();
            }

            cmbTemplates = new ComboBox<>(ts.getTranslatedString("module.visualization.connection-wizard-template"));
            cmbTemplates.setEnabled(false);
            cmbTemplates.setClassName("width300px");
            chkHasTemplate = new Checkbox(ts.getTranslatedString("module.visualization.connection-wizard-use-template"));
            chkHasTemplate.addValueChangeListener((newSelection) -> {
                cmbTemplates.setEnabled(chkHasTemplate.getValue());
            });
            HorizontalLayout lytTemplate = new HorizontalLayout(chkHasTemplate, cmbTemplates);
            lytTemplate.setAlignItems(Alignment.BASELINE);
            add(txtName, cmbConnectionType, cmbConnectionClass, lytTemplate);
            setSizeFull();
        }

        @Override
        public Step next() throws InvalidArgumentException  {
            if (txtName.getValue().trim().isEmpty() || cmbConnectionType.getValue() == null
                    || cmbConnectionClass.getValue() == null || (chkHasTemplate.getValue() && cmbTemplates.getValue() == null))
                throw new InvalidArgumentException(ts.getTranslatedString("module.visualization.connection-wizard-fill-fields"));
            properties.put("name", txtName.getValue());
            properties.put("class", cmbConnectionClass.getValue().getName());
            
            properties.put("templateId", chkHasTemplate.getValue() ? cmbTemplates.getValue().getId() : "");
            
            if (cmbConnectionType.getValue().type == 1)
                return new SelectContainerEndpointsStep(properties, bem ,aem , mem , physicalConnectionsService, rs);
            else
                return new SelectLinkEndpointsStep(properties, bem ,aem , mem , physicalConnectionsService, rs);
        }

        @Override
        public boolean isFinal() {
            return false;
        }

        @Override
        public Properties getProperties() {
            return properties;
        }
        
        private class ConnectionType {
            private int type;
            private final String displayName;

            public ConnectionType(int type, String displayName) {
                this.type = type;
                this.displayName = displayName;
            }

            public int getType() {
                return type;
            }
            
            @Override
            public String toString() {
                return displayName;
            }

            @Override
            public boolean equals(Object obj) {
                 if(obj == null)
                    return false;
                 if (!(obj instanceof ConnectionType))
                    return false;
                 return ((ConnectionType) obj).getType() == type;
            }

            @Override
            public int hashCode() {
                int hash = 7;
                hash = 67 * hash + this.type;
                return hash;
            }
        }
    }
    
    /**
     * Step to select the endpoints if the connection type selected in the past step was a container.
     */
    public class SelectContainerEndpointsStep extends Step {
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
         * Reference to the Metadata Entity Manager.
         */
        private final MetadataEntityManager mem;
        /**
         * Reference to the Physical Connections Service.
         */
        private final PhysicalConnectionsService physicalConnectionsService;
        /**
         * Saves the selected object from aSideTree.
         */
        private BusinessObjectLight selectedEndPointA;
        /**
         * Saves the selected object from bSideTree.
         */
        private BusinessObjectLight selectedEndPointB;
        
        public SelectContainerEndpointsStep(Properties properties, BusinessEntityManager bem,
                                            ApplicationEntityManager aem, MetadataEntityManager mem,
                                            PhysicalConnectionsService physicalConnectionsService, ResourceFactory rs) {
            
            this.mem = mem;
            this.physicalConnectionsService = physicalConnectionsService;
            this.properties = properties;
            this.properties.put("title", ts.getTranslatedString("module.visualization.connection-wizard-select-container-endpoints"));
                       
            BusinessObjectLight rootASide = (BusinessObjectLight) properties.get("rootASide");
            BusinessObjectLight rootBSide = (BusinessObjectLight) properties.get("rootBSide");
            
            if (rootASide != null && rootBSide != null) {
                // rootAside grid
                aSideTree = new InventoryObjectNodeTreeGrid<>();
                aSideTree.createDataProvider(rs, new ObjectChildrenProvider(bem, ts),
                        new InventoryObjectNode(rootASide), true);
                aSideTree.addItemClickListener(item -> selectedEndPointA = item.getItem().getObject());
                aSideTree.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS,
                        GridVariant.LUMO_COMPACT, GridVariant.LUMO_WRAP_CELL_CONTENT);
                aSideTree.setSelectionMode(Grid.SelectionMode.SINGLE);
                aSideTree.setId("a-side-tree");
                
                // rootBside grid
                bSideTree = new InventoryObjectNodeTreeGrid<>();
                bSideTree.createDataProvider(rs, new ObjectChildrenProvider(bem, ts),
                        new InventoryObjectNode(rootBSide), true);
                bSideTree.addItemClickListener(item -> selectedEndPointB = item.getItem().getObject());
                bSideTree.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS,
                        GridVariant.LUMO_COMPACT, GridVariant.LUMO_WRAP_CELL_CONTENT);
                bSideTree.setSelectionMode(Grid.SelectionMode.SINGLE);
                bSideTree.setId("b-side-tree");
                
                HorizontalLayout lytTrees = new HorizontalLayout(aSideTree, bSideTree);
                lytTrees.setHeightFull();
                lytTrees.setWidthFull();
                lytTrees.setMargin(false);
                lytTrees.setSpacing(true);
                this.add(lytTrees);
            }
            this.setSpacing(true);
            this.setWidthFull();
            this.setHeightFull();
        }
            
        @Override
        public Step next() throws InvalidArgumentException {
            if (aSideTree.getSelectedItems().isEmpty() || bSideTree.getSelectedItems().isEmpty())
                throw new InvalidArgumentException(ts.getTranslatedString("module.visualization.connection-wizard-select-both-endpoints"));
  
            try {
                if (mem.isSubclassOf(Constants.CLASS_GENERICPORT, selectedEndPointA.getClassName())
                        || mem.isSubclassOf(Constants.CLASS_GENERICPORT, selectedEndPointB.getClassName())) {
                    throw new InvalidArgumentException(ts.getTranslatedString("module.visualization.connection-wizard-ports-cant-be-enpoints-containers"));
                } else {
                    properties.put("aSide", selectedEndPointA);
                    properties.put("bSide", selectedEndPointB);
                    Session session = UI.getCurrent().getSession().getAttribute(Session.class);
                    String newConnection = physicalConnectionsService.createPhysicalConnection(
                            selectedEndPointA.getClassName(),
                            selectedEndPointA.getId(),
                            selectedEndPointB.getClassName(),
                            selectedEndPointB.getId(),
                            properties.getProperty("name"),
                            properties.getProperty("class"),
                            (String) properties.get("templateId"),
                            session.getUser().getUserName()
                    );

                    properties.put("connection", new BusinessObjectLight(properties.getProperty("class"),
                            newConnection, properties.getProperty("name")));

                    return null;
                }
            } catch (IllegalStateException | OperationNotPermittedException | MetadataObjectNotFoundException ex) {
                log.writeLogMessage(LoggerType.ERROR, NewPhysicalConnectionWizard.class, "", ex);
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getMessage(),
                        AbstractNotification.NotificationType.ERROR, ts).open();
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
    
    /**
     * Step to select the endpoints if the connection type selected in the past step was a link.
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
         * Reference to the Metadata Entity Manager.
         */
        private final MetadataEntityManager mem;
        /**
         * Reference to the Physical Connections Service.
         */
        private final PhysicalConnectionsService physicalConnectionsService;
        /**
         * Saves the selected object from aSideTree.
         */
        private BusinessObjectLight selectedEndPointA;
        /**
         * Saves the selected object from bSideTree.
         */
        private BusinessObjectLight selectedEndPointB;
        
        public SelectLinkEndpointsStep(Properties properties, BusinessEntityManager bem, 
                ApplicationEntityManager aem, MetadataEntityManager mem,
                PhysicalConnectionsService physicalConnectionsService, ResourceFactory rs) {
            
            this.mem = mem;
            this.physicalConnectionsService = physicalConnectionsService;
            this.properties = properties;
            this.properties.put("title", ts.getTranslatedString("module.visualization.connection-wizard-select-link-endpoints"));
            
            BusinessObjectLight rootASide = (BusinessObjectLight)properties.get("rootASide");
            BusinessObjectLight rootBSide = (BusinessObjectLight)properties.get("rootBSide");
            
            if (rootASide != null && rootBSide != null) {
                Label lblSelectedEndPointsTitle = new BoldLabel(String.format(ts.getTranslatedString("module.visualization.connection-wizard-selected-endpoints"), "", ""));
                Label lblSelectedEndPoints = new Label();
                HorizontalLayout lytSelectedEndPoints = new HorizontalLayout(lblSelectedEndPointsTitle, lblSelectedEndPoints);
                lytSelectedEndPoints.setPadding(false);
                lytSelectedEndPoints.getElement().getStyle().set("z-index", "1000");
                
                // rootAside grid
                aSideTree = new InventoryObjectNodeTreeGrid<>();
                aSideTree.createDataProvider(rs, new ObjectChildrenProvider(bem, ts),
                        new InventoryObjectNode(rootASide), true);
                aSideTree.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS,
                        GridVariant.LUMO_COMPACT, GridVariant.LUMO_WRAP_CELL_CONTENT);
                aSideTree.setSelectionMode(Grid.SelectionMode.SINGLE);
                aSideTree.setId("a-side-tree");
                
                aSideTree.addItemClickListener(item -> {
                    selectedEndPointA = item.getItem().getObject().equals(selectedEndPointA) ? null : item.getItem().getObject();
                    lblSelectedEndPoints.setText(String.format(" %s - %s",
                            selectedEndPointA == null ? "" : selectedEndPointA.getName(),
                            selectedEndPointB == null ? "" : selectedEndPointB.getName()));
                });
                
                // rootBside grid
                bSideTree = new InventoryObjectNodeTreeGrid<>();
                bSideTree.createDataProvider(rs, new ObjectChildrenProvider(bem, ts),
                        new InventoryObjectNode(rootBSide), true);
                bSideTree.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS,
                        GridVariant.LUMO_COMPACT, GridVariant.LUMO_WRAP_CELL_CONTENT);
                bSideTree.setSelectionMode(Grid.SelectionMode.SINGLE);
                bSideTree.setId("a-side-tree");
                
                bSideTree.addItemClickListener(item -> {
                    selectedEndPointB = item.getItem().getObject().equals(selectedEndPointB) ? null : item.getItem().getObject();
                    lblSelectedEndPoints.setText(String.format(" %s - %s",
                            selectedEndPointA == null ? "" : selectedEndPointA.getName(),
                            selectedEndPointB == null ? "" : selectedEndPointB.getName()));
                });

                HorizontalLayout lytTrees = new HorizontalLayout(aSideTree, bSideTree);
                lytTrees.setHeightFull();
                lytTrees.setWidthFull();
                lytTrees.setMargin(false);
                lytTrees.setSpacing(true);
                this.add(lytTrees, lytSelectedEndPoints);
            }
            
            this.setSpacing(true);
            this.setWidthFull();
            this.setHeightFull();
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
                    
                    Session session = UI.getCurrent().getSession().getAttribute(Session.class);
                    String newConnection = physicalConnectionsService.createPhysicalConnection(
                            selectedEndPointA.getClassName(),
                            selectedEndPointA.getId(),
                            selectedEndPointB.getClassName(), 
                            selectedEndPointB.getId(),
                            properties.getProperty("name"),
                            properties.getProperty("class"), 
                            (String)properties.get("templateId"),
                            session.getUser().getUserName()
                    );
                    
                    properties.put("connection", new BusinessObjectLight(properties.getProperty("class"),
                            newConnection, properties.getProperty("name")));
                    
                    return null;
                }
            } catch (IllegalStateException | InvalidArgumentException
                    | MetadataObjectNotFoundException | OperationNotPermittedException ex) {
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