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
import org.neotropic.kuwaiba.core.configuration.proxies.ProxyManagerModule;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.neotropic.util.visual.selectors.BusinessObjectSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Visual wrapper of relate object action.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class AssociateObjectToProxyVisualAction extends AbstractVisualAdvancedAction {
    /**
     * Parameter business object.
     */
    public static String PARAM_BUSINESS_OBJECT = "businessObject"; //NOI18N
    /**
     * Parameter proxy.
     */
    public static String PARAM_PROXY = "proxy"; //NOI18N
    /**
     * Reference to the translation service.
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
    private AssociateObjectToProxyAction relateObjectToProxyAction;
    /**
     * Window to relate object with a proxy
     */
    private ConfirmDialog wdwRelate;
    /**
     * Target proxy;
     */
    private BusinessObjectLight targetProxy;

    public AssociateObjectToProxyVisualAction() {
        super(ProxyManagerModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        if (parameters.containsKey(PARAM_BUSINESS_OBJECT)) {
            BusinessObjectLight selectedObject = (BusinessObjectLight) parameters.get(PARAM_BUSINESS_OBJECT);
            BusinessObjectSelector lytContent = new BusinessObjectSelector(
                    ts.getTranslatedString("module.configman.proxies.actions.relate-object-to-proxy.placeholder"),
                    false, aem, bem, mem, ts, Constants.CLASS_GENERICPROXY);

            wdwRelate = new ConfirmDialog(ts,
                    String.format(ts.getTranslatedString("module.configman.proxies.actions.relate-object-to-proxy.title"),
                            selectedObject.toString()));
            wdwRelate.getBtnConfirm().setEnabled(false);
            wdwRelate.setWidth("60%");

            wdwRelate.getBtnConfirm().addClickListener((event) -> {
                if (targetProxy != null) {
                    try {
                        if (mem.isSubclassOf(Constants.CLASS_GENERICPROXY, targetProxy.getClassName())) {
                            relateObjectToProxyAction.getCallback().execute(new ModuleActionParameterSet(
                                    new ModuleActionParameter<>(PARAM_BUSINESS_OBJECT, selectedObject),
                                    new ModuleActionParameter<>(PARAM_PROXY, targetProxy)));

                            fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                    ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                                    String.format(ts.getTranslatedString("module.configman.proxies.actions.associate-object-to-proxy.success"),
                                            selectedObject.toString()),
                                    AssociateObjectToProxyAction.class));

                            wdwRelate.close();
                        }
                    } catch (ModuleActionException | MetadataObjectNotFoundException ex) {
                        fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                                ex.getMessage(), ReleaseProjectAction.class));
                    }
                }
            });
            
            lytContent.addSelectedObjectChangeListener(event -> {
                try {
                    targetProxy = event.getSelectedObject();
                    wdwRelate.getBtnConfirm().setEnabled(targetProxy != null && mem.isSubclassOf(Constants.CLASS_GENERICPROXY, targetProxy.getClassName()));
                } catch (MetadataObjectNotFoundException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(),
                            AbstractNotification.NotificationType.ERROR, ts).open();
                }
            });
            // Add content
            wdwRelate.add(lytContent);
            return wdwRelate;
        } else {
            ConfirmDialog errorDialog = new ConfirmDialog(ts,
                    this.getModuleAction().getDisplayName(),
                    String.format(ts.getTranslatedString("module.general.messages.parameter-not-found"), PARAM_BUSINESS_OBJECT)
            );
            errorDialog.getBtnConfirm().addClickListener(e -> errorDialog.close());
            return errorDialog;
        }
    }

    @Override
    public AbstractAction getModuleAction() {
        return relateObjectToProxyAction;
    }
    
    @Override
    public String appliesTo() {
        return Constants.CLASS_INVENTORYOBJECT;
    }
    
    @Override
    public int getRequiredSelectedObjects() {
        return 1;
    }
}