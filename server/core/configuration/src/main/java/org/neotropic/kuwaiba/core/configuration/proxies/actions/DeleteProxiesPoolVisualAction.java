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
package org.neotropic.kuwaiba.core.configuration.proxies.actions;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.server.Command;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.configuration.proxies.ProxyManagerModule;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Visual wrapper of delete proxies pool action.
 * @author Mauricio Ruiz {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class DeleteProxiesPoolVisualAction extends AbstractVisualAction<Dialog> {
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private DeleteProxiesPoolAction deleteProxiesPoolAction;
    /**
     * Close action command from main ui
     */
    private Command commandClose;
    /**
     * Close action command from main Dialog
     */
    private Command commandDelete;

    public DeleteProxiesPoolVisualAction() {
        super(ProxyManagerModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        InventoryObjectPool selectedPool;
        if (parameters.containsKey("pool")) {
            selectedPool = (InventoryObjectPool) parameters.get("pool");
            commandClose = (Command) parameters.get("commandClose");
            commandDelete = (Command) parameters.get("commandDelete");
            
            ConfirmDialog wdwDeletePool = new ConfirmDialog(ts,
                    this.deleteProxiesPoolAction.getDisplayName(),
                    String.format(ts.getTranslatedString("module.configman.proxies.actions.delete-pool.confirm"),
                            selectedPool.getName()));
            
            wdwDeletePool.getBtnConfirm().addClickListener((event) -> {
                try {
                    deleteProxiesPoolAction.getCallback().execute(new ModuleActionParameterSet(
                            new ModuleActionParameter<>("pool", selectedPool)));
                    //refresh related grid
                    getCommandClose().execute();
                    commandDelete.execute();
                    fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                            ts.getTranslatedString("module.configman.proxies.actions.delete-pool.success"), DeleteProxiesPoolAction.class));
                } catch (ModuleActionException ex) {
                    fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                            ex.getMessage(), DeleteProxiesPoolAction.class));
                }
                wdwDeletePool.close();
            });
            return wdwDeletePool;
        } else {
            ConfirmDialog errorDialog = new ConfirmDialog(ts,
                    this.getModuleAction().getDisplayName(),
                    ts.getTranslatedString("module.configman.proxies.actions.delete-pool.error-param")
            );
            errorDialog.getBtnConfirm().addClickListener(e -> errorDialog.close());
            return errorDialog;
        }
    }

    @Override
    public AbstractAction getModuleAction() {
        return deleteProxiesPoolAction;
    }
    
    /**
     * refresh grid
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