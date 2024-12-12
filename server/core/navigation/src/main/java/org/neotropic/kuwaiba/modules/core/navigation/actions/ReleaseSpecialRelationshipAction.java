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

import com.vaadin.flow.component.UI;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.application.ActivityLogEntry;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.Privilege;
import org.neotropic.kuwaiba.core.apis.persistence.application.Session;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Releases a business object from another business object.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class ReleaseSpecialRelationshipAction extends AbstractAction {

    /**
     * Parameter by object id.
     */
    public static String PARAM_OBJECT_ID = "objectId"; //NOI18N
    /**
     * Parameter by other target id.
     */
    public static String PARAM_OTHER_OBJECT_ID = "otherObjectId"; //NOI18N
    /**
     * Parameter by other target id.
     */
    public static String PARAM_OTHER_OBJECT_CLASS = "otherObjectClass"; //NOI18N
    /**
     * Parameter by relationship name.
     */
    public static String PARAM_RELATIONSHIP_NAME = "relationshipName"; //NOI18N
    /**
     * Reference to the Business Entity Manager.
     */
    @Autowired
    private BusinessEntityManager bem;
    /**
     * Reference to the Translation Service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Application Entity Manager.
     */
    @Autowired
    private ApplicationEntityManager aem;

    @PostConstruct
    protected void init() {
        this.id = "navigation.release-special-relationship";
        this.displayName = ts.getTranslatedString("module.navigation.actions.release-special-relationship.name");
        this.description = ts.getTranslatedString("module.navigation.actions.release-special-relationship.description");
        this.order = 1;

        setCallback(parameters -> {
            try {
                String objectId = (String) parameters.get(PARAM_OBJECT_ID);
                String relationshipName = (String) parameters.get(PARAM_RELATIONSHIP_NAME);
                String otherObjectId = (String) parameters.get(PARAM_OTHER_OBJECT_ID);
                String otherObjectClass = (String) parameters.get(PARAM_OTHER_OBJECT_CLASS);

                bem.releaseSpecialRelationship(
                        otherObjectClass,
                        otherObjectId,
                        objectId,
                        relationshipName
                );

                aem.createObjectActivityLogEntry(
                        UI.getCurrent().getSession().getAttribute(Session.class).getUser().getUserName(),
                        otherObjectClass,
                        otherObjectId,
                        ActivityLogEntry.ACTIVITY_TYPE_RELEASE_RELATIONSHIP_INVENTORY_OBJECT,
                        Constants.PROPERTY_NAME,
                        relationshipName, "",
                        String.format(ts.getTranslatedString("module.navigation.actions.release-special-relationship.log"),
                                otherObjectId, objectId)
                );
                return new ActionResponse();
            } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException |
                     InvalidArgumentException | ApplicationObjectNotFoundException ex) {
                throw new ModuleActionException(ex.getMessage());
            }
        });
    }

    @Override
    public int getRequiredAccessLevel() {
        return Privilege.ACCESS_LEVEL_READ_WRITE;
    }

    @Override
    public boolean requiresConfirmation() {
        return false;
    }
}