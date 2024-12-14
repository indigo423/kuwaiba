/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>.
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

package org.neotropic.kuwaiba.modules.optional.layouteditor.actions;

import javax.annotation.PostConstruct;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.application.Privilege;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.logging.LoggerType;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Creates a new layout View
 *  @author Orlando Paz {@literal <orlando.paz@kuwaiba.org>}
 */
@Component
public class NewLayoutViewAction extends AbstractAction {
    
     /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    
    /**
     * Reference to the metadata entity manager.
     */
    @Autowired
    private ApplicationEntityManager aem;
    /**
     * Reference to the Logging Service.
     */
    @Autowired
    private LoggingService log;
    
    @PostConstruct
    protected void init() {
        this.id = "layouteditor.new-view";
        this.displayName = ts.getTranslatedString("module.layout-editor.actions.new-view.name");
        this.description = ts.getTranslatedString("module.layout-editor.actions.new-view.name.description");
        this.order = 1000;
    
        setCallback((parameters) -> {
            
            try {
                String viewName = (String)parameters.get("viewName");                
                String viewDescription = (String)parameters.get("description");                
                long newViewId = aem.createLayout("", viewName, viewDescription, new byte[0], null);
                
                ActionResponse actionResponse = new ActionResponse();
                actionResponse.put("viewId", newViewId); // the id of the view created
                
                return actionResponse;
                        
            } catch (InvalidArgumentException ex) {
                log.writeLogMessage(LoggerType.ERROR, NewLayoutViewAction.class, "", ex);
                return null;
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