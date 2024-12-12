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
package com.neotropic.kuwaiba.modules.commercial.ipam.actions;

import com.neotropic.kuwaiba.modules.commercial.ipam.IpamModule;
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
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Visual wrapper of delete a subnet.
 * 
 * @author Adrian Martinez Molina {@literal <adrian.martnez@kuwaiba.org>}
 */
@Component
public class DeleteSubnetVisualAction extends AbstractDeleteAction {
    /**
     * Reference to the Translation Service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to module action.
     */
    @Autowired
    private DeleteSubnetAction deleteSubnetAction;
    /**
     * Parameter business object. This parameter is used to maintain compatibility with the object's options panel.
     */
    public static String PARAMETER_BUSINESS_OBJECT = "businessObject"; //NOI18N

    public DeleteSubnetVisualAction() {
        super(IpamModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        //Params
        BusinessObjectLight subnet = (BusinessObjectLight) parameters.get(PARAMETER_BUSINESS_OBJECT);
        
        //Dialog
        ConfirmDialog wdwDeleteSubnet = new ConfirmDialog(ts, getModuleAction().getDisplayName(),
                String.format(ts.getTranslatedString("module.ipam.actions.delete-subnet.confirmation"),
                        subnet.getName()));
        
        wdwDeleteSubnet.getBtnConfirm().addClickListener(e -> {
            try {
                deleteSubnetAction.getCallback().execute(new ModuleActionParameterSet(
                        new ModuleActionParameter<>(Constants.PROPERTY_ID, subnet.getId()),
                        new ModuleActionParameter<>(Constants.PROPERTY_CLASSNAME, subnet.getClassName())
                ));

                ActionResponse actionResponse = new ActionResponse();
                actionResponse.put(ActionResponse.ActionType.REMOVE, "");
                
                fireActionCompletedEvent(
                        new ActionCompletedListener.ActionCompletedEvent(
                                ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                                ts.getTranslatedString("module.ipam.actions.delete-subnet.notification"),
                                DeleteSubnetAction.class, actionResponse));

                wdwDeleteSubnet.close();
            } catch (ModuleActionException ex) {
                fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                        ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                        ex.getMessage(), DeleteSubnetAction.class));
            }
        });
        
        return wdwDeleteSubnet;
    }

    @Override
    public AbstractAction getModuleAction() {
        return deleteSubnetAction;
    }

    @Override
    public int getRequiredSelectedObjects() {
        return 1;
    }
    
    @Override
    public String appliesTo() {
        return Constants.CLASS_GENERICSUBNET;
    }
}