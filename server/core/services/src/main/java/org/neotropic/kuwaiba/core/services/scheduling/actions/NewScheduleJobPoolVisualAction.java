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
import com.vaadin.flow.component.textfield.TextField;
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
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Visual wrapper of create a new schedule job pool action.
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */
@Component
public class NewScheduleJobPoolVisualAction extends AbstractVisualAction<Dialog> {
    /**
     * Action command to close after finish add schedule job pool
     */
    @Getter
    private Command commandClose;
    /**
     * Action command after finish add schedule job pool
     */
    private Command commandAdd;
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private NewScheduleJobPoolAction addPoolAction;

    public NewScheduleJobPoolVisualAction() {
        super(SchedulingModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        commandClose = (Command) parameters.get("commandClose");
        commandAdd = (Command) parameters.get("commandAdd");

        TextField txtName = new TextField(ts.getTranslatedString("module.scheduleJob.ui.actions.label.name"));
        txtName.setRequiredIndicatorVisible(true);
        txtName.setSizeFull();

        TextField txtDescription = new TextField(ts.getTranslatedString("module.scheduleJob.ui.actions.label.description"));
        txtDescription.setSizeFull();

        ConfirmDialog windowNewSchedulePool = new ConfirmDialog(ts, this.getModuleAction().getDisplayName());
        windowNewSchedulePool.getBtnConfirm().addClickListener((event) ->{
            try {
                addPoolAction.getCallback().execute(new ModuleActionParameterSet(
                        new ModuleActionParameter<>(Constants.PROPERTY_NAME, txtName.getValue()),
                        new ModuleActionParameter<>(Constants.PROPERTY_DESCRIPTION, txtDescription.getValue())
                ));

                fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                        ts.getTranslatedString("module.scheduleJob.ui.actions.new-pool-created-success"), NewScheduleJobPoolAction.class));
                windowNewSchedulePool.close();
                //refresh grib
                getCommandClose().execute();
                commandAdd.execute();

            } catch (ModuleActionException ex) {
                fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                        ex.getMessage(), NewScheduleJobPoolAction.class));
            }
        });

        windowNewSchedulePool.getBtnConfirm().setEnabled(false);
        txtName.addValueChangeListener((event) -> {
            windowNewSchedulePool.getBtnConfirm().setEnabled(!txtName.isEmpty());
        });

        windowNewSchedulePool.setContent(txtName, txtDescription);

        return windowNewSchedulePool;
    }

    @Override
    public AbstractAction getModuleAction() {
        return addPoolAction;
    }

    /**
     * @param commandClose;Command; refresh action
     */
    public void setCommandClose(Command commandClose) {
        this.commandClose = commandClose;
    }
}
