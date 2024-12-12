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
package com.neotropic.kuwaiba.modules.commercial.whman.actions;

import com.neotropic.kuwaiba.modules.commercial.whman.WarehousesManagerModule;
import com.neotropic.kuwaiba.modules.commercial.whman.persistence.WarehousesService;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.Command;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Visual wrapper of create a new warehouse action.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class NewWarehouseVisualAction extends AbstractVisualAction<Dialog> {
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
    private NewWarehouseAction newWarehouseAction;
    /**
     * Reference to the Warehouses Services
     */
    @Autowired
    private WarehousesService ws;
    /**
     * Dialog to create new warehouse
     */
    private ConfirmDialog wdwNewWarehouse;
    /**
     * Object to save the selected pool
     */
    private InventoryObjectPool selectedPool;

    public NewWarehouseVisualAction() {
        super(WarehousesManagerModule.MODULE_ID);
    }
    
    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        if (parameters.containsKey("pool")) {
                selectedPool = (InventoryObjectPool) parameters.get("pool");
                commandClose = (Command) parameters.get("commandClose");

                TextField txtName = new TextField(ts.getTranslatedString("module.general.labels.name"));
                txtName.setRequiredIndicatorVisible(true);
                txtName.setSizeFull();
                // Dialog
            wdwNewWarehouse = new ConfirmDialog(ts, String.format(
                    ts.getTranslatedString("module.whman.actions.warehouses.new-warehouse.name-in"),
                    selectedPool.getName()));
                wdwNewWarehouse.getBtnConfirm().addClickListener( (event) -> {
                    try {
                        if (txtName.getValue() == null || txtName.getValue().trim().isEmpty())
                            new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                                    String.format(ts.getTranslatedString("module.general.messages.attribute-not-empty"),
                                            ts.getTranslatedString("module.general.labels.name")),
                                    AbstractNotification.NotificationType.WARNING, ts).open();
                        else {
                            newWarehouseAction.getCallback().execute(new ModuleActionParameterSet(
                                    new ModuleActionParameter<>("pool", selectedPool),
                                    new ModuleActionParameter<>("name", txtName.getValue())
                            ));
                            wdwNewWarehouse.close();
                            //refresh related grid
                            getCommandClose().execute();
                            fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                    ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                                    ts.getTranslatedString("module.whman.actions.warehouses.new-warehouse.success"),
                                    NewWarehouseAction.class));
                        }
                    } catch (ModuleActionException ex) {
                        fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                                ex.getMessage(), NewWarehouseAction.class));
                    }
                });
                // Add content to window
            wdwNewWarehouse.setContent(txtName);
            return wdwNewWarehouse;
        } else {
            ConfirmDialog errorDialog = new ConfirmDialog(ts,
                    this.getModuleAction().getDisplayName(),
                    String.format(ts.getTranslatedString("module.general.messages.parameter-not-found"), "pool")
            );
            errorDialog.getBtnConfirm().addClickListener(e -> errorDialog.close());
            return errorDialog;
        }
    }

    @Override
    public AbstractAction getModuleAction() {
        return newWarehouseAction;
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