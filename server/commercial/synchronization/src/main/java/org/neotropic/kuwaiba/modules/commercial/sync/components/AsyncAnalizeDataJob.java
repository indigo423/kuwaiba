/*
 * Copyright 2010-2024. Neotropic SAS <contact@neotropic.co>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neotropic.kuwaiba.modules.commercial.sync.components;

import lombok.Getter;
import lombok.Setter;
import org.neotropic.kuwaiba.modules.commercial.sync.model.AbstractSyncProvider;
import org.neotropic.kuwaiba.modules.commercial.sync.model.PollResult;
import org.neotropic.kuwaiba.modules.commercial.sync.model.SyncResult;
import org.neotropic.kuwaiba.modules.commercial.sync.notification.ProgressBroadcaster;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.neotropic.kuwaiba.modules.core.logging.LoggerType;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

/**
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@neotropic.co>}
 */
public class AsyncAnalizeDataJob implements DetailedJob {

    @Getter
    private final String jobName;
    private final AbstractSyncProvider syncProvider;
    private final PollResult pollResult;
    private float progress;
    @Setter
    private int totalJobs;
    @Setter
    private int jobNumber;
    @Setter
    private ProgressBroadcaster progressBroadcaster;
    @Getter
    private volatile List<SyncResult> value;
    @Getter
    private EJobState state;
    /**
     * Reference to the Logging service
     */
    private LoggingService log;

    public AsyncAnalizeDataJob(PollResult pollResult
            , AbstractSyncProvider syncProvider, LoggingService log) {

        UUID uuid = UUID.randomUUID();
        this.jobName = "job-" + uuid;
        this.progress = 0;
        this.state = EJobState.NEW;
        this.syncProvider = syncProvider;
        this.pollResult = pollResult;
        this.log = log;
        updateProgress(progressBroadcaster);
    }

    @Override
    public void run() {
        state = EJobState.IN_PROGRESS;
        updateProgress(progressBroadcaster);
        value = syncProvider.automatedSync(pollResult);
        state = EJobState.FINISH;
        progress = (float) jobNumber / totalJobs;
        updateProgress(progressBroadcaster);
    }

    /**
     * Update progress dialog with values
     *
     * @param progressBroadcaster vaadin broadcaster
     */
    public void updateProgress(ProgressBroadcaster progressBroadcaster) {
        JobProgressMessage temp = new JobProgressMessage(jobName);
        temp.setProgress(progress);
        temp.setState(state);
        temp.setStep(EAsyncStep.ANALYZE);
        temp.setTotalElements(totalJobs);
        temp.setElement(jobNumber);
        log.writeLogMessage(LoggerType.INFO, AsyncAnalizeDataJob.class, 
                String.format("%s => job (%s / %s) progress: %s - %s - State: %s\n", jobName, jobNumber, totalJobs
                , progress, temp.getStep(), state));
        if (progressBroadcaster != null && value != null && !value.isEmpty()) {
            progressBroadcaster.broadcast(temp, value);
        } else if (progressBroadcaster != null) {
            progressBroadcaster.broadcast(temp, new ArrayList<>());
        }
    }

    @Override
    public float getProgress() {
        return progress;
    }
}
