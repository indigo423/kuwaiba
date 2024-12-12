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
package org.neotropic.kuwaiba.modules.optional.taskman.actions;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.application.Task;
import org.neotropic.kuwaiba.core.apis.persistence.util.StringPair;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.optional.taskman.TaskManagerModule;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Visual wrapper of update a task parameter action.
 * 
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class UpdateTaskParameterVisualAction extends AbstractVisualAction<Dialog> {
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private UpdateTaskParameterAction updateTaskParameterAction;
    /**
     * Dialog to update the task parameter
     */
    private ConfirmDialog wdwUpdateParameter;

    public UpdateTaskParameterVisualAction() {
        super(TaskManagerModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        Task task;
        StringPair parameter = null;
        if (parameters.containsKey("task")) {
            task = (Task) parameters.get("task");
            
            if (parameters.containsKey("parameter"))
                parameter = (StringPair) parameters.get("parameter");
            
            TextField txtName = new TextField(ts.getTranslatedString("module.taskman.task.parameters.name"));
            txtName.setRequiredIndicatorVisible(true);
            txtName.setWidthFull();
            
            TextField txtValue = new TextField(ts.getTranslatedString("module.taskman.task.parameters.value"));
            txtValue.setRequiredIndicatorVisible(true);
            txtValue.setWidthFull();
            
            if (parameter != null) {
                txtName.setValue(parameter.getKey());
                txtValue.setValue(parameter.getValue());
            }
            
            wdwUpdateParameter = new ConfirmDialog(ts, this.updateTaskParameterAction.getDisplayName());
            
            wdwUpdateParameter.getBtnConfirm().addClickListener(event -> {
                try {
                    if (txtName.getValue() == null || txtName.getValue().isEmpty())
                        this.notificationEmptyFields(ts.getTranslatedString("module.taskman.task.parameters.name"));
                    else if (txtValue.getValue() == null || txtValue.getValue().isEmpty())
                        this.notificationEmptyFields(ts.getTranslatedString("module.taskman.task.parameters.value"));
                    else {
                        ActionResponse actionResponse = updateTaskParameterAction.getCallback().execute(new ModuleActionParameterSet(
                                new ModuleActionParameter<>("task", task),
                                new ModuleActionParameter<>("name", txtName.getValue()),
                                new ModuleActionParameter<>("value", txtValue.getValue())
                        ));
                        if (actionResponse.containsKey("exception"))
                            throw new ModuleActionException(((Exception) actionResponse.get("exception")).getLocalizedMessage());

                        fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                                ts.getTranslatedString("module.taskman.task.actions.update-task-success"),
                                UpdateTaskParameterAction.class));
                        wdwUpdateParameter.close();
                    }
                } catch (ModuleActionException ex) {
                    fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                            ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                            ex.getMessage(), UpdateTaskParameterAction.class));
                }
            });
            // Add content to window
            wdwUpdateParameter.setContent(txtName, txtValue);          
        } else {
            ConfirmDialog errorDialog = new ConfirmDialog(ts,
                    this.getModuleAction().getDisplayName(),
                    ts.getTranslatedString("module.taskman.task.actions.delete-task-error")
            );
            errorDialog.getBtnConfirm().addClickListener(e -> errorDialog.close());
            return errorDialog;
        }
        return wdwUpdateParameter;
    }

    @Override
    public AbstractAction getModuleAction() {
        return updateTaskParameterAction;
    }
    
    private void notificationEmptyFields(String field) {
        new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                String.format(ts.getTranslatedString("module.general.messages.attribute-not-empty"), field),
                AbstractNotification.NotificationType.WARNING, ts).open();
    }
}