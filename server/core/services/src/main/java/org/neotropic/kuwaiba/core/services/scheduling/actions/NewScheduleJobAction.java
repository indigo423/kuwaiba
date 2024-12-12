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
package org.neotropic.kuwaiba.core.services.scheduling.actions;

import com.vaadin.flow.component.UI;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.application.ActivityLogEntry;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.Privilege;
import org.neotropic.kuwaiba.core.apis.persistence.application.Session;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ExecutionException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.core.services.scheduling.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import org.neotropic.kuwaiba.modules.core.logging.LoggerType;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

/**
 * Create a new Schedule Job.
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */
@Component
public class NewScheduleJobAction extends AbstractAction {
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
    /**
     * Reference to the scheduling service.
     */
    @Autowired
    private SchedulingService shs;
    /**
     * Reference to the logging service.
     */
    @Autowired
    private LoggingService log;

    @PostConstruct
    protected void init() {
        this.id = "scheduling.new-schedule-job";
        this.displayName = ts.getTranslatedString("module.scheduleJob.ui.label.new-job");
        this.description = ts.getTranslatedString("module.scheduleJob.ui.label.new-job.description");
        this.order = 1000;

        setCallback((parameters) -> {
            String parentPoolId = (String) parameters.get(Constants.PROPERTY_ID);
            String name = (String) parameters.get(Constants.PROPERTY_NAME);
            String descriptions = (String) parameters.get(Constants.PROPERTY_DESCRIPTION);
            String cronExpression = (String) parameters.get(Constants.PROPERTY_CRON);
            Long taskId = (Long) parameters.get(Constants.LABEL_TASKS);
            List<Long> usersId = parameters.get(Constants.LABEL_USER) != null
                    ? (List<Long>) parameters.get(Constants.LABEL_USER) : new ArrayList<>();
            Boolean enabled = (Boolean) parameters.get(Constants.PROPERTY_ENABLED);
            Boolean logResult = (Boolean) parameters.get(Constants.PROPERTY_LOG_RESULTS);
            ActionResponse actionResponse = new ActionResponse();
            try {
                String jobId = shs.createJob(name, descriptions, cronExpression,
                        parentPoolId, taskId, usersId, enabled, logResult);

                shs.scheduleJob(jobId);

                log.writeLogMessage(LoggerType.INFO, NewScheduleJobAction.class, 
                        String.format(ts.getTranslatedString("module.scheduleJob.ui.actions.new-job-log"), name, jobId));

                aem.createGeneralActivityLogEntry(UI.getCurrent().getSession().getAttribute(Session.class).getUser().getUserName(),
                        ActivityLogEntry.ACTIVITY_TYPE_CREATE_APPLICATION_OBJECT,
                        String.format(ts.getTranslatedString("module.scheduleJob.ui.actions.new-job-log"), name, jobId));
            } catch (InvalidArgumentException | ExecutionException | ApplicationObjectNotFoundException ex) {
                log.writeLogMessage(LoggerType.ERROR, NewScheduleJobAction.class, ex.getMessage());
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
