/*
 * Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neotropic.kuwaiba.modules.optional.serviceman.actions;

import com.vaadin.flow.component.dialog.Dialog;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAdvancedAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InventoryException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.optional.serviceman.ServiceManagerModule;
import org.neotropic.kuwaiba.modules.optional.serviceman.ServiceManagerUI;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.neotropic.util.visual.selectors.NetworkResourceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Visual wrapper of relate service to network resources action.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class RelateNetworkResourceToServiceVisualAction extends AbstractVisualAdvancedAction {

    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Business Entity Manager.
     */
    @Autowired
    private BusinessEntityManager bem;
    /**
     * Reference to the Application Entity Manager.
     */
    @Autowired
    private ApplicationEntityManager aem;
    /**
     * Reference to the Metadata Entity Manager.
     */
    @Autowired
    private MetadataEntityManager mem;
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private RelateNetworkResourceToServiceAction relateNetworkResourceToServiceAction;
    /**
     * Saves the selected objects.
     */
    private BusinessObjectLight selectedObject;
    private List<BusinessObjectLight> selectedObjects;
    /**
     * The confirm dialog to relate a network resource to the service.
     */
    private ConfirmDialog wdwRelate;

    public RelateNetworkResourceToServiceVisualAction() {
        super(ServiceManagerModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        if (parameters.containsKey(ServiceManagerUI.PARAMETER_BUSINESS_OBJECT)) {
            BusinessObjectLight service = (BusinessObjectLight) parameters.get(ServiceManagerUI.PARAMETER_BUSINESS_OBJECT);

            wdwRelate = new ConfirmDialog(ts, this.relateNetworkResourceToServiceAction.getDisplayName());
            createNetworkResourceSelector();

            wdwRelate.getBtnConfirm().addClickListener(event -> {
                try {
                    if (selectedObject != null && mem.isSubclassOf(Constants.CLASS_INVENTORYOBJECT,
                            selectedObject.getClassName())) {
                        relateNetworkResourceToServiceAction.getCallback().execute(new ModuleActionParameterSet(
                                new ModuleActionParameter<>(ServiceManagerUI.PARAMETER_SERVICE, service),
                                new ModuleActionParameter<>(ServiceManagerUI.PARAMETER_BUSINESS_OBJECT, selectedObject)
                        ));

                        fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                                String.format(ts.getTranslatedString(
                                                "module.serviceman.actions.relate-service-to-network-resource.relationship-success"),
                                        selectedObject.getName(), service.getName()),
                                RelateNetworkResourceToServiceAction.class));

                        clearElements();
                    }
                } catch (MetadataObjectNotFoundException | ModuleActionException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                }
            });

            wdwRelate.getBtnCancel().addClickListener(event -> clearElements());

            wdwRelate.setId("wdwRelate");
            wdwRelate.setMinHeight("20%");
            return wdwRelate;
        } else {
            ConfirmDialog errorDialog = new ConfirmDialog(ts,
                    this.getModuleAction().getDisplayName(), ts.getTranslatedString(
                    "module.serviceman.actions.relate-service-to-network-resource.relationship-error-param-object"));
            errorDialog.getBtnConfirm().addClickListener(e -> errorDialog.close());
            return errorDialog;
        }
    }

    /**
     * Builds the selector that allows to search for a network resource, selecting a parent and browsing its children.
     * Applies to inventory objects.
     */
    public void createNetworkResourceSelector() {
        try {
            NetworkResourceSelector networkResourceSelector = new NetworkResourceSelector(
                    selectedObject, selectedObjects, ts.getTranslatedString(
                    "module.serviceman.actions.relate-service-to-network-resource.placeholder"),
                    aem, bem, mem, ts);
            networkResourceSelector.setId("networkResourceSelector");

            networkResourceSelector.addSelectedObjectChangeListener(event -> {
                selectedObject = event.getSelectedObject();
                selectedObjects = event.getSelectedObjects();
            });

            wdwRelate.setContent(networkResourceSelector);
        } catch (InventoryException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    /**
     * Clears the elements when the relate action is completed or canceled.
     */
    private void clearElements() {
        selectedObject = null;
        selectedObjects = null;
        wdwRelate.close();
    }

    @Override
    public AbstractAction getModuleAction() {
        return relateNetworkResourceToServiceAction;
    }

    @Override
    public String appliesTo() {
        return Constants.CLASS_GENERICSERVICE;
    }

    @Override
    public int getRequiredSelectedObjects() {
        return 1;
    }
}