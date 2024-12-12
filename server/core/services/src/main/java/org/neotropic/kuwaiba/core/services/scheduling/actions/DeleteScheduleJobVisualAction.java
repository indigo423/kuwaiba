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
package org.neotropic.kuwaiba.core.services.scheduling.actions;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.server.Command;
import lombok.Getter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.core.services.scheduling.SchedulingModule;
import org.neotropic.kuwaiba.core.services.scheduling.schemas.ExecuteJob;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Visual wrapper of delete job action
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */
@Component
public class DeleteScheduleJobVisualAction extends AbstractVisualAction<Dialog> {
    /**
     * Action command after finish delete schedule job pool
     */
    @Getter
    private Command commandDelete;
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the underlying action.
     */
    @Autowired DeleteScheduleJobAction deleteJobAction;

    public DeleteScheduleJobVisualAction() {
        super(SchedulingModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        ExecuteJob selectJob;
        String msgDialog;

        if (parameters.containsKey("scheduleJob")) {
            selectJob = (ExecuteJob) parameters.get("scheduleJob");
            commandDelete = (Command) parameters.get("commandDelete");

            if (selectJob.getState() == 1)
                msgDialog = String.format(ts.getTranslatedString("module.scheduleJob.ui.actions.delete-job-run-confirm"), selectJob.getName());
            else
                msgDialog = String.format(ts.getTranslatedString("module.scheduleJob.ui.actions.delete-job-confirm"), selectJob.getName());

            ConfirmDialog windowDeleteJob = new ConfirmDialog(ts,
                    this.deleteJobAction.getDisplayName(), msgDialog);

            windowDeleteJob.getBtnConfirm().addClickListener(event -> {
                try {
                    deleteJobAction.getCallback().execute(new ModuleActionParameterSet(
                            new ModuleActionParameter<>(Constants.PROPERTY_ID, selectJob.getJobId()),
                            new ModuleActionParameter<>("jobName", selectJob.getName())
                    ));
                    getCommandDelete().execute();
                    fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                            ts.getTranslatedString("module.scheduleJob.ui.actions.delete-job-deleted-success"), DeleteScheduleJobAction.class));
                    windowDeleteJob.close();

                } catch (ModuleActionException ex) {
                    fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                            ex.getMessage(), DeleteScheduleJobAction.class));
                    windowDeleteJob.close();
                }
            });

            return windowDeleteJob;

        } else {
            ConfirmDialog errorDialog = new ConfirmDialog(ts,
                    this.getModuleAction().getDisplayName(),
                    ts.getTranslatedString("module.scheduleJob.ui.error-param-job")
            );
            errorDialog.getBtnConfirm().addClickListener(e -> errorDialog.close());
            return errorDialog;
        }
    }

    @Override
    public AbstractAction getModuleAction() {
        return deleteJobAction;
    }
}
