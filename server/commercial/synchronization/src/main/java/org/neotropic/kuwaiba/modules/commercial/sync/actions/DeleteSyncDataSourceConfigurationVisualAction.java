/*
 * Copyright 2010-2024. Neotropic SAS <contact@neotropic.co>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neotropic.kuwaiba.modules.commercial.sync.actions;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.server.Command;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.commercial.sync.SynchronizationModule;
import org.neotropic.kuwaiba.modules.commercial.sync.model.SyncDataSourceConfiguration;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Visual wrapper of delete Synchronization Data Source Configuration action.
 *
 * @author Mauricio Ruiz {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class DeleteSyncDataSourceConfigurationVisualAction extends AbstractVisualAction<Dialog> {
    /**
     * Parameter, data source configuration.
     */
    public static String PARAM_SYNC_DATA_SOURCE = "syncDataSourceConfiguration"; //NOI18N
    /**
     * Parameter command close.
     */
    public static String PARAM_COMMANDCLOSE = "commandClose"; //NOI18N
    /**
     * Parameter exception.
     */
    public static String PARAM_EXCEPTION = "exception"; //NOI18N
    /**
     * Close action command
     */
    private Command commandClose;
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private DeleteSyncDataSourceConfigurationAction deleteSyncDataSourceConfigurationAction;

    public DeleteSyncDataSourceConfigurationVisualAction() {
        super(SynchronizationModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        SyncDataSourceConfiguration configuration;
        if (parameters.containsKey(PARAM_SYNC_DATA_SOURCE)) {
            configuration = (SyncDataSourceConfiguration) parameters.get(PARAM_SYNC_DATA_SOURCE);
            commandClose = (Command) parameters.get(PARAM_COMMANDCLOSE);

            ConfirmDialog wdwDeleteConfiguration = new ConfirmDialog(ts,
                     this.deleteSyncDataSourceConfigurationAction.getDisplayName(),
                     String.format(ts.getTranslatedString("module.sync.actions.delete-sync-data-source-configuration.confirm"),
                            configuration.getName()));

            wdwDeleteConfiguration.getBtnConfirm().addClickListener((event) -> {
                try {
                    ActionResponse actionResponse
                            = deleteSyncDataSourceConfigurationAction.getCallback().execute(new ModuleActionParameterSet(
                            new ModuleActionParameter<>(PARAM_SYNC_DATA_SOURCE, configuration)));

                    if (actionResponse.containsKey(PARAM_EXCEPTION))
                        throw new ModuleActionException(((Exception) actionResponse.get(PARAM_EXCEPTION)).getLocalizedMessage());

                    fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                            ts.getTranslatedString("module.sync.actions.delete-sync-data-source-configuration.success"), DeleteSyncDataSourceConfigurationAction.class));
                    wdwDeleteConfiguration.close();
                    //refresh related grid
                    getCommandClose().execute();
                } catch (ModuleActionException ex) {
                    wdwDeleteConfiguration.close();
                    fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                            ex.getMessage(), DeleteSyncDataSourceConfigurationAction.class));
                }
            });
            return wdwDeleteConfiguration;
        } else {
            ConfirmDialog errorDialog = new ConfirmDialog(ts,
                    this.getModuleAction().getDisplayName(),
                    ts.getTranslatedString("module.sync.actions.delete-sync-data-source-configuration.error")
            );
            errorDialog.getBtnConfirm().addClickListener(e -> errorDialog.close());
            return errorDialog;
        }
    }

    @Override
    public AbstractAction getModuleAction() {
        return deleteSyncDataSourceConfigurationAction;
    }

    /**
     * refresh grid
     *
     * @return commandClose;Command; refresh action
     */
    public Command getCommandClose() {
        return commandClose;
    }

    /**
     * @param commandClose;Command; refresh action
     */
    public void setCommandClose(Command commandClose) {
        this.commandClose = commandClose;
    }
}