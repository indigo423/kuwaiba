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
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.Task;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.optional.taskman.TaskManagerModule;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Visual wrapper of create a new task parameter action.
 * 
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class NewTaskParameterVisualAction extends AbstractVisualAction<Dialog> {
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the application entity manager.
     */
    @Autowired
    protected ApplicationEntityManager aem;
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private NewTaskParameterAction newTaskParameterAction;

    public NewTaskParameterVisualAction() {
        super(TaskManagerModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        if (parameters.containsKey("task")) {
            Task selectedTask = (Task) parameters.get("task");

            TextField txtParameterName = new TextField(ts.getTranslatedString("module.taskman.task.parameters.name"));
            txtParameterName.setRequiredIndicatorVisible(true);
            txtParameterName.setWidthFull();

            TextField txtParameterValue = new TextField(ts.getTranslatedString("module.taskman.task.parameters.value"));
            txtParameterValue.setRequiredIndicatorVisible(true);
            txtParameterValue.setWidthFull();

            // Window to create a new task parameter
            ConfirmDialog wdwNewTaskParameter = new ConfirmDialog(ts,
                    String.format(ts.getTranslatedString("module.taskman.task.actions.new-task-parameter.for"),
                            selectedTask.getName()));

            wdwNewTaskParameter.getBtnConfirm().addClickListener(event -> {
                try {
                    if (txtParameterName.getValue() == null || txtParameterName.getValue().isEmpty())
                        this.notificationEmptyFields(ts.getTranslatedString("module.taskman.task.parameters.name"));
                    else if (txtParameterValue.getValue() == null || txtParameterValue.getValue().isEmpty())
                        this.notificationEmptyFields(ts.getTranslatedString("module.taskman.task.parameters.value"));
                    else {
                        newTaskParameterAction.getCallback().execute(new ModuleActionParameterSet(
                                new ModuleActionParameter<>("taskId", selectedTask.getId()),
                                new ModuleActionParameter<>("name", txtParameterName.getValue()),
                                new ModuleActionParameter<>("value", txtParameterValue.getValue())
                        ));

                        fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                                ts.getTranslatedString("module.taskman.task.actions.new-task-parameter-success"),
                                NewTaskParameterAction.class));
                        wdwNewTaskParameter.close();
                    }
                } catch (ModuleActionException ex) {
                    fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                            ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                            ex.getMessage(), NewTaskParameterAction.class));
                }
            });

            // Add content to window
            wdwNewTaskParameter.setContent(txtParameterName, txtParameterValue);
            return wdwNewTaskParameter;
        }
        return null;
    }

    @Override
    public AbstractAction getModuleAction() {
        return newTaskParameterAction;
    } 
    
    private void notificationEmptyFields(String field) {
        new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                String.format(ts.getTranslatedString("module.general.messages.attribute-not-empty"), field),
                AbstractNotification.NotificationType.WARNING, ts).open();
    }
}