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
package org.neotropic.kuwaiba.modules.optional.taskman.actions;

import javax.annotation.PostConstruct;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.Privilege;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Add a new task user
 * @author Mauricio Ruiz {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class NewTaskUserAction extends AbstractAction {
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the application entity manager.
     */
    @Autowired
    private ApplicationEntityManager aem;
    
    @PostConstruct
    protected void init() {
        this.id = "taskman.new-task-user";
        this.displayName = ts.getTranslatedString("module.taskman.task.actions.new-task-user.name");
        this.description = ts.getTranslatedString("module.taskman.task.actions.new-task-user.description");
        this.order = 1000;

        setCallback((parameters) -> {
            long userId = (long) parameters.get("userId");
            long taskId = (long) parameters.get("taskId");
            ActionResponse actionResponse = new ActionResponse();

            try {
                aem.subscribeUserToTask(userId, taskId);
            } catch (InvalidArgumentException  | ApplicationObjectNotFoundException ex) {
                actionResponse.put("exception", ex);
            }
            return actionResponse;
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