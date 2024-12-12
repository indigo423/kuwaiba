/*
 * Copyright 2010-2024 Neotropic SAS<contact@neotropic.co>.
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
package org.neotropic.kuwaiba.modules.optional.physcon.actions;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.ShortcutRegistration;
import com.vaadin.flow.component.dialog.Dialog;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractDeleteAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.optional.physcon.PhysicalConnectionsModule;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Visual action to delete a physical link.
 * 
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class DeletePhysicalLinkVisualAction extends AbstractDeleteAction {

    /**
     * The parameter of the business object.
     */
    public static String PARAM_BUSINESS_OBJECT = "businessObject"; //NOI18N
    /**
     * Reference to the Translation Service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private DeletePhysicalLinkAction deletePhysicalLinkAction;
    
    public DeletePhysicalLinkVisualAction() {
        super(PhysicalConnectionsModule.MODULE_ID);
    }

    @Override
    public int getRequiredSelectedObjects() {
        return 1;
    }

    @Override
    public  Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        BusinessObjectLight businessObject = (BusinessObjectLight) parameters.get(PARAM_BUSINESS_OBJECT);
        ConfirmDialog wdwDelete = new ConfirmDialog(ts, getModuleAction().getDisplayName(),
                String.format(ts.getTranslatedString("module.physcon.actions.delete-physical-container.confirmation-message"),
                        businessObject));

        ShortcutRegistration btnOkShortcut = wdwDelete.getBtnConfirm().addClickShortcut(Key.ENTER).listenOn(wdwDelete);
        wdwDelete.getBtnConfirm().addClickListener(event -> {
            try {
                deletePhysicalLinkAction.getCallback().execute(new ModuleActionParameterSet(
                        new ModuleActionParameter<>(DeletePhysicalLinkAction.PARAM_OBJECT_CLASS_NAME, businessObject.getClassName()),
                        new ModuleActionParameter<>(DeletePhysicalLinkAction.PARAM_OBJECT_ID, businessObject.getId())
                ));

                ActionResponse actionResponse = new ActionResponse();
                actionResponse.put(ActionResponse.ActionType.REMOVE, "");
                actionResponse.put(PARAM_BUSINESS_OBJECT, businessObject);

                wdwDelete.close();

                fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                        ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                        ts.getTranslatedString("module.physcon.actions.delete-physical-container.success"),
                        DeletePhysicalLinkAction.class, actionResponse));

            } catch (ModuleActionException ex) {
                fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                        ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                        ex.getMessage(), DeletePhysicalLinkVisualAction.class)
                );
                wdwDelete.close();
            }
            btnOkShortcut.remove();
            event.unregisterListener();
        });

        return wdwDelete;
    }

    @Override
    public AbstractAction getModuleAction() {
        return deletePhysicalLinkAction;
    }
    
    @Override
    public String appliesTo() {
        return Constants.CLASS_GENERICPHYSICALLINK;
    }
}