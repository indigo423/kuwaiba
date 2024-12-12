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
package org.neotropic.kuwaiba.core.services.scheduling;

import org.neotropic.kuwaiba.core.apis.persistence.ChangeDescriptor;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.application.Task;
import org.neotropic.kuwaiba.core.apis.persistence.application.UserProfile;
import org.neotropic.kuwaiba.core.apis.persistence.application.UserProfileLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ExecutionException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.OperationNotPermittedException;
import org.neotropic.kuwaiba.core.services.scheduling.schemas.ExecuteJob;
import org.neotropic.kuwaiba.core.services.scheduling.schemas.JobExecutionListener;
import org.neotropic.util.visual.properties.AbstractProperty;

import java.util.List;

/**
 * This is the entity in charge of manipulating jobs
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */
public interface SchedulingService {
    /**
     * Schedule all jobs stored in db
     * @throws ExecutionException if the scheduler is disabled
     * @throws ApplicationObjectNotFoundException if the job cannot be found
     */
    public void scheduleJobs() throws ExecutionException, ApplicationObjectNotFoundException;
    /**
     * Schedule a job to be executed based on cron Expression.
     * @param jobId job id.
     * @throws ApplicationObjectNotFoundException if the job cannot be found.
     * @throws ExecutionException if the job cannot be scheduled.
     */
    public void scheduleJob(String jobId) throws ApplicationObjectNotFoundException, ExecutionException;
    /**
     * removes a job from the scheduled jobs
     * @param jobId job id.
     */
    public void removeScheduledJob(String jobId);
    /**
     * Return the list of scheduled jobs.
     * @param skip page.
     * @param limit limit.
     * @return list of scheduled jobs
     */
    public List<ExecuteJob> getRunningJobs(int skip, int limit);
    /**
     * Changes the cron expression of given job
     * @param jobId job id
     * @param cronScheduleDefinition new cron expression
     * @throws ApplicationObjectNotFoundException if the job cannot be found
     * @throws OperationNotPermittedException If the job is running
     */
    public void changeCron(String jobId, String cronScheduleDefinition) throws ApplicationObjectNotFoundException, OperationNotPermittedException;
    /**
     * creates a scheduleJob.
     * @param name New job's name. Mandatory.
     * @param description New job's description.
     * @param cronScheduleDefinition New job's cronScheduleDefinition. represent the time to the task be executed.
     * @param parentPoolId New job's paren pool id.
     * @param taskId New job's taskId. the id of the task to be executed.
     * @param usersId New job's usersId. users id list.
     * @param enabled New job's state.
     * @param logResults New job's logResults. Enable the print info in log.
     * @return the jobId of the nwe scheduleJob.
     * @throws InvalidArgumentException if the name is invalid
     * @throws ExecutionException If an unexpected error occurred when creating the job
     */
    public String createJob(String name, String description, String cronScheduleDefinition, String parentPoolId,
                            long taskId, List<Long> usersId, boolean enabled, boolean logResults) throws InvalidArgumentException, ExecutionException;
    /**
     * get the list of jobs associated with a pool
     * @param poolId parent pool
     * @param skip page
     * @param limit limit
     * @return the list of jobs associated with a pool
     */
    public List<ExecuteJob> getScheduleJobsInPool(String poolId, int skip, int limit);
    /**
     * get a specific job
     * @param jobId job id
     * @return a specific job
     * @throws ApplicationObjectNotFoundException if the job cannot be found
     */
    public ExecuteJob getScheduleJob(String jobId) throws ApplicationObjectNotFoundException;
    /**
     * get the list of all job
     * @param skip page
     * @param limit limit
     * @return the list of all jobs in the application
     */
    public List<ExecuteJob> getScheduleJobs(int skip, int limit);
    /**
     * Update the property of a job
     *
     * @param selectedJobId    job to modified
     * @param propertyToUpdate property name to modified
     * @param value            new value
     * @return the changes perform in the job
     * @throws ApplicationObjectNotFoundException if the job cannot be found
     * @throws InvalidArgumentException if the name is invalid
     * @throws ExecutionException If an unexpected error occurred when updating the job
     */
    public ChangeDescriptor updateScheduleJob(String selectedJobId, String propertyToUpdate, String value) throws ApplicationObjectNotFoundException, InvalidArgumentException, ExecutionException;
    /**
     * Delete a given job
     * @param jobId job id
     * @throws ApplicationObjectNotFoundException if the job cannot be found
     */
    public void deleteScheduleJob(String jobId) throws ApplicationObjectNotFoundException;
    /**
     * Create a pool to manage jobs
     * @param name name
     * @param description description
     * @return id of new pool
     * @throws InvalidArgumentException if the name is invalid
     * @throws ExecutionException If an unexpected error occurred when creating the job pool
     */
    public String createScheduleJobsPools(String name, String description) throws InvalidArgumentException, ExecutionException;
    /**
     * Get a list of all schedule pools
     * @param skip page
     * @param limit limit
     * @return list of all schedule pools
     */
    public List<InventoryObjectPool> getScheduleJobsPools(int skip, int limit);
    /**
     * Update the property of a schedule pool
     * @param poolId pool id
     * @param propertyToUpdate property name to modified
     * @param value new value
     * @param userName user logged
     * @return the changes perform in the schedule pool
     * @throws InvalidArgumentException if the name is invalid
     * @throws ApplicationObjectNotFoundException if the job pool cannot be found
     */
    public ChangeDescriptor updateScheduleJobsPools(String poolId, String propertyToUpdate, String value, String userName)
            throws InvalidArgumentException, ApplicationObjectNotFoundException;
    /**
     * Delete a give schedule pool
     * @param poolId pool id
     * @throws ApplicationObjectNotFoundException if the job cannot be found
     */
    public void deleteScheduleJobsPools (String poolId) throws ApplicationObjectNotFoundException;
    /**
     * Assigns the given user to job
     * @param userId user id
     * @param jobId job id
     * @return object that represents the changes
     * @throws InvalidArgumentException if the user is already subscribed to the job
     * @throws ExecutionException If the job is running
     * @throws ApplicationObjectNotFoundException if the job cannot be found
     */
    public ChangeDescriptor assignUserToJob(long userId, String jobId) throws InvalidArgumentException,
            ExecutionException, ApplicationObjectNotFoundException;
    /**
     * removes the given user from a job
     * @param userId userId
     * @param jobId jobId
     * @return object that represents the changes
     * @throws ApplicationObjectNotFoundException if the job cannot be found
     * @throws ExecutionException If the job is running
     */
    public ChangeDescriptor deleteUserFromJob(long userId, String jobId) throws ApplicationObjectNotFoundException, ExecutionException;
    /**
     * return the list of users assigned to the job
     * @param jobId job id
     * @param skip number of element to skip
     * @param limit paged
     * @return list of users assigned to the job
     */
    public List<UserProfileLight> getAssignUsersToJob(String jobId, int skip, int limit);
    /**
     * updates the users assigned to the job
     *
     * @param selectedJob job
     * @param users       user's ids
     * @return object that represents the changes
     * @throws ExecutionException If the job is running
     */
    public ChangeDescriptor updateUsersForJob(ExecuteJob selectedJob, List<UserProfile> users) throws ExecutionException;
    /**
     * updates the task assigned to the job
     *
     * @param selectedJob job
     * @param task        task
     * @return object that represents the changes
     * @throws InvalidArgumentException if the task is already assigned to the job
     * @throws ApplicationObjectNotFoundException if the task cannot be found
     * @throws ExecutionException If the job is running
     */
    public ChangeDescriptor updateTaskForJob(ExecuteJob selectedJob, Task task) throws InvalidArgumentException,
            ApplicationObjectNotFoundException, ExecutionException;
    /**
     * build the list of properties of job
     * @param executeJob job
     * @return list of properties
     * @throws ApplicationObjectNotFoundException If the task could not be found
     */
    public List<AbstractProperty> getAbstractPropertiesFromJob(ExecuteJob executeJob) throws ApplicationObjectNotFoundException;
    /**
     * Add observer to notify job's changes
     * @param listener listener
     */
    public void addJobUpdateListener(JobExecutionListener listener);
    /**
     * Remove observer to notify job's changes
     * @param listener listener
     */
    public void removeJobUpdateListener(JobExecutionListener listener);
}