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
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.application.ActivityLogEntry;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.Privilege;
import org.neotropic.kuwaiba.core.apis.persistence.application.Session;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ExecutionException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.core.services.scheduling.SchedulingService;
import org.neotropic.kuwaiba.core.services.scheduling.schemas.CronDefinition;
import org.neotropic.kuwaiba.core.services.scheduling.schemas.ExecuteJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import org.neotropic.kuwaiba.modules.core.logging.LoggerType;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

/**
 * Schedule a job
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */
@Component
public class ScheduleJobAction extends AbstractAction {
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
        this.id = "scheduling.schedule-job";
        this.displayName = ts.getTranslatedString("module.scheduleJob.ui.label.schedule-jobs");
        this.description = ts.getTranslatedString("module.scheduleJob.ui.actions.schedule-job.description");
        this.order = 1000;

        setCallback(parameters -> {
            ExecuteJob job = (ExecuteJob) parameters.get("scheduleJob");

            try {
                shs.scheduleJob(job.getJobId());

                log.writeLogMessage(LoggerType.INFO, ScheduleJobAction.class, 
                        String.format(ts.getTranslatedString(
                        "module.scheduleJob.ui.actions.scheduled-job-log"), job.getName(), CronDefinition.getCronSummary(job.getCronExpression(), ts)));

                aem.createGeneralActivityLogEntry(UI.getCurrent().getSession().getAttribute(Session.class).getUser().getUserName(),
                        ActivityLogEntry.ACTIVITY_TYPE_UPDATE_APPLICATION_OBJECT, String.format(ts.getTranslatedString(
                                "module.scheduleJob.ui.actions.scheduled-job-log"), job.getName(), CronDefinition.getCronSummary(job.getCronExpression(), ts)));

            } catch (ExecutionException | ApplicationObjectNotFoundException ex) {
                log.writeLogMessage(LoggerType.ERROR, ScheduleJobAction.class, ex.getMessage());
                throw new ModuleActionException(ex.getMessage());
            }
            return new ActionResponse();
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
