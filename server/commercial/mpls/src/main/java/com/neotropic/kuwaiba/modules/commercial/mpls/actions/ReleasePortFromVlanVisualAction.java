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
package com.neotropic.kuwaiba.modules.commercial.mpls.actions;

import com.neotropic.kuwaiba.modules.commercial.mpls.MplsModule;
import com.vaadin.componentfactory.theme.EnhancedDialogVariant;
import com.vaadin.flow.component.dialog.Dialog;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAdvancedAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Front-end to the release port from VLAN action.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class ReleasePortFromVlanVisualAction extends AbstractVisualAdvancedAction {
    /**
     * Reference to the Translation Service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private ReleasePortFromVlanAction releasePortFromVlanAction;
    /**
     * Parameter business object.
     */
    public static String PARAM_BUSINESS_OBJECT = "businessObject"; //NOI18N
    
    public ReleasePortFromVlanVisualAction() {
        super(MplsModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        if (parameters.containsKey(PARAM_BUSINESS_OBJECT)) {
            BusinessObjectLight selectedPort = (BusinessObjectLight) parameters.get(PARAM_BUSINESS_OBJECT);
            if (parameters.containsKey(ReleasePortFromVlanAction.PARAM_VLAN)) {
                BusinessObjectLight selectedVlan = (BusinessObjectLight) parameters.get(ReleasePortFromVlanAction.PARAM_VLAN);
                ConfirmDialog wdwRelease = new ConfirmDialog(ts,
                        String.format(ts.getTranslatedString("module.mpls.actions.release-port-from-vlan.header"),
                                selectedPort.getName(), selectedVlan.getName()),
                        ts.getTranslatedString("module.mpls.actions.release-port-from-vlan.confirm"));
                wdwRelease.setThemeVariants(EnhancedDialogVariant.SIZE_SMALL);

                wdwRelease.getBtnConfirm().addClickListener(event -> {
                    try {
                        releasePortFromVlanAction.getCallback()
                                .execute(new ModuleActionParameterSet(
                                        new ModuleActionParameter<>(ReleasePortFromVlanAction.PARAM_PORT, selectedPort),
                                         new ModuleActionParameter<>(ReleasePortFromVlanAction.PARAM_VLAN, selectedVlan)));

                        fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                                String.format(ts.getTranslatedString("module.mpls.actions.relate-port-to-vlan.success"),
                                        selectedPort.getName(), selectedVlan.getName()),
                                ReleasePortFromVlanAction.class));
                        wdwRelease.close();
                    } catch (ModuleActionException ex) {
                        fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                                ex.getMessage(), ReleasePortFromVlanAction.class));
                    }
                });

                return wdwRelease;
            } else {
                ConfirmDialog errorDialog = new ConfirmDialog(ts,
                        this.releasePortFromVlanAction.getDisplayName(),
                        String.format(ts.getTranslatedString(
                                "module.general.messages.parameter-not-found"), ReleasePortFromVlanAction.PARAM_VLAN));
                errorDialog.getBtnConfirm().addClickListener(e -> errorDialog.close());
                return errorDialog;
            }

        } else {
            ConfirmDialog errorDialog = new ConfirmDialog(ts,
                    this.releasePortFromVlanAction.getDisplayName(),
                    String.format(ts.getTranslatedString(
                            "module.general.messages.parameter-not-found"), PARAM_BUSINESS_OBJECT));
            errorDialog.getBtnConfirm().addClickListener(e -> errorDialog.close());
            return errorDialog;
        }
    }

    @Override
    public AbstractAction getModuleAction() {
        return releasePortFromVlanAction;
    }
    
    @Override
    public String appliesTo() {
        return Constants.CLASS_GENERICPORT;
    }
    
    @Override
    public int getRequiredSelectedObjects() {
        return 1;
    }
}