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
package org.neotropic.kuwaiba.modules.optional.serviceman.actions;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualInventoryAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.application.TemplateObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadataLight;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.optional.serviceman.ServiceManagerModule;
import org.neotropic.kuwaiba.modules.optional.serviceman.ServiceManagerUI;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Visual wrapper of a new service from template.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class NewServiceFromTemplateVisualAction extends AbstractVisualInventoryAction {

    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the metadata entity manager.
     */
    @Autowired
    private MetadataEntityManager mem;
    /**
     * Reference to the Application Entity Manager.
     */
    @Autowired
    private ApplicationEntityManager aem;
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private NewServiceFromTemplateAction newServiceFromTemplateAction;
    /**
     * Window to create the new service.
     */
    private ConfirmDialog wdwNewService;

    public NewServiceFromTemplateVisualAction() {
        super(ServiceManagerModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        try {
            if (parameters.containsKey(ServiceManagerUI.PARAMETER_SERVICE_POOL)) {
                InventoryObjectPool pool = (InventoryObjectPool) parameters.get(ServiceManagerUI.PARAMETER_SERVICE_POOL);
                wdwNewService = new ConfirmDialog(ts, this.getModuleAction().getDisplayName());

                List<ClassMetadataLight> getServiceTypeData = mem.getSubClassesLight(Constants.CLASS_GENERICSERVICE, false, false);
                ComboBox<ClassMetadataLight> cmbServiceTypes = new ComboBox<>(
                        ts.getTranslatedString("module.serviceman.actions.new-service.ui.service-type"),
                        getServiceTypeData
                );
                cmbServiceTypes.setRequiredIndicatorVisible(true);
                cmbServiceTypes.setSizeFull();

                ComboBox<TemplateObjectLight> cmbTemplate = new ComboBox<>(
                        ts.getTranslatedString("module.serviceman.actions.new-service-from-template.ui.template"));
                cmbTemplate.setItemLabelGenerator(TemplateObjectLight::getName);
                cmbTemplate.setRequiredIndicatorVisible(true);
                cmbTemplate.setSizeFull();

                cmbServiceTypes.addValueChangeListener(listener -> {
                    if (listener.getValue() != null) {
                        try {
                            List<TemplateObjectLight> templatesForSelectedClass = aem.getTemplatesForClass(listener.getValue().getName());
                            cmbTemplate.setItems(templatesForSelectedClass);
                        } catch (MetadataObjectNotFoundException ex) {
                            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                        }
                    }
                });

                wdwNewService.getBtnConfirm().addClickListener(event -> {
                    if (cmbServiceTypes.getValue() == null)
                        notificationEmptyFields(ts.getTranslatedString("module.serviceman.actions.new-service.ui.service-type"));
                    else if (cmbTemplate.getValue() == null)
                        notificationEmptyFields(ts.getTranslatedString("module.serviceman.actions.new-service-from-template.ui.template"));
                    else {
                        ActionResponse actionResponse = null;
                        try {
                            actionResponse = newServiceFromTemplateAction.getCallback().execute(new ModuleActionParameterSet(
                                    new ModuleActionParameter<>("poolId", pool.getId()),
                                    new ModuleActionParameter<>("serviceClass", cmbServiceTypes.getValue().getName()),
                                    new ModuleActionParameter<>("templateId", cmbTemplate.getValue().getId())));

                            actionResponse.put(ActionResponse.ActionType.ADD, "");
                            actionResponse.put(ServiceManagerUI.PARAMETER_SERVICE_POOL, pool);

                            fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                    ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                                    ts.getTranslatedString("module.serviceman.actions.new-service-from-template.ui.service-created-success"),
                                    NewServiceFromTemplateAction.class, actionResponse));

                            wdwNewService.close();
                        } catch (ModuleActionException ex) {
                            fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                    ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                                    ex.getMessage(), NewServiceFromTemplateAction.class));
                        }
                    }
                });
                wdwNewService.setContent(cmbServiceTypes, cmbTemplate);
            } else {
                wdwNewService = new ConfirmDialog(ts,
                        this.getModuleAction().getDisplayName(),
                        String.format(ts.getTranslatedString("module.general.messages.parameter-not-found"),
                                ServiceManagerUI.PARAMETER_SERVICE_POOL)
                );
                wdwNewService.getBtnConfirm().addClickListener(e -> wdwNewService.close());
            }
        } catch (MetadataObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }

        return wdwNewService;
    }

    private void notificationEmptyFields(String field) {
        new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                String.format(ts.getTranslatedString("module.general.messages.attribute-not-empty"), field),
                AbstractNotification.NotificationType.WARNING, ts).open();
    }

    @Override
    public AbstractAction getModuleAction() {
        return newServiceFromTemplateAction;
    }

    @Override
    public int getRequiredSelectedObjects() {
        return 1;
    }
}