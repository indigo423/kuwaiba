/*
 *  Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.neotropic.kuwaiba.modules.commercial.whman.actions;

import com.neotropic.kuwaiba.modules.commercial.whman.WarehousesManagerModule;
import com.vaadin.flow.component.combobox.ComboBox;
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
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadata;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadataLight;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Visual wrapper of create a new object action.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class NewSparePartVisulaAction extends AbstractVisualAction<Dialog> {
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
    private NewSparePartAction newSparePartAction;
    /**
     * Reference to the metadata entity manager.
     */
    @Autowired
    private MetadataEntityManager mem;
    /**
     * Dialog to create new spare part
     */
    private ConfirmDialog wdwNewObject;

    public NewSparePartVisulaAction() {
        super(WarehousesManagerModule.MODULE_ID);
    }
    
    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        InventoryObjectPool selectedPool;
        if (parameters.containsKey("sparePool")) {
            try {
                selectedPool = (InventoryObjectPool) parameters.get("sparePool");
                commandClose = (Command) parameters.get("commandClose");
                boolean cmbClassesEnable;

                ComboBox<ClassMetadataLight> cmbClasses = new ComboBox<>(ts.getTranslatedString("module.general.labels.type"));
                cmbClasses.setAllowCustomValue(false);
                cmbClasses.setRequiredIndicatorVisible(true);
                cmbClasses.setSizeFull();
                
                ClassMetadata poolClass = mem.getClass(selectedPool.getClassName());
                if (poolClass.getName().equals(Constants.CLASS_INVENTORYOBJECT) || poolClass.isAbstract()) {
                    cmbClasses.setItems(mem.getSubClassesLight(poolClass.getName(), false, false));
                    cmbClassesEnable = true;
                } else
                    cmbClassesEnable = false;

                TextField txtName = new TextField(ts.getTranslatedString("module.general.labels.name"));
                txtName.setRequiredIndicatorVisible(true);
                txtName.setSizeFull();
                
                TextField txtDescription = new TextField(ts.getTranslatedString("module.general.labels.description"));
                txtDescription.setSizeFull();
                // Dialog
                wdwNewObject = new ConfirmDialog(ts,
                        !cmbClassesEnable ? String.format(ts.getTranslatedString("module.whman.actions.spare.new-spare-part.name-in-type"),
                                selectedPool.getName(), selectedPool.getClassName())
                                : String.format(ts.getTranslatedString("module.whman.actions.spare.new-spare-part.name-in"),
                                selectedPool.getName())
                );

                wdwNewObject.getBtnConfirm().addClickListener((event) -> {
                    try {
                        if (cmbClassesEnable && cmbClasses.getValue() == null)
                            notificationEmptyFields(ts.getTranslatedString("module.general.labels.type"));
                        else if (txtName.getValue() == null || txtName.getValue().trim().isEmpty())
                            notificationEmptyFields(ts.getTranslatedString("module.general.labels.name"));
                        else {
                            newSparePartAction.getCallback().execute(new ModuleActionParameterSet(
                                    new ModuleActionParameter<>("sparePool", selectedPool),
                                    new ModuleActionParameter<>("className",
                                            cmbClassesEnable ? cmbClasses.getValue().getName()
                                                    : selectedPool.getClassName()),
                                    new ModuleActionParameter<>(Constants.PROPERTY_NAME, txtName.getValue()),
                                    new ModuleActionParameter<>(Constants.PROPERTY_DESCRIPTION, txtDescription.getValue())
                            ));
                            wdwNewObject.close();
                            //refresh related grid
                            getCommandClose().execute();
                            fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                    ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                                    ts.getTranslatedString("module.whman.actions.spare.new-spare-part.success"),
                                    NewSparePartAction.class));
                        }
                    } catch (ModuleActionException ex) {
                        fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                                ex.getMessage(), NewSparePartAction.class));
                    }
                });

                if (cmbClassesEnable)
                    wdwNewObject.setContent(cmbClasses, txtName, txtDescription);
                else
                    wdwNewObject.setContent(txtName, txtDescription);
            } catch (MetadataObjectNotFoundException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            }
        } else {
            ConfirmDialog errorDialog = new ConfirmDialog(ts,
                    this.getModuleAction().getDisplayName(),
                    String.format(ts.getTranslatedString("module.general.messages.parameter-not-found"), "sparePool")
            );
            errorDialog.getBtnConfirm().addClickListener(e -> errorDialog.close());
            return errorDialog;
        }
        return wdwNewObject;
    }

    private void notificationEmptyFields(String field) {
        new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                String.format(ts.getTranslatedString("module.general.messages.attribute-not-empty"), field),
                AbstractNotification.NotificationType.WARNING, ts).open();
    }

    @Override
    public AbstractAction getModuleAction() {
        return newSparePartAction;
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