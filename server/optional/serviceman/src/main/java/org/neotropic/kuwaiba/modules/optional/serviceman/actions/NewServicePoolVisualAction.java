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

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.Command;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAdvancedAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.optional.serviceman.ServiceManagerModule;
import org.neotropic.kuwaiba.modules.optional.serviceman.ServiceManagerUI;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Visual wrapper of a new service pool action.
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
@Component
public class NewServicePoolVisualAction extends AbstractVisualAdvancedAction {
    /**
     * Action command.
     */
    private Command commandAddServicePoolDashboard;
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private NewServicePoolAction newServicePoolAction;
    /**
     * Reference to the business entity manager.
     */
    @Autowired
    protected BusinessEntityManager bem;
    /**
     * Parameter business object.
     */
    public static String PARAMETER_BUSINESS_OBJECT = "businessObject";

    public NewServicePoolVisualAction() {
        super(ServiceManagerModule.MODULE_ID);
    }
    
    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        if (parameters.containsKey(PARAMETER_BUSINESS_OBJECT)) {
            BusinessObjectLight selectedObject = (BusinessObjectLight) parameters.get(PARAMETER_BUSINESS_OBJECT);
            commandAddServicePoolDashboard = (Command) parameters.get("commandAddServicePoolDashboard");
            
            TextField txtName = new TextField(ts.getTranslatedString("module.serviceman.actions.new-service-pool.ui.pool-name"));
            txtName.setRequired(true);
            txtName.setRequiredIndicatorVisible(true);
            txtName.setSizeFull();
            
            TextField txtDescription = new TextField(ts.getTranslatedString("module.serviceman.actions.new-service-pool.ui.pool-description"));
            txtDescription.setWidthFull();
            
            ConfirmDialog wdwNewCustomerPool = new ConfirmDialog(ts,
                    String.format(ts.getTranslatedString("module.serviceman.actions.new-service-pool.header"),
                            selectedObject.getName()));
            wdwNewCustomerPool.getBtnConfirm().addClickListener(e -> {
                try {
                    if (txtName.getValue() == null || txtName.getValue().trim().isEmpty())
                        new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                                String.format(ts.getTranslatedString("module.general.messages.attribute-not-empty"),
                                        ts.getTranslatedString("module.reporting.parameters.name")),
                                AbstractNotification.NotificationType.WARNING, ts).open();
                    else {
                        newServicePoolAction.getCallback().execute(new ModuleActionParameterSet(
                                new ModuleActionParameter<>(Constants.PROPERTY_PARENT, selectedObject),
                                new ModuleActionParameter<>(Constants.PROPERTY_NAME, txtName.getValue()),
                                new ModuleActionParameter<>(Constants.PROPERTY_DESCRIPTION, txtDescription.getValue())));
                        
                        ActionResponse actionResponse = new ActionResponse();
                        actionResponse.put(ActionResponse.ActionType.ADD, "");
                        
                        fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                                ts.getTranslatedString("module.serviceman.actions.new-service-pool.ui.pool-created-success"),
                                NewServicePoolAction.class, actionResponse));
                        wdwNewCustomerPool.close();
                        // Refresh related grid
                        commandAddServicePoolDashboard.execute();
                    }
                } catch (ModuleActionException ex) {
                    fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                            ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                            ex.getMessage(), NewServicePoolAction.class));
                }
            });
            
            wdwNewCustomerPool.setContent(txtName, txtDescription);
            return wdwNewCustomerPool;
        } else {
            ConfirmDialog errorDialog = new ConfirmDialog(ts,
                    this.getModuleAction().getDisplayName(),
                    String.format(ts.getTranslatedString("module.general.messages.parameter-not-found"),
                            ServiceManagerUI.PARAMETER_BUSINESS_OBJECT)
            );
            errorDialog.getBtnConfirm().addClickListener(e -> errorDialog.close());
            return errorDialog;
        }
    }

    @Override
    public AbstractAction getModuleAction() {
        return newServicePoolAction;
    }

    @Override
    public String appliesTo() {
        return Constants.CLASS_GENERICCUSTOMER;
    }

    @Override
    public int getRequiredSelectedObjects() {
        return 0;
    }
}