/*
 *  Copyright 2022 Neotropic SAS. <contact@neotropic.co>.
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.neotropic.synchronization.jobs;

import com.neotropic.synchronization.notification.Broadcaster;
import com.neotropic.synchronization.services.imp.PersonService;
import lombok.Getter;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 * created on 04/04/2022-09:46
 */
public class AsyncTaskJob implements DetailedJob{
    private final AtomicInteger progress;
    @Getter
    private EJobState state;
    private Random myRandom;
    @Getter
    private final String jobName;
    private PersonService personService;
    private Broadcaster broadcaster;


    public AsyncTaskJob(String jobName, PersonService personService, Broadcaster broadcaster) {
        this.jobName = jobName;
        this.progress = new AtomicInteger();
        this.myRandom = new Random();
        this.state = EJobState.NEW;
        this.personService = personService;
        this.broadcaster = broadcaster;
        sendProgress();
    }

    @Override
    public void run() {
        state = EJobState.IN_PROGRESS;
        int loops = 20;
        sendProgress();
        for (double i = 0.0; i <= loops; i++) {
            try {
                Thread.sleep(1000);
                Thread.sleep(myRandom.nextInt(5000));
                progress.set((int) ((i / loops) * 100));
                sendProgress();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        state = EJobState.FINISH;
        sendProgress();
    }

    public void sendProgress() {
        JobProgressMessage temp = new JobProgressMessage(jobName);
        temp.setProgress(progress.get());
        temp.setState(state);
        System.out.printf("progress: %s - State: %s%n", temp.getProgress(), temp.getState().getValue());
        broadcaster.broadcast(String.format("progress: %s - State: %s%n", temp.getProgress(), temp.getState().getValue()));
    }

    @Override
    public int getProgress() {
        return progress.get();
    }
}
