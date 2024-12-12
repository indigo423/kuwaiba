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
package org.neotropic.kuwaiba.modules.commercial.contractman.actions;

import com.vaadin.flow.component.combobox.ComboBox;
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
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadataLight;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.commercial.contractman.ContractManagerModule;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Visual wrapper of create a new contracts pool action.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class NewContractsPoolVisualAction extends AbstractVisualAction<Dialog> {
    /**
     * Close action command
     * -- GETTER --
     *  refresh grid
     *
     * @return commandClose; refresh action

     */
    @Getter
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
    private NewContractsPoolAction newContractsPoolAction;
    /**
     * Reference to the Metadata Entity Manager.
     */
    @Autowired
    private MetadataEntityManager mem;
    /**
     * ComboBox for classes 
     */
    private ComboBox<ClassMetadataLight> cmbClasses;

    public NewContractsPoolVisualAction() {
        super(ContractManagerModule.MODULE_ID);
    }
    
    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        commandClose = (Command) parameters.get("commandClose");

        TextField txtName = new TextField(ts.getTranslatedString("module.contractman.pool.label.name"));
        txtName.setRequiredIndicatorVisible(true);
        txtName.setSizeFull();
        TextField txtDescription = new TextField(ts.getTranslatedString("module.contractman.pool.label.description"));
        txtDescription.setSizeFull();
        try {
            List<ClassMetadataLight> classes = mem.getSubClassesLight(Constants.CLASS_GENERICCONTRACT, true, true);
            cmbClasses = new ComboBox<>(ts.getTranslatedString("module.contractman.pool.label.type"));
            cmbClasses.setItems(classes);
            cmbClasses.setSizeFull();
            cmbClasses.setAllowCustomValue(false);
            cmbClasses.setRequiredIndicatorVisible(true);
        } catch (MetadataObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(),
                    AbstractNotification.NotificationType.ERROR, ts).open();
        }
        // Dialog
        ConfirmDialog wdwContractsPool = new ConfirmDialog(ts, this.newContractsPoolAction.getDisplayName());        
        wdwContractsPool.getBtnConfirm().addClickListener(event -> {
            try {
                if (txtName.getValue() == null || txtName.getValue().trim().isEmpty())
                    notificationEmptyFields(ts.getTranslatedString("module.contractman.pool.label.name"));
                else if (cmbClasses.getValue() == null)
                    notificationEmptyFields(ts.getTranslatedString("module.contractman.pool.label.type"));
                else {
                    ClassMetadataLight poolType = (ClassMetadataLight) cmbClasses.getValue();
                    newContractsPoolAction.getCallback().execute(new ModuleActionParameterSet(
                            new ModuleActionParameter<>(Constants.PROPERTY_NAME, txtName.getValue()),
                            new ModuleActionParameter<>(Constants.PROPERTY_DESCRIPTION, txtDescription.getValue()),
                            new ModuleActionParameter<>("class", poolType.getName())
                    ));
                    fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                            ts.getTranslatedString("module.contractman.actions.pool.new-pool-success"), NewContractsPoolAction.class));
                    wdwContractsPool.close();
                    // Refresh related grid
                    getCommandClose().execute();
                    if (parameters.containsKey("commandAdd")) {
                        Command commandAdd = (Command) parameters.get("commandAdd");
                        commandAdd.execute();
                    }
                }
            } catch (ModuleActionException ex) {
                fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                        ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                        ex.getMessage(), NewContractsPoolAction.class));
            }
        });
        wdwContractsPool.setContent(txtName, txtDescription, cmbClasses);        
        return wdwContractsPool;
    }

    private void notificationEmptyFields(String field) {
        new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                String.format(ts.getTranslatedString("module.general.messages.attribute-not-empty"), field),
                AbstractNotification.NotificationType.WARNING, ts).open();
    }

    @Override
    public AbstractAction getModuleAction() {
        return newContractsPoolAction;
    }
}