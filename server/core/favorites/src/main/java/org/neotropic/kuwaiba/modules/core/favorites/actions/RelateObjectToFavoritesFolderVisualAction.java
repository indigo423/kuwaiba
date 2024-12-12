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
package org.neotropic.kuwaiba.modules.core.favorites.actions;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import java.util.List;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualInventoryAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.FavoritesFolder;
import org.neotropic.kuwaiba.core.apis.persistence.application.Session;
import org.neotropic.kuwaiba.core.apis.persistence.application.UserProfile;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.favorites.FavoritesModule;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Visual wrapper of relate object action.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class RelateObjectToFavoritesFolderVisualAction extends AbstractVisualInventoryAction {
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Application Entity Manager.
     */
    @Autowired
    private ApplicationEntityManager aem;
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private RelateObjectToFavoritesFolderAction relateObjectToFavoritesFolderAction;
    /**
     * Parameter business object.
     */
    public static String PARAM_BUSINESS_OBJECT = "businessObject"; //NOI18N
    /**
     * Parameter business object.
     */
    public static String PARAM_FOLDER = "folder"; //NOI18N
    /**
     * Parameter user.
     */
    public static String PARAM_USER = "user"; //NOI18N
    
    public RelateObjectToFavoritesFolderVisualAction() {
        super(FavoritesModule.MODULE_ID);
    }

    @Override
    public int getRequiredSelectedObjects() {
        return 1;
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        if (parameters.containsKey(PARAM_BUSINESS_OBJECT)) {
            try {
                UserProfile user = UI.getCurrent().getSession().getAttribute(Session.class).getUser();
                BusinessObjectLight businessObject = (BusinessObjectLight) parameters.get(PARAM_BUSINESS_OBJECT);
            
                ConfirmDialog wdwRelate = new ConfirmDialog(ts, 
                        String.format(
                                ts.getTranslatedString("module.favorites.actions.favorite-add-objects-to-favorite.header"), 
                                businessObject.toString()
                        ));
                wdwRelate.setWidth("60%");
                
                List<FavoritesFolder> folders = aem.getFavoritesFoldersForUser(user.getId());
                ComboBox<FavoritesFolder> cmbFolders = new ComboBox<>(ts.getTranslatedString("module.favorites.filter.name")
                        , folders);
                cmbFolders.setItemLabelGenerator(item -> item.getName());
                cmbFolders.setPlaceholder(ts.getTranslatedString("module.favorites.filter.placeholder"));
                cmbFolders.setAllowCustomValue(false);
                cmbFolders.setRequiredIndicatorVisible(true);
                cmbFolders.setWidthFull();
                    
                wdwRelate.getBtnConfirm().addClickListener(event -> {
                    try {
                        if (cmbFolders.getValue() == null)
                            new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                                    String.format(ts.getTranslatedString("module.general.messages.attribute-not-empty"), 
                                            ts.getTranslatedString("module.favorites.filter.name")),
                                    AbstractNotification.NotificationType.WARNING, ts).open();
                        else {
                            relateObjectToFavoritesFolderAction.getCallback().execute(new ModuleActionParameterSet(
                                    new ModuleActionParameter<>(PARAM_BUSINESS_OBJECT, businessObject),
                                    new ModuleActionParameter<>(PARAM_FOLDER, cmbFolders.getValue()),
                                    new ModuleActionParameter<>(PARAM_USER, user)
                            ));
                            
                            fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                    ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                                    String.format(
                                            ts.getTranslatedString("module.favorites.actions.favorite-add-objects-to-favorite.success-activity"),
                                            businessObject.toString(), cmbFolders.getValue()),
                                    RelateObjectToFavoritesFolderAction.class));

                            wdwRelate.close();
                        }
                    } catch (ModuleActionException ex) {
                        fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                                ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                                ex.getMessage(), RelateObjectToFavoritesFolderAction.class));
                        }
                    });
                
                wdwRelate.setContent(cmbFolders);
                return wdwRelate;
            } catch (ApplicationObjectNotFoundException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), 
                        ex.getMessage(),AbstractNotification.NotificationType.ERROR, ts).open();
            }
        } else {
            ConfirmDialog errorDialog = new ConfirmDialog(ts,
                    this.getModuleAction().getDisplayName(),
                    String.format(ts.getTranslatedString("module.general.messages.parameter-not-found"),
                            PARAM_BUSINESS_OBJECT)
            );
            errorDialog.getBtnConfirm().addClickListener(e -> errorDialog.close());
            return errorDialog;
        }
        return null;
    }

    @Override
    public AbstractAction getModuleAction() {
        return relateObjectToFavoritesFolderAction;
    }   
}