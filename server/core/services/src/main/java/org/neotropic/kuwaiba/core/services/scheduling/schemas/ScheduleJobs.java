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
package org.neotropic.kuwaiba.core.services.scheduling.schemas;

import lombok.Data;

/**
 * Class representing a scheduled job in Kuwaiba
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */
@Data
public abstract class ScheduleJobs implements Runnable {
    /**
     * The task instance exists, but hasn't been executed yet.
     */
    public static final int STATE_SCHEDULED = 0;
    /**
     * The task is running.
     */
    public static final int STATE_RUNNING = 1;
    /**
     * The task is done.
     */
    public static final int STATE_EXECUTED = 2;
    /**
     * The task has been killed, most likely because a critical error stopped its
     * execution.
     */
    public static final int STATE_KILLED = 3;
    /**
     * The task is no scheduled.
     */
    public static final int STATE_NOT_SCHEDULED = 6;
    /**
     * username to log actions
     */
    public static final String SYSTEM_LOG = "scheduler";
    /**
     * Unique identifier of the running task. Usually a uuid.
     */
    private String jobId;
    /**
     * Name of job
     */
    private String name;
    /**
     * Description of job
     */
    private String description;
    /**
     * The current status of the task. See STATE_XXX for possible values.
     */
    private int state;
    /**
     * If the task, although scheduled, should not be executed.
     */
    private boolean enabled;
    /**
     * If the task, print information in log.
     */
    private boolean logResults;
    /**
     * The expression that represents the time to execute.
     */
    private String cronExpression;

    public ScheduleJobs(String name, String description, String cronExpression, boolean enabled, boolean logResults) {
        this.name = name;
        this.description = description;
        this.cronExpression = cronExpression;
        this.logResults = logResults;
        this.enabled = enabled;
        this.state = STATE_NOT_SCHEDULED;
    }

    public ScheduleJobs() {}

}
