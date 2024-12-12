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
package org.neotropic.kuwaiba.modules.core.navigation.actions;

import com.vaadin.flow.component.dialog.Dialog;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualInventoryAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.navigation.NavigationModule;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReleaseMultipleSpecialRelationshipVisualAction extends AbstractVisualInventoryAction {
    /**
     * Parameter by object.
     */
    public static String PARAM_OBJECT = "object"; //NOI18N
    /**
     * Parameter by relationship name.
     */
    public static String PARAM_RELATIONSHIP_NAME = "relationshipName"; //NOI18N
    /**
     * Parameter by other object.
     */
    public static String PARAM_OTHER_OBJECTS = "otherObjectS"; //NOI18N
    /**
     * Reference to the Translation Service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to module action.
     */
    @Autowired
    private ReleaseMultipleSpecialRelationshipAction releaseMultipleSpecialRelationshipAction;

    public ReleaseMultipleSpecialRelationshipVisualAction() {
        super(NavigationModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        BusinessObjectLight object = (BusinessObjectLight) parameters.get(PARAM_OBJECT);
        String relationshipName = (String) parameters.get(PARAM_RELATIONSHIP_NAME);
        List<BusinessObjectLight> targetObjects = (List<BusinessObjectLight>) parameters.get(PARAM_OTHER_OBJECTS);

        ConfirmDialog wdwRelease = new ConfirmDialog(ts, getModuleAction().getDisplayName(),
                String.format(ts.getTranslatedString("module.navigation.actions.release-multiple-special-relationship.confirmation-message")
                        , relationshipName));

        wdwRelease.getBtnConfirm().addClickListener(e -> {
            try {
                ModuleActionParameterSet actionParameters = new ModuleActionParameterSet(
                        new ModuleActionParameter<>(ReleaseMultipleSpecialRelationshipAction.PARAM_OBJECT_CLASS, object.getClassName()),
                        new ModuleActionParameter<>(ReleaseMultipleSpecialRelationshipAction.PARAM_OBJECT_ID, object.getId()),
                        new ModuleActionParameter<>(PARAM_OTHER_OBJECTS, targetObjects),
                        new ModuleActionParameter<>(PARAM_RELATIONSHIP_NAME, relationshipName)
                );

                ActionResponse actionResponse = releaseMultipleSpecialRelationshipAction.getCallback().execute(actionParameters);
                actionResponse.put(ActionResponse.ActionType.RELEASE, "");
                actionResponse.put(PARAM_OBJECT, object);

                fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                        ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                        String.format(ts.getTranslatedString("module.navigation.actions.release-multiple-special-relationship.success")
                                , relationshipName),
                        ReleaseMultipleSpecialRelationshipAction.class,
                        actionResponse));

                wdwRelease.close();
            } catch (ModuleActionException ex) {
                fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                        ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                        ex.getLocalizedMessage(),
                        ModuleActionException.class
                ));
            }
        });

        return wdwRelease;
    }

    @Override
    public AbstractAction getModuleAction() {
        return releaseMultipleSpecialRelationshipAction;
    }

    @Override
    public int getRequiredSelectedObjects() {
        return 1;
    }
}