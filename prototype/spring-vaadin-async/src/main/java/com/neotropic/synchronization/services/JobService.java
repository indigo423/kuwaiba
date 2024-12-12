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

package com.neotropic.synchronization.services;

import com.neotropic.synchronization.jobs.AsyncTaskJob;
import com.neotropic.synchronization.notification.Broadcaster;
import com.neotropic.synchronization.services.imp.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 * created on 04/04/2022-14:34
 */
@Service
public class JobService {

    @Autowired
    private Broadcaster broadcaster;

    @Async
    public void createJob(String jobName, PersonService personService, Broadcaster broadcaster){
        AsyncTaskJob newJob = new AsyncTaskJob(jobName, personService, broadcaster);
        newJob.run();
    }
}
