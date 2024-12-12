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
import com.vaadin.flow.component.dialog.Dialog;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAdvancedAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.neotropic.util.visual.selectors.BusinessObjectSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Front-end to the relate port to VLAN action.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class RelatePortToVlanVisualAction extends AbstractVisualAdvancedAction {
    /**
     * Reference to the Translation Service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * References to the Application Entity Manager.
     */
    @Autowired
    private ApplicationEntityManager aem;
    /**
     * Reference to the Business Entity Manager.
     */
    @Autowired
    private BusinessEntityManager bem;
    /**
     * Reference to the Metadata Entity Manager.
     */
    @Autowired
    private MetadataEntityManager mem;
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private RelatePortToVlanAction relatePortToVlanAction;
    /**
     * Target VLAN.
     */
    private BusinessObjectLight targetVLAN;
    /**
     * Parameter business object.
     */
    public static String PARAM_BUSINESS_OBJECT = "businessObject"; //NOI18N
    
    public RelatePortToVlanVisualAction() {
        super(MplsModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        if (parameters.containsKey(PARAM_BUSINESS_OBJECT)) {
            BusinessObjectLight selectedPort = (BusinessObjectLight) parameters.get(PARAM_BUSINESS_OBJECT);
            
            BusinessObjectSelector lytContent = new BusinessObjectSelector(
                    ts.getTranslatedString("module.mpls.actions.relate-port-to-vlan.select"),
                    false, aem, bem, mem, ts, Constants.CLASS_VLAN);
            
            ConfirmDialog wdwRelate = new ConfirmDialog(ts,
                    String.format(ts.getTranslatedString("module.mpls.actions.relate-port-to-vlan.header"),
                            selectedPort.toString()));
            wdwRelate.getBtnConfirm().setEnabled(false);
            wdwRelate.setWidth("60%");
            
            wdwRelate.getBtnConfirm().addClickListener((event) -> {
                if (targetVLAN != null) {
                    try {
                        if (mem.isSubclassOf(Constants.CLASS_VLAN, targetVLAN.getClassName())) {
                            relatePortToVlanAction.getCallback().execute(new ModuleActionParameterSet(
                                    new ModuleActionParameter<>(RelatePortToVlanAction.PARAM_PORT, selectedPort),
                                    new ModuleActionParameter<>(RelatePortToVlanAction.PARAM_VLAN, targetVLAN)));

                            fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                    ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                                    String.format(ts.getTranslatedString(
                                            "module.mpls.actions.relate-port-to-vlan.success"),
                                            selectedPort.toString(), targetVLAN.toString()),
                                    RelatePortToVlanAction.class));

                            wdwRelate.close();
                        }
                    } catch (ModuleActionException | MetadataObjectNotFoundException ex) {
                        fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                                ex.getMessage(), RelatePortToVlanAction.class));
                    }
                }
            });
            
            lytContent.addSelectedObjectChangeListener(event -> {
                try {
                    targetVLAN = event.getSelectedObject();
                    wdwRelate.getBtnConfirm().setEnabled(targetVLAN != null
                            && mem.isSubclassOf(Constants.CLASS_VLAN, targetVLAN.getClassName()));
                } catch (MetadataObjectNotFoundException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                }
            });
            
            wdwRelate.add(lytContent);
            return wdwRelate;
        } else {
            ConfirmDialog errorDialog = new ConfirmDialog(ts,
                    this.relatePortToVlanAction.getDisplayName(),
                    String.format(ts.getTranslatedString(
                            "module.general.messages.parameter-not-found"), PARAM_BUSINESS_OBJECT));
            errorDialog.getBtnConfirm().addClickListener(e -> errorDialog.close());
            return errorDialog;
        }
    }

    @Override
    public AbstractAction getModuleAction() {
        return relatePortToVlanAction;
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