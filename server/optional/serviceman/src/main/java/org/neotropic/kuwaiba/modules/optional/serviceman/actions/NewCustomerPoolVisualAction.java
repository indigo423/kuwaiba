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
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.optional.serviceman.ServiceManagerModule;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Visual wrapper of a new customer pool action.
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
@Component
public class NewCustomerPoolVisualAction extends AbstractVisualAdvancedAction {
    /**
     * Close action command
     */
    private Command commandAddCustomerPoolDashboard;
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private NewCustomerPoolAction newCustomerPoolAction;

    public NewCustomerPoolVisualAction() {
        super(ServiceManagerModule.MODULE_ID);
    }
    
    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        commandAddCustomerPoolDashboard = (Command) parameters.get("commandAddCustomerPoolDashboard");
        
        TextField txtName = new TextField(ts.getTranslatedString("module.serviceman.actions.new-customer-pool.ui.pool-name"));
        txtName.setRequiredIndicatorVisible(true);
        txtName.setSizeFull();

        TextField txtDescription = new TextField(ts.getTranslatedString("module.serviceman.actions.new-customer-pool.ui.pool-description"));
        txtDescription.setSizeFull();

        ConfirmDialog wdwNewCustomerPool = new ConfirmDialog(ts, this.newCustomerPoolAction.getDisplayName());
        wdwNewCustomerPool.getBtnConfirm().addClickListener( (e) -> {
            try {
                if (txtName.getValue() == null || txtName.getValue().trim().isEmpty())
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                            String.format(ts.getTranslatedString("module.general.messages.attribute-not-empty"),
                                    ts.getTranslatedString("module.serviceman.actions.new-customer-pool.ui.pool-name")),
                            AbstractNotification.NotificationType.WARNING, ts).open();
                else {
                    newCustomerPoolAction.getCallback().execute(new ModuleActionParameterSet(
                            new ModuleActionParameter<>(Constants.PROPERTY_NAME, txtName.getValue()), 
                            new ModuleActionParameter<>(Constants.PROPERTY_DESCRIPTION, txtDescription.getValue())));

                    fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                            ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                            ts.getTranslatedString("module.serviceman.actions.new-customer-pool.ui.customer-pool-created-success"),
                            NewCustomerPoolAction.class));
                    wdwNewCustomerPool.close();
                    // Refresh related grid
                    commandAddCustomerPoolDashboard.execute();
                }
            } catch (ModuleActionException ex) {
                fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                        ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                        ex.getMessage(), NewCustomerPoolAction.class));
            }
        });
        
        wdwNewCustomerPool.setContent(txtName, txtDescription);
        return wdwNewCustomerPool;
    }

    @Override
    public AbstractAction getModuleAction() {
        return newCustomerPoolAction;
    }

    @Override
    public String appliesTo() {
        return null;
    }

    @Override
    public int getRequiredSelectedObjects() {
        return 0;
    }
}