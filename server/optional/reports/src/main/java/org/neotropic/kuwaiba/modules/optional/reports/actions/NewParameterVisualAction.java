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
package org.neotropic.kuwaiba.modules.optional.reports.actions;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.reporting.ReportMetadataLight;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.optional.reports.ReportsModule;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Visual wrapper of create a new report parameter action.
 * 
 * @author Orlando Paz {@literal <orlando.paz@kuwaiba.org>}
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class NewParameterVisualAction extends AbstractVisualAction<Dialog> {
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
    private NewParameterAction newTaskParameterAction;

    public NewParameterVisualAction() {
        super(ReportsModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        if (parameters.containsKey("report")) {
            ReportMetadataLight selectedReport = (ReportMetadataLight) parameters.get("report");

            TextField txtParameterName = new TextField(ts.getTranslatedString("module.reporting.parameters.name"));
            txtParameterName.setRequiredIndicatorVisible(true);
            txtParameterName.setWidthFull();

            ConfirmDialog wdwParameter = new ConfirmDialog(ts, newTaskParameterAction.getDisplayName());
            wdwParameter.getBtnConfirm().addClickListener(event -> {
                try {
                    if (txtParameterName.getValue() == null || txtParameterName.getValue().trim().isEmpty())
                        new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                                String.format(ts.getTranslatedString("module.general.messages.attribute-not-empty"),
                                        ts.getTranslatedString("module.reporting.parameters.name")),
                                AbstractNotification.NotificationType.WARNING, ts).open();
                    else {
                        newTaskParameterAction.getCallback().execute(new ModuleActionParameterSet(
                                new ModuleActionParameter<>("report", selectedReport.getId()),
                                new ModuleActionParameter<>("name", txtParameterName.getValue())
                        ));

                        fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                                ts.getTranslatedString(ts.getTranslatedString("module.reporting.actions.parameter-created")),
                                NewParameterAction.class));
                        wdwParameter.close();
                    }
                } catch (ModuleActionException ex) {
                    fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                            ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                            ex.getMessage(), NewParameterAction.class));
                }
            });

            wdwParameter.setContent(txtParameterName);
            return wdwParameter;
        } else
            return new ConfirmDialog(ts,
                    String.format(ts.getTranslatedString("module.general.messages.parameter-not-found"),
                            "report"));
    }

    @Override
    public AbstractAction getModuleAction() {
        return newTaskParameterAction;
    }
}