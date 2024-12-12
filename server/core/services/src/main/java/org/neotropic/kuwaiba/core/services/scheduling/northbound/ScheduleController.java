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
package org.neotropic.kuwaiba.core.services.scheduling.northbound;

import org.neotropic.kuwaiba.core.apis.persistence.ChangeDescriptor;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.application.UserProfileLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ExecutionException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.OperationNotPermittedException;
import org.neotropic.kuwaiba.core.services.scheduling.neo4j.SchedulingServiceImpl;
import org.neotropic.kuwaiba.core.services.scheduling.schemas.ExecuteJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */
@RestController
@RequestMapping(ScheduleController.PATH)
public class ScheduleController {
    /**
     * Path that includes the Kuwaiba version and the module id
     */
    public static final String PATH = "/v2.1/schedule-jobs/"; //NOI18N
    /**
     * Reference to the default logger.
     */
    @Autowired
    private SchedulingServiceImpl schedulingService;

    @PostMapping("/scheduling")
    public void scheduleJob(@RequestParam("jobId") String jobId) throws ApplicationObjectNotFoundException, ExecutionException {
         schedulingService.scheduleJob(jobId);
    }

    @GetMapping("/running")
    public List<ExecuteJob> getRunningJobs() {
        return schedulingService.getRunningJobs();
    }

    @PutMapping("/remove/{job-id}")
    public void removeScheduledJob(
            @PathVariable("job-id") String jobId) {
        schedulingService.removeScheduledJob(jobId);
    }

    @GetMapping("cron/{job-id}")
    public void changeCron(
            @PathVariable("job-id") String jobId,
            @RequestParam("cron") String cron) throws ApplicationObjectNotFoundException, OperationNotPermittedException {
        schedulingService.changeCron(jobId, cron);
    }

    // <editor-fold desc="ScheduleJobs" defaultstate="collapsed">

    @PostMapping
    public String createScheduleJob(
            @RequestBody ScheduleJobsPostRequestBody body) throws InvalidArgumentException, ExecutionException {
        return schedulingService.createJob(body.getName(),
                body.getDescription(),
                body.getCronScheduleDefinition(),
                body.getParentPoolId(),
                body.getTaskId(),
                body.getUsersId(),
                body.isEnabled(),
                body.isLogResult());
    }

    @GetMapping("/in-pool/{pool-id}")
    public List<ExecuteJob> getScheduleJobsInPool(
            @PathVariable("pool-id") String poolId,
            @RequestParam("skip") int skip,
            @RequestParam("limit") int limit)
    {
        return schedulingService.getScheduleJobsInPool(poolId, skip, limit);
    }

    @GetMapping
    public List<ExecuteJob> getScheduleJobs(
            @RequestParam("skip") int skip,
            @RequestParam("limit") int limit)
    {
        return schedulingService.getScheduleJobs(skip, limit);
    }

    @GetMapping("/{job-id}")
    public ExecuteJob getScheduleJob(
            @PathVariable("job-id") String jobId) throws ApplicationObjectNotFoundException
    {
        return schedulingService.getScheduleJob(jobId);
    }

    @PutMapping("/{job-id}")
    public ChangeDescriptor updateScheduleJob(
            @PathVariable("job") String jobId,
            @RequestBody ScheduleJobsPutRequestBody body) throws ApplicationObjectNotFoundException, InvalidArgumentException, ExecutionException {
        return schedulingService.updateScheduleJob(jobId, body.getPropertyToUpdate(), body.getValue());
    }

    @DeleteMapping("/{job-id}")
    public void deleteScheduleJob(
            @PathVariable("job-id") String jobId) throws ApplicationObjectNotFoundException {
        schedulingService.deleteScheduleJob(jobId);
    }

    // </editor-fold>

    // <editor-fold desc="ScheduleJobs Pools" defaultstate="collapsed">

    @PostMapping("/pools")
    public String createScheduleJobPool(
            @RequestBody ScheduleJobsPoolsPostRequestBody body) throws InvalidArgumentException, ExecutionException {
        return schedulingService.createScheduleJobsPools(body.getName(), body.getDescription());
    }

    @GetMapping("/pools")
    public List<InventoryObjectPool> getScheduleJobsPools(
            @RequestParam("skip") int skip,
            @RequestParam("limit") int limit) {
        return schedulingService.getScheduleJobsPools(skip, limit);
    }

    @PutMapping("/pools/{pool-id}")
    public ChangeDescriptor updateScheduleJobPool(
            @PathVariable("pool-id") String poolId,
            @RequestBody ScheduleJobsPutRequestBody body) throws ApplicationObjectNotFoundException, InvalidArgumentException {
        return schedulingService.updateScheduleJobsPools(poolId, body.getPropertyToUpdate(), body.getValue(), body.getUserName());
    }

    @DeleteMapping("/pools/{pool-id}")
    public void deleteScheduleJobPool(
            @PathVariable("pool-id") String poolId) throws ApplicationObjectNotFoundException {
        schedulingService.deleteScheduleJobsPools(poolId);
    }

    // </editor-fold>

    // <editor-fold desc="Users" defaultstate="collapsed">

//    @PostMapping("/assign/user")
//    public ChangeDescriptor assignUserToJob(
//            @RequestBody ScheduleJobsSubscribeUserPostRequestBody body) throws InvalidArgumentException {
//        return schedulingService.assignUserToJob(body.getUserId(), body.getJobId());
//    }

    @GetMapping("/{job-id}/users")
    public List<UserProfileLight> getAssignUserToJob(
            @PathVariable("job-id") String jobId,
            @RequestParam("skip") int skip,
            @RequestParam("limit") int limit)
    {
        return schedulingService.getAssignUsersToJob(jobId, skip, limit);
    }

    @DeleteMapping("/{job-id}/delete/user/{user-id}")
    public ChangeDescriptor deleteUserToJob(
            @PathVariable("job-id") String jobId,
            @PathVariable("user-id") long userId) throws ApplicationObjectNotFoundException, ExecutionException {
        return schedulingService.deleteUserFromJob(userId, jobId);
    }

    // </editor-fold>

}
