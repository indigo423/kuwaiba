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
package com.neotropic.kuwaiba.modules.commercial.sdh.actions;

import com.neotropic.kuwaiba.modules.commercial.sdh.SdhService;
import javax.annotation.PostConstruct;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.application.Privilege;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InventoryException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Action to delete a Sdh transport link.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class DeleteSdhTransportLinkAction extends AbstractAction {

    /**
     * The parameter of object id.
     */
    public static String PARAM_OBJECT_ID = "id"; //NOI18N
    /**
     * The parameter of object class.
     */
    public static String PARAM_OBJECT_CLASS_NAME = "class"; //NOI18N
    /**
     * Reference to the Translation Service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Sdh Service.
     */
    @Autowired
    private SdhService sdhService;
    
    @PostConstruct
    protected void init() {
        this.id = "delete-transport-link";
        this.displayName = ts.getTranslatedString("module.sdh.actions.delete-transport-link.name");
        this.description = ts.getTranslatedString("module.sdh.actions.delete-transport-link.description");
        this.order = 1000;
        
        setCallback(parameters -> {
            try {
                String className = (String) parameters.get(PARAM_OBJECT_CLASS_NAME);
                String objectId = (String) parameters.get(PARAM_OBJECT_ID);
                
                sdhService.deleteSDHTransportLink(className, objectId, true);
                return new ActionResponse();
            } catch (InventoryException ex) {
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
        return true;
    }
}