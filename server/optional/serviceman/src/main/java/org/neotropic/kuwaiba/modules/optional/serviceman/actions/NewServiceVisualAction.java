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
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.Command;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAdvancedAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadataLight;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.optional.serviceman.ServiceManagerModule;
import org.neotropic.kuwaiba.modules.optional.serviceman.ServiceManagerService;
import org.neotropic.kuwaiba.modules.optional.serviceman.ServiceManagerUI;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.icon.ActionIcon;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Visual wrapper of a new customer action that provides means to choose the service pool and type.
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
@Component
public class NewServiceVisualAction extends AbstractVisualAdvancedAction implements ActionCompletedListener {
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private NewServiceAction newServiceAction;
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
     * Reference to the service manager service.
     */
    @Autowired
    private ServiceManagerService sms;
    /**
     * Reference to the action that creates service pools.
     */
    @Autowired
    private NewServicePoolVisualAction newServicePoolVisualAction;
    /**
     * Displays the available service types.
     */
    private ComboBox<ClassMetadataLight> cmbServiceTypes;
    /**
     * Window to create the new service.
     */
    private ConfirmDialog wdwNewService;
    /**
     * Saves the service name.
     */
    private TextField txtName;

    public NewServiceVisualAction() {
        super(ServiceManagerModule.MODULE_ID);
    }
    
    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        // This action might be called with or without parameters depending on who launches it. 
        // For example, if launched from the dashboard, it won't received any initial parameter and all the 
        // necessary information will have to be requested (the parent customer pool and the customer type), 
        // but if launched from a customer pool, only the customer type will be requested.
        if (parameters.containsKey(ServiceManagerUI.PARAMETER_SERVICE_POOL)) {
            InventoryObjectPool pool = (InventoryObjectPool) parameters.get(ServiceManagerUI.PARAMETER_SERVICE_POOL);

            cmbServiceTypes = new ComboBox<>(
                    ts.getTranslatedString("module.serviceman.actions.new-service.ui.service-type"),
                    getServiceTypeData()
            );
            cmbServiceTypes.setRequiredIndicatorVisible(true);
            cmbServiceTypes.setSizeFull();

            txtName = new TextField(ts.getTranslatedString("module.serviceman.actions.new-service.ui.service-name"));
            txtName.setRequiredIndicatorVisible(true);
            txtName.setSizeFull();

            wdwNewService = new ConfirmDialog(ts, this.newServiceAction.getDisplayName());
            wdwNewService.getBtnConfirm().addClickListener((e) -> {
                try {
                    if (cmbServiceTypes.getValue() == null)
                        notificationEmptyFields(ts.getTranslatedString("module.serviceman.actions.new-service.ui.service-type"));
                    else if (txtName.getValue() == null || txtName.getValue().trim().isEmpty())
                        notificationEmptyFields(ts.getTranslatedString("module.serviceman.actions.new-service.ui.service-name"));
                    else {
                        HashMap<String, String> attributes = new HashMap<>();
                        attributes.put(Constants.PROPERTY_NAME, txtName.getValue());

                        ActionResponse actionResponse = newServiceAction.getCallback().execute(new ModuleActionParameterSet(
                                new ModuleActionParameter<>("poolId", pool.getId()),
                                new ModuleActionParameter<>("serviceClass", cmbServiceTypes.getValue().getName()),
                                new ModuleActionParameter<>("attributes", attributes)));

                        actionResponse.put(ActionResponse.ActionType.ADD, "");
                        actionResponse.put(ServiceManagerUI.PARAMETER_SERVICE_POOL, pool);

                        fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                                ts.getTranslatedString("module.serviceman.actions.new-service.ui.service-created-success"),
                                NewServiceAction.class, actionResponse));
                        wdwNewService.close();
                    }
                } catch (ModuleActionException ex) {
                    fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                            ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                            ex.getMessage(), NewServiceAction.class));
                }
            });
            wdwNewService.setContent(cmbServiceTypes, txtName);
        } else if (parameters.containsKey(ServiceManagerUI.PARAMETER_BUSINESS_OBJECT)) {
            this.newServicePoolVisualAction.registerActionCompletedLister(this);

            BusinessObjectLight customer = (BusinessObjectLight) parameters.get(ServiceManagerUI.PARAMETER_BUSINESS_OBJECT);

            ComboBox<InventoryObjectPool> cmbPools = new ComboBox<>(
                    ts.getTranslatedString("module.serviceman.actions.new-service.ui.service-pool"),
                    getServicePoolsData(customer)
            );
            cmbPools.setRequiredIndicatorVisible(true);
            cmbPools.setWidth("90%");

            Command commandAddServicePool = () -> cmbPools.setItems(getServicePoolsData(customer));
            ActionButton btnAddPool = new ActionButton(new ActionIcon(VaadinIcon.PLUS_SQUARE_O),
                    this.newServicePoolVisualAction.getModuleAction().getDisplayName());
            btnAddPool.addClickListener((event) -> {
                this.newServicePoolVisualAction.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>("commandAddServicePoolDashboard", commandAddServicePool),
                        new ModuleActionParameter<>("businessObject", customer))).open();
            });
            btnAddPool.getStyle().set("margin-top", "30px");
            btnAddPool.setHeight("32px");
            btnAddPool.setWidth("10%");

            HorizontalLayout lytPools = new HorizontalLayout(cmbPools, btnAddPool);
            lytPools.setWidthFull();
            lytPools.setSpacing(false);

            cmbServiceTypes = new ComboBox<>(
                    ts.getTranslatedString("module.serviceman.actions.new-service.ui.service-type"),
                    getServiceTypeData()
            );
            cmbServiceTypes.setRequiredIndicatorVisible(true);
            cmbServiceTypes.setSizeFull();

            txtName = new TextField(ts.getTranslatedString("module.serviceman.actions.new-service.ui.service-name"));
            txtName.setRequiredIndicatorVisible(true);
            txtName.setSizeFull();

            wdwNewService = new ConfirmDialog(ts, this.newServiceAction.getDisplayName());
            wdwNewService.getBtnConfirm().addClickListener((e) -> {
                try {
                    if (cmbPools.getValue() == null)
                        notificationEmptyFields(ts.getTranslatedString("module.serviceman.actions.new-service.ui.service-pool"));
                    else if (cmbServiceTypes.getValue() == null)
                        notificationEmptyFields(ts.getTranslatedString("module.serviceman.actions.new-service.ui.service-type"));
                    else if (txtName.getValue() == null || txtName.getValue().trim().isEmpty())
                        notificationEmptyFields(ts.getTranslatedString("module.serviceman.actions.new-service.ui.service-name"));
                    else {
                        HashMap<String, String> attributes = new HashMap<>();
                        attributes.put(Constants.PROPERTY_NAME, txtName.getValue());

                        ActionResponse actionResponse = newServiceAction.getCallback().execute(new ModuleActionParameterSet(
                                new ModuleActionParameter<>("poolId", cmbPools.getValue().getId()),
                                new ModuleActionParameter<>("serviceClass", cmbServiceTypes.getValue().getName()),
                                new ModuleActionParameter<>("attributes", attributes)));

                        actionResponse.put(ActionResponse.ActionType.ADD, "");
                        actionResponse.put(ServiceManagerUI.PARAMETER_SERVICE_POOL, cmbPools.getValue());

                        fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                                ts.getTranslatedString("module.serviceman.actions.new-service.ui.service-created-success"),
                                NewServiceAction.class, actionResponse));

                        releaseResources();
                        wdwNewService.close();
                    }
                } catch (ModuleActionException ex) {
                    fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                            ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                            ex.getMessage(), NewServiceAction.class));
                }
            });

            wdwNewService.getBtnCancel().addClickListener(event -> {
                releaseResources();
                wdwNewService.close();
            });

            wdwNewService.setContent(lytPools, cmbServiceTypes, txtName);
        } else {
            wdwNewService = new ConfirmDialog(ts,
                    this.getModuleAction().getDisplayName(),
                    ts.getTranslatedString("module.general.messages.unexpected-error")
            );
            wdwNewService.getBtnConfirm().addClickListener(e -> wdwNewService.close());
        }
        return wdwNewService;
    }

    private List<InventoryObjectPool> getServicePoolsData(BusinessObjectLight customer) {
        try {
            return sms.getServicePoolsInCostumer(customer.getClassName(), customer.getId(),
                    Constants.CLASS_GENERICSERVICE);
        } catch (InvalidArgumentException | BusinessObjectNotFoundException |
                 MetadataObjectNotFoundException | ApplicationObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return new ArrayList<>();
        }
    }

    private List<ClassMetadataLight> getServiceTypeData() {
        try {
            return mem.getSubClassesLight(Constants.CLASS_GENERICSERVICE, false, false);
        } catch (MetadataObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return new ArrayList<>();
        }
    }

    private void releaseResources() {
        this.newServicePoolVisualAction.unregisterListener(this);
    }

    private void notificationEmptyFields(String field) {
        new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                String.format(ts.getTranslatedString("module.general.messages.attribute-not-empty"), field),
                AbstractNotification.NotificationType.WARNING, ts).open();
    }
    
    @Override
    public AbstractAction getModuleAction() {
        return newServiceAction;
    }

    @Override
    public String appliesTo() {
        return Constants.CLASS_GENERICCUSTOMER;
    }

    @Override
    public int getRequiredSelectedObjects() {
        return 1;
    }

    @Override
    public void actionCompleted(ActionCompletedEvent ev) {
        if (ev.getStatus() == ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                    ev.getMessage(), AbstractNotification.NotificationType.INFO, ts).open();
        } else {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ev.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }
}