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
package org.neotropic.kuwaiba.core.services.scheduling.neo4j;

import lombok.Getter;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neotropic.kuwaiba.core.apis.persistence.ChangeDescriptor;
import org.neotropic.kuwaiba.core.apis.persistence.ConnectionManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.ActivityLogEntry;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.application.Task;
import org.neotropic.kuwaiba.core.apis.persistence.application.UserProfile;
import org.neotropic.kuwaiba.core.apis.persistence.application.UserProfileLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ExecutionException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.OperationNotPermittedException;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.core.services.scheduling.NoOpTaskScheduler;
import org.neotropic.kuwaiba.core.services.scheduling.SchedulingService;
import org.neotropic.kuwaiba.core.services.scheduling.properties.CronProperty;
import org.neotropic.kuwaiba.core.services.scheduling.properties.TaskProperty;
import org.neotropic.kuwaiba.core.services.scheduling.properties.UserProperty;
import org.neotropic.kuwaiba.core.services.scheduling.schemas.CronDefinition;
import org.neotropic.kuwaiba.core.services.scheduling.schemas.ExecuteJob;
import org.neotropic.kuwaiba.core.services.scheduling.schemas.JobExecutionListener;
import org.neotropic.kuwaiba.core.services.scheduling.schemas.ScheduleJobs;
import org.neotropic.kuwaiba.modules.core.logging.LoggerType;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;
import org.neotropic.util.visual.properties.AbstractProperty;
import org.neotropic.util.visual.properties.BooleanProperty;
import org.neotropic.util.visual.properties.StringProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

/**
 * A service that manages the execution of schedule tasks, most likely related to 
 * maintenance, report generation or inventory synchronization.
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@neotropic.org>}
 */
@Service
public class SchedulingServiceImpl implements SchedulingService {
    /**
     * Database connection manager instance.
     */
    private final ConnectionManager<GraphDatabaseService> connectionManager;
    /**
     * Reference to the Application Entity Manager.
     */
    private final ApplicationEntityManager aem;
    /**
     * Reference to the translation service.
     */
    private final TranslationService ts;
    /**
     * Reference to spring's taskscheduler.
     */
    private final TaskScheduler taskScheduler;
    /**
     * A list of all running jobs.
     */
    @Getter
    private List<ExecuteJob> runningJobs;
    /**
     * A map for keeping scheduled system tasks.
     */
    Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();

    private final List<JobExecutionListener> jobUpdateListeners = new ArrayList<>();
    /**
     * Reference to the Logging Service.
     */
    private final LoggingService log;
    
    @Value("${schedule.logging.path}")
    private String logPath;
    
    @Value("${schedule.logging.history}")
    private String logMaxFiles;
    
    @Value("${schedule.logging.policy.max-file-size}")
    private String logFileSize;

    public SchedulingServiceImpl(TaskScheduler taskScheduler,
                                 ConnectionManager<GraphDatabaseService> connectionManager,
                                 ApplicationEntityManager aem,
                                 TranslationService ts, 
                                 LoggingService log)
    {
        this.taskScheduler = taskScheduler;
        this.connectionManager = connectionManager;
        this.aem = aem;
        this.ts = ts;
        this.log = log;
        this.runningJobs = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
        log.registerLog(SchedulingService.class.getPackageName(), 
                logPath, "scheduling.log", logFileSize, Integer.parseInt(logMaxFiles));
    }

    @Override
    public void scheduleJobs() throws ExecutionException, ApplicationObjectNotFoundException {
        try(Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            if (taskScheduler instanceof NoOpTaskScheduler)
                throw new ExecutionException(ts.getTranslatedString("module.scheduleJob.ui.actions.disabled-scheduling-module"));

            Result result = connectionManager.getConnectionHandler().execute("MATCH (scheduleJob:scheduledJobs) " +
                    "MATCH (scheduleJob)-[:HAS_TASK]->(task) " +
                    "OPTIONAL MATCH (scheduleJob)-[:HAS_USER]->(user) " +
                    "RETURN scheduleJob, COLLECT(user) AS users, task");

            if (!result.hasNext())
                return;

            while (result.hasNext()) {
                Map<String, Object> row = result.next();
                Node jobNode = (Node) row.get("scheduleJob");
                Node taskNode = (Node) row.get("task");
                List<Node> usersNodes = (List<Node>) row.get("users");

                List<UserProfileLight> users = new ArrayList<>();
                if (usersNodes != null) {
                    usersNodes.forEach(node -> {
                        users.add(new UserProfileLight(
                                node.getId(),
                                (String) node.getProperty(UserProfile.PROPERTY_NAME),
                                (String) node.getProperty(UserProfile.PROPERTY_FIRST_NAME),
                                (String) node.getProperty(UserProfile.PROPERTY_LAST_NAME),
                                (boolean) node.getProperty(UserProfile.PROPERTY_ENABLED),
                                (long) node.getProperty(UserProfile.PROPERTY_CREATION_DATE),
                                node.hasProperty(UserProfile.PROPERTY_TYPE) ?
                                        (int) node.getProperty(UserProfile.PROPERTY_TYPE) : UserProfile.USER_TYPE_GUI,
                                node.hasProperty(UserProfile.PROPERTY_EMAIL) ?
                                        (String) node.getProperty(UserProfile.PROPERTY_EMAIL) : null
                        ));
                    });
                }

                ExecuteJob job = new ExecuteJob(
                        (String) jobNode.getProperty("name", ""),
                        (String) jobNode.getProperty("description", ""),
                        (String) jobNode.getProperty("cronExpression", null),
                        (boolean) jobNode.getProperty("enabled", false),
                        (boolean) jobNode.getProperty("logResults", false),
                        this.aem, users, log
                );

                job.setJobId((String) jobNode.getProperty("jobId"));
                job.setTaskId(taskNode.getId());
                job.setState(0);
                job.setListeners(jobUpdateListeners);

                try {
                    ScheduledFuture<?> jobSchedule = taskScheduler.schedule(job,
                            new CronTrigger(job.getCronExpression(), TimeZone
                                    .getTimeZone(TimeZone.getDefault().getID())));
                    jobsMap.put(job.getJobId(), jobSchedule);
                    runningJobs.add(job);
                    log.writeLogMessage(LoggerType.INFO, SchedulingServiceImpl.class, 
                            String.format("Job %s - %s scheduled", job.getName(), job.getJobId()));

                    aem.createGeneralActivityLogEntry("scheduler", ActivityLogEntry.ACTIVITY_TYPE_CREATE_APPLICATION_OBJECT,
                            String.format(ts.getTranslatedString("module.scheduleJob.ui.actions.scheduled-job-log"), job.getName(), job.getJobId()));

                } catch (Exception ex) {
                    ScheduledFuture<?> scheduledTask = jobsMap.get(job.getJobId());
                    scheduledTask.cancel(true);
                    runningJobs.removeIf(scheduleJob -> scheduleJob.getJobId().equals(job.getJobId()));

                    throw new ExecutionException(String.format(
                            ts.getTranslatedString("module.scheduleJob.ui.actions.schedule-job-fail"), job.getName(), ex.getMessage()));
                }
            }

            tx.success();
        }
    }

    @Override
    public void scheduleJob(String jobId) throws ApplicationObjectNotFoundException, ExecutionException {
        try(Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            if (taskScheduler instanceof NoOpTaskScheduler)
                throw new ExecutionException(ts.getTranslatedString("module.scheduleJob.ui.actions.disabled-scheduling-module"));

            if (jobsMap.containsKey(jobId))
                return;

            Map<String, Object> params = new HashMap<>();
            params.put("jobId", jobId);

            String query = "MATCH (scheduleJob:scheduledJobs {jobId: $jobId}) " +
                    "MATCH (scheduleJob)-[:HAS_TASK]->(task) " +
                    "OPTIONAL MATCH (scheduleJob)-[:HAS_USER]->(user) " +
                    "RETURN scheduleJob, task, user ";

            Node jobNode = null;
            Node taskNode = null;
            Node userNode;
            List<Node> usersNodes = new ArrayList<>();
            List<UserProfileLight> users = new ArrayList<>();

            Result result = connectionManager.getConnectionHandler().execute(query, params);

            if (!result.hasNext())
                throw new ApplicationObjectNotFoundException(String.format(
                        ts.getTranslatedString("module.scheduleJob.actions.job-not-found"), jobId));

            while (result.hasNext()) {
                Map<String, Object> row = result.next();
                jobNode = (Node) row.get("scheduleJob");
                taskNode = (Node) row.get("task");
                userNode = row.containsKey("user") ? (Node) row.get("user") : null;
                if (userNode != null)
                    usersNodes.add(userNode);
            }

            tx.success();

            usersNodes.forEach(node -> {
                users.add(new UserProfileLight(node.getId(),
                        (String)node.getProperty(UserProfile.PROPERTY_NAME),
                        (String)node.getProperty(UserProfile.PROPERTY_FIRST_NAME),
                        (String)node.getProperty(UserProfile.PROPERTY_LAST_NAME),
                        (boolean)node.getProperty(UserProfile.PROPERTY_ENABLED),
                        (long)node.getProperty(UserProfile.PROPERTY_CREATION_DATE),
                        node.hasProperty(UserProfile.PROPERTY_TYPE) ?  //To keep backward compatibility
                                (int)node.getProperty(UserProfile.PROPERTY_TYPE) : UserProfile.USER_TYPE_GUI,
                        node.hasProperty(UserProfile.PROPERTY_EMAIL) ? //To keep backward compatibility
                                (String) node.getProperty(UserProfile.PROPERTY_EMAIL) : null));
            });

            ExecuteJob job = new ExecuteJob(
                    (String) jobNode.getProperty("name", ""),
                    (String) jobNode.getProperty("description", ""),
                    (String) jobNode.getProperty("cronExpression", null),
                    (boolean) jobNode.getProperty("enabled", false),
                    (boolean) jobNode.getProperty("logResults", false),
                    this.aem, users, log
            );
            job.setJobId((String) jobNode.getProperty("jobId"));
            job.setTaskId(taskNode.getId());
            job.setState(0);
            job.setListeners(jobUpdateListeners);

            try {
                ScheduledFuture<?> jobSchedule = taskScheduler.schedule(job,
                        new CronTrigger(job.getCronExpression(), TimeZone
                                .getTimeZone(TimeZone.getDefault().getID())));
                jobsMap.put(job.getJobId(), jobSchedule);
                runningJobs.add(job);
                log.writeLogMessage(LoggerType.INFO, SchedulingServiceImpl.class, 
                        String.format("Job %s - %s scheduled", job.getName(), job.getJobId()));
            } catch (Exception ex) {
                throw new ExecutionException(String.format(
                        ts.getTranslatedString("module.scheduleJob.ui.actions.schedule-job-fail"), job.getName(), ex.getMessage()));
            }
        }
    }

    @Override
    public void removeScheduledJob(String jobId) {
        if (!jobsMap.containsKey(jobId))
            return;

        ScheduledFuture<?> scheduledTask = jobsMap.get(jobId);
        scheduledTask.cancel(true);

        jobsMap.remove(jobId);

        runningJobs.removeIf(job -> job.getJobId().equals(jobId));

//        for (int i = 0; i < runningJobs.size(); i++) {
//            if (runningJobs.get(i).getJobId().equals(jobId)) {
//                runningJobs.remove(i);
//                break;
//            }
//        }
        log.writeLogMessage(LoggerType.INFO, SchedulingServiceImpl.class, 
                        String.format("Job %s unscheduled", jobId));
    }

    @Override
    public List<ExecuteJob> getRunningJobs(int skip, int limit) {
        int endIndex = Math.min(limit, runningJobs.size());

        if (skip < 0 || skip >= runningJobs.size())
            return Collections.emptyList();

        return runningJobs.subList(skip, endIndex);
    }

    // <editor-fold desc="Jobs" defaultstate="collapsed">

    @Override
    public String createJob(String name, String description, String cronScheduleDefinition, String parentPoolId,
                            long taskId, List<Long> usersId, boolean enabled, boolean logResults) throws InvalidArgumentException, ExecutionException {
        try(Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            if (name == null || name.trim().isEmpty())
                throw  new InvalidArgumentException(ts.getTranslatedString("module.scheduleJob.messages.job-name-not-empty"));

            Map<String, Object> params = new HashMap<>();
            params.put("name", name);
            params.put("description", description);
            params.put("cronExpression", cronScheduleDefinition);
            params.put("jobId", UUID.randomUUID().toString());
            params.put("enabled", enabled);
            params.put("logResults", logResults);
            Map<String, Object> properties = new HashMap<>();
            properties.put("properties", params);
            properties.put("taskId", taskId);
            properties.put("usersId", usersId.isEmpty() ? "" : usersId);
            properties.put("parentPoolId", parentPoolId);

            String createQuery = "MATCH (parentPool:scheduledJobsPools {_uuid: $parentPoolId}) " +
                    "MATCH (task:tasks) WHERE ID(task) = $taskId " +
                    "CREATE (scheduleJob:scheduledJobs $properties) " +
                    "CREATE (parentPool)<-[:CHILD_OF_SPECIAL]-(scheduleJob)-[:HAS_TASK]->(task) ";

            if (!usersId.isEmpty()) {
                createQuery += "WITH scheduleJob " +
                        "MATCH (user:users) WHERE ID(user) IN $usersId " +
                        "CREATE (scheduleJob)-[:HAS_USER]->(user) ";
            }

            createQuery += "RETURN scheduleJob.jobId AS id";

            Result resultNode = connectionManager.getConnectionHandler().execute(createQuery, properties);

            if (resultNode.hasNext()) {
                tx.success();
                return (String) resultNode.next().get("id");
            }

            throw new ExecutionException(
                    ts.getTranslatedString("module.general.messages.unexpected-error"));
        }
    }

    @Override
    public void changeCron(String jobId, String cronScheduleDefinition) throws ApplicationObjectNotFoundException, OperationNotPermittedException {
        try(Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            Map<String, Object> params = new HashMap<>();
            params.put("jobId", jobId);
            params.put("cron", cronScheduleDefinition);

            Result resultNode = connectionManager.getConnectionHandler().execute(
                    "MATCH (scheduleJob:scheduledJobs {jobId: $jobId}) RETURN scheduleJob", params);

            if (!resultNode.hasNext())
                throw new ApplicationObjectNotFoundException(
                        String.format(ts.getTranslatedString("module.scheduleJob.actions.job-not-found"), jobId));

            String query = "MATCH (scheduleJob:scheduledJobs {jobId: $jobId}) " +
                    "SET scheduleJob.cronExpression = $cron " +
                    "RETURN scheduleJob ";

            connectionManager.getConnectionHandler().execute(query, params);
            tx.success();

            if (jobsMap.containsKey(jobId)) {

                for (ExecuteJob runningJob : runningJobs) {
                    if (runningJob.getJobId().equals(jobId)) {
                        if (runningJob.getState() == ScheduleJobs.STATE_RUNNING)
                            throw new OperationNotPermittedException(ts.getTranslatedString("module.scheduleJob.error.actions.update-running-job"));

                        ScheduledFuture<?> scheduledTask = jobsMap.get(jobId);
                        scheduledTask.cancel(true);
                        jobsMap.remove(jobId);

                        runningJob.setCronExpression(cronScheduleDefinition);
                        ScheduledFuture<?> jobSchedule = taskScheduler.schedule(runningJob,
                                new CronTrigger(runningJob.getCronExpression(), TimeZone
                                        .getTimeZone(TimeZone.getDefault().getID())));
                        jobsMap.put(runningJob.getJobId(), jobSchedule);
                        log.writeLogMessage(LoggerType.INFO, SchedulingServiceImpl.class, 
                        String.format("Job %s - %s scheduled", runningJob.getName(), runningJob.getJobId()));
                    }
                }
            }
        }
    }

    @Override
    public List<ExecuteJob> getScheduleJobsInPool(String poolId, int skip, int limit) {
        try (Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            List<ExecuteJob> jobs = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();
            params.put("poolId", poolId);
            params.put("skip", skip);
            params.put("limit", limit);
            Result poolResult = connectionManager.getConnectionHandler().execute(
                    "MATCH (poolNode:scheduledJobsPools {_uuid: $poolId}) " +
                    "MATCH (poolNode)<-[:CHILD_OF_SPECIAL]-(scheduleJob:scheduledJobs) " +
                    "MATCH (scheduleJob)-[:HAS_TASK]->(task) " +
                    "OPTIONAL MATCH (scheduleJob)-[:HAS_USER]->(user) " +
                    "RETURN scheduleJob AS jobs, task, COLLECT(user) AS users SKIP $skip LIMIT $limit", params);

            while (poolResult.hasNext()) {
                Map<String, Object> row = poolResult.next();
                Node jobNode = (Node) row.get("jobs");
                Node taskNode = (Node) row.get("task");
                List<Node> usersNode = new ArrayList<>();
                List<UserProfileLight> users = new ArrayList<>();
                if (row.containsKey("users"))
                    if (row.get("users") instanceof List)
                        ((List<?>) row.get("users")).forEach(user -> usersNode.add((Node) user));

                usersNode.forEach(node -> {
                users.add(new UserProfileLight(node.getId(),
                        (String)node.getProperty(UserProfile.PROPERTY_NAME),
                        (String)node.getProperty(UserProfile.PROPERTY_FIRST_NAME),
                        (String)node.getProperty(UserProfile.PROPERTY_LAST_NAME),
                        (boolean)node.getProperty(UserProfile.PROPERTY_ENABLED),
                        (long)node.getProperty(UserProfile.PROPERTY_CREATION_DATE),
                        node.hasProperty(UserProfile.PROPERTY_TYPE) ?  //To keep backward compatibility
                                (int)node.getProperty(UserProfile.PROPERTY_TYPE) : UserProfile.USER_TYPE_GUI,
                        node.hasProperty(UserProfile.PROPERTY_EMAIL) ? //To keep backward compatibility
                                (String) node.getProperty(UserProfile.PROPERTY_EMAIL) : null));
                });

                ExecuteJob job = new ExecuteJob(
                        (String) jobNode.getProperty("name"),
                        (String) jobNode.getProperty("description"),
                        (String) jobNode.getProperty("cronExpression"),
                        (boolean) jobNode.getProperty("enabled"),
                        (boolean) jobNode.getProperty("logResults")
                );
                job.setJobId((String) jobNode.getProperty("jobId"));
                job.setUsers(users);
                job.setTaskId(taskNode.getId());
                jobs.add(job);

            }
            tx.success();
            return jobs;
        }
    }

    @Override
    public ExecuteJob getScheduleJob(String jobId) throws ApplicationObjectNotFoundException {
        try(Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            Map<String, Object> params = new HashMap<>();
            params.put("jobId", jobId);
            Result result = connectionManager.getConnectionHandler().execute(
                    "MATCH (scheduleJob:scheduledJobs {jobId: $jobId}) " +
                    "RETURN scheduleJob AS job", params);

            if (!result.hasNext())
                throw new ApplicationObjectNotFoundException(String.format(
                        ts.getTranslatedString("module.scheduleJob.actions.job-not-found"), jobId));

            ResourceIterator<Node> nodes = result.columnAs( "job" );
            Node node = nodes.next();
            ExecuteJob job = new ExecuteJob(
                    (String) node.getProperty("name"),
                    (String) node.getProperty("description"),
                    (String) node.getProperty("cronExpression"),
                    (boolean) node.getProperty("enabled"),
                    (boolean) node.getProperty("logResults")
            );
            job.setJobId((String) node.getProperty("jobId"));
            return job;
        }
    }

    @Override
    public List<ExecuteJob> getScheduleJobs(int skip, int limit) {
        try (Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            List<ExecuteJob> jobs = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();
            params.put("skip", skip);
            params.put("limit", limit);
            Result result = connectionManager.getConnectionHandler().execute(
                    "MATCH (scheduleJob:scheduledJobs) " +
                    "MATCH (scheduleJob)-[:HAS_TASK]->(task) " +
                    "OPTIONAL MATCH (scheduleJob)-[:HAS_USER]->(user) " +
                    "RETURN scheduleJob AS jobs, task, COLLECT(user) AS users SKIP $skip LIMIT $limit", params);

            while (result.hasNext()) {
                Map<String, Object> row = result.next();
                Node jobNode = (Node) row.get("jobs");
                Node taskNode = (Node) row.get("task");
                List<Node> usersNode = new ArrayList<>();
                List<UserProfileLight> users = new ArrayList<>();
                if (row.containsKey("users"))
                    if (row.get("users") instanceof List)
                        ((List<?>) row.get("users")).forEach(user -> usersNode.add((Node) user));

                usersNode.forEach(node -> {
                    users.add(new UserProfileLight(node.getId(),
                            (String)node.getProperty(UserProfile.PROPERTY_NAME),
                            (String)node.getProperty(UserProfile.PROPERTY_FIRST_NAME),
                            (String)node.getProperty(UserProfile.PROPERTY_LAST_NAME),
                            (boolean)node.getProperty(UserProfile.PROPERTY_ENABLED),
                            (long)node.getProperty(UserProfile.PROPERTY_CREATION_DATE),
                            node.hasProperty(UserProfile.PROPERTY_TYPE) ?  //To keep backward compatibility
                                    (int)node.getProperty(UserProfile.PROPERTY_TYPE) : UserProfile.USER_TYPE_GUI,
                            node.hasProperty(UserProfile.PROPERTY_EMAIL) ? //To keep backward compatibility
                                    (String) node.getProperty(UserProfile.PROPERTY_EMAIL) : null));
                });

                ExecuteJob job = new ExecuteJob(
                        (String) jobNode.getProperty("name"),
                        (String) jobNode.getProperty("description"),
                        (String) jobNode.getProperty("cronExpression"),
                        (boolean) jobNode.getProperty("enabled"),
                        (boolean) jobNode.getProperty("logResults")
                );
                job.setJobId((String) jobNode.getProperty("jobId"));
                job.setUsers(users);
                job.setTaskId(taskNode.getId());
                jobs.add(job);

            }
            tx.success();
            return jobs;
        }
    }

    @Override
    public ChangeDescriptor updateScheduleJob(String selectedJobId, String propertyToUpdate, String value) throws ApplicationObjectNotFoundException, InvalidArgumentException, ExecutionException {
        try(Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", selectedJobId);
            params.put("value", value);
            String query = "MATCH (scheduleNode:scheduledJobs {jobId: $id}) RETURN scheduleNode ";
            Result resultNode = connectionManager.getConnectionHandler().execute(query, params);

            if (!resultNode.hasNext())
                throw new ApplicationObjectNotFoundException(
                        String.format(ts.getTranslatedString("module.scheduleJob.actions.job-not-found"), selectedJobId));

            ResourceIterator<Node> nodes = resultNode.columnAs( "scheduleNode" );
            Node node = nodes.next();

            StringBuilder updateQuery = new StringBuilder()
                    .append("MATCH (scheduleNode:scheduledJobs {jobId: $id}) ");

            String oldValue;
            switch (propertyToUpdate) {
                case Constants.PROPERTY_NAME:
                    if (value == null || value.trim().isEmpty())
                        throw  new InvalidArgumentException(ts.getTranslatedString("module.scheduleJob.messages.job-name-not-empty"));
                    oldValue = (String) node.getProperty(Constants.PROPERTY_NAME);
                    updateQuery.append("SET scheduleNode.").append(Constants.PROPERTY_NAME).append("= $value ");
                    if (jobsMap.containsKey(selectedJobId)) {
                        for (ExecuteJob job : runningJobs) {
                            if (job.getJobId().equals(selectedJobId)) {
                                if (job.getState() == 1)
                                    throw new ExecutionException(ts.getTranslatedString("module.scheduleJob.error.actions.update-running-job"));

                                ScheduledFuture<?> scheduledTask = jobsMap.get(selectedJobId);
                                scheduledTask.cancel(true);
                                jobsMap.remove(selectedJobId);

                                job.setName(value);
                                ScheduledFuture<?> jobSchedule = taskScheduler.schedule(job,
                                        new CronTrigger(job.getCronExpression(), TimeZone
                                                .getTimeZone(TimeZone.getDefault().getID())));
                                jobsMap.put(job.getJobId(), jobSchedule);
                                
                                log.writeLogMessage(LoggerType.INFO, SchedulingServiceImpl.class, 
                                        String.format("Job %s - %s update and scheduled", job.getName(), job.getJobId()));
                            }
                        }
                    }
                    break;
                case Constants.PROPERTY_DESCRIPTION:
                    oldValue = (String) node.getProperty(Constants.PROPERTY_DESCRIPTION);
                    updateQuery.append("SET scheduleNode.").append(Constants.PROPERTY_DESCRIPTION).append("= $value ");
                    if (jobsMap.containsKey(selectedJobId)) {
                        for (ExecuteJob job : runningJobs) {
                            if (job.getJobId().equals(selectedJobId)) {
                                if (job.getState() == 1)
                                    throw new ExecutionException(ts.getTranslatedString("module.scheduleJob.error.actions.update-running-job"));

                                ScheduledFuture<?> scheduledTask = jobsMap.get(selectedJobId);
                                scheduledTask.cancel(true);
                                jobsMap.remove(selectedJobId);

                                job.setDescription(value);
                                ScheduledFuture<?> jobSchedule = taskScheduler.schedule(job,
                                        new CronTrigger(job.getCronExpression(), TimeZone
                                                .getTimeZone(TimeZone.getDefault().getID())));
                                jobsMap.put(job.getJobId(), jobSchedule);
                                log.writeLogMessage(LoggerType.INFO, SchedulingServiceImpl.class, 
                                        String.format("Job %s - %s update and scheduled", job.getName(), job.getJobId()));
                            }
                        }
                    }
                    break;
                case Constants.PROPERTY_ENABLED:
                    params.put("value", Boolean.parseBoolean(value));
                    oldValue = String.valueOf(node.getProperty(Constants.PROPERTY_ENABLED));
                    updateQuery.append("SET scheduleNode.").append(Constants.PROPERTY_ENABLED).append("= $value ");
                    if (jobsMap.containsKey(selectedJobId)) {
                        for (ExecuteJob job : runningJobs) {
                            if (job.getJobId().equals(selectedJobId)) {
                                if (job.getState() == 1)
                                    throw new ExecutionException(ts.getTranslatedString("module.scheduleJob.error.actions.update-running-job"));

                                ScheduledFuture<?> scheduledTask = jobsMap.get(selectedJobId);
                                scheduledTask.cancel(true);
                                jobsMap.remove(selectedJobId);

                                job.setEnabled(Boolean.parseBoolean(value));
                                ScheduledFuture<?> jobSchedule = taskScheduler.schedule(job,
                                        new CronTrigger(job.getCronExpression(), TimeZone
                                                .getTimeZone(TimeZone.getDefault().getID())));
                                jobsMap.put(job.getJobId(), jobSchedule);
                                log.writeLogMessage(LoggerType.INFO, SchedulingServiceImpl.class, 
                                        String.format("Job %s - %s update and scheduled", job.getName(), job.getJobId()));
                            }
                        }
                    }
                    break;
                case Constants.PROPERTY_LOG_RESULTS:
                    params.put("value", Boolean.parseBoolean(value));
                    oldValue = String.valueOf(node.getProperty(Constants.PROPERTY_LOG_RESULTS));
                    updateQuery.append("SET scheduleNode.").append(Constants.PROPERTY_LOG_RESULTS).append("= $value ");
                    if (jobsMap.containsKey(selectedJobId)) {
                        for (ExecuteJob job : runningJobs) {
                            if (job.getJobId().equals(selectedJobId)) {
                                if (job.getState() == 1)
                                    throw new ExecutionException(ts.getTranslatedString("module.scheduleJob.error.actions.update-running-job"));

                                ScheduledFuture<?> scheduledTask = jobsMap.get(selectedJobId);
                                scheduledTask.cancel(true);
                                jobsMap.remove(selectedJobId);

                                job.setLogResults(Boolean.parseBoolean(value));
                                ScheduledFuture<?> jobSchedule = taskScheduler.schedule(job,
                                        new CronTrigger(job.getCronExpression(), TimeZone
                                                .getTimeZone(TimeZone.getDefault().getID())));
                                jobsMap.put(job.getJobId(), jobSchedule);
                                log.writeLogMessage(LoggerType.INFO, SchedulingServiceImpl.class, 
                                        String.format("Job %s - %s update and scheduled", job.getName(), job.getJobId()));
                            }
                        }
                    }
                    break;
                case Constants.PROPERTY_CRON:
                    oldValue = (String) node.getProperty(Constants.PROPERTY_CRON);
                    updateQuery.append("SET scheduleNode.").append(Constants.PROPERTY_CRON).append("= $value ");
                    if (jobsMap.containsKey(selectedJobId)) {
                        for (ExecuteJob job : runningJobs) {
                            if (job.getJobId().equals(selectedJobId)) {
                                if (job.getState() == 1)
                                    throw new ExecutionException(ts.getTranslatedString("module.scheduleJob.error.actions.update-running-job"));

                                ScheduledFuture<?> scheduledTask = jobsMap.get(selectedJobId);
                                scheduledTask.cancel(true);
                                jobsMap.remove(selectedJobId);

                                job.setCronExpression(value);
                                ScheduledFuture<?> jobSchedule = taskScheduler.schedule(job,
                                        new CronTrigger(job.getCronExpression(), TimeZone
                                                .getTimeZone(TimeZone.getDefault().getID())));
                                jobsMap.put(job.getJobId(), jobSchedule);
                                log.writeLogMessage(LoggerType.INFO, SchedulingServiceImpl.class, 
                                        String.format("Job %s - %s update and scheduled", job.getName(), job.getJobId()));
                            }
                        }
                    }
                    break;
                default:
                    throw new InvalidArgumentException(
                            String.format(ts.getTranslatedString("module.general.messages.invalid-property"), propertyToUpdate));

            }

            connectionManager.getConnectionHandler().execute(updateQuery.toString(), params);
            tx.success();

            return new ChangeDescriptor(propertyToUpdate, oldValue, value,
                    String.format(ts.getTranslatedString("module.scheduleJob.actions.update-scheduleJob.ui.update-log"), node.getProperty(Constants.PROPERTY_NAME)));
        }
    }

    @Override
    public void deleteScheduleJob(String jobId) throws ApplicationObjectNotFoundException {
        try(Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            Map<String, Object> params = new HashMap<>();
            params.put("jobId", jobId);
            Result searchResult = connectionManager.getConnectionHandler().execute(
                    "MATCH (scheduleJob:scheduledJobs {jobId: $jobId}) " +
                            "RETURN scheduleJob AS job", params);
            if (!searchResult.hasNext())
                throw new ApplicationObjectNotFoundException(String.format(
                        ts.getTranslatedString("module.scheduleJob.actions.job-not-found"), jobId));

            connectionManager.getConnectionHandler().execute("MATCH (scheduleJob:scheduledJobs {jobId: $jobId}) " +
                    "DETACH DELETE scheduleJob", params);

            tx.success();
        }
    }

    // </editor-fold>

    // <editor-fold desc="Jobs Pools" defaultstate="collapsed">

    @Override
    public String createScheduleJobsPools(String name, String description) throws InvalidArgumentException, ExecutionException {
        if (name == null || name.trim().isEmpty())
            throw new InvalidArgumentException(ts.getTranslatedString("module.scheduleJob.messages.job-name-not-empty"));
        try(Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            Map<String, Object> params = new HashMap<>();
            params.put("_uuid", UUID.randomUUID().toString());
            params.put("name", name);
            params.put("description", description);
            Map<String, Object> properties = new HashMap<>();
            properties.put("properties", params);

            Result poolResult = connectionManager.getConnectionHandler().execute("CREATE (pool:scheduledJobsPools $properties) " +
                            "RETURN pool._uuid AS id"
                    , properties);
            if (poolResult.hasNext()) {
                tx.success();
                return (String) poolResult.next().get("id");
            }
            throw new ExecutionException(ts.getTranslatedString("module.scheduleJob.error.actions.create-job-pool"));
        }
    }

    @Override
    public List<InventoryObjectPool> getScheduleJobsPools(int skip, int limit) {
        try (Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            List<InventoryObjectPool> pools = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();
            params.put("skip", skip);
            params.put("limit", limit);
            Result poolResult = connectionManager.getConnectionHandler().execute("MATCH (scheduleJobPool:scheduledJobsPools) " +
                    "RETURN scheduleJobPool AS pool SKIP $skip LIMIT $limit", params);
            ResourceIterator<Node> nodes = poolResult.columnAs( "pool" );
            while (nodes.hasNext()) {
                Node node = nodes.next();
                pools.add(new InventoryObjectPool(
                        (String) node.getProperty("_uuid"),
                        (String) node.getProperty("name"),
                        (String) node.getProperty("description"),
                        "Schedule Job",
                        2
                ));
            }
            tx.success();
            return pools;
        }
    }

    @Override
    public ChangeDescriptor updateScheduleJobsPools(String poolId, String propertyToUpdate, String value, String userName)
            throws InvalidArgumentException, ApplicationObjectNotFoundException {
        try(Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", poolId);
            params.put("value", value);
            String query = "MATCH (poolNode:scheduledJobsPools {_uuid: $id}) RETURN poolNode AS poolNode";
            Result resultNode = connectionManager.getConnectionHandler().execute(query, params);

            if (!resultNode.hasNext())
                throw new ApplicationObjectNotFoundException(
                        String.format(ts.getTranslatedString("module.general.messages.pool-id-not-found"), poolId));

            ResourceIterator<Node> nodes = resultNode.columnAs( "poolNode" );
            Node node = nodes.next();

            StringBuilder updateQuery = new StringBuilder()
                    .append("MATCH (poolNode:scheduledJobsPools {_uuid: $id}) ");

            String oldValue;
            switch (propertyToUpdate) {
                case Constants.PROPERTY_NAME:
                    if (value == null || value.trim().isEmpty())
                        throw  new InvalidArgumentException(ts.getTranslatedString("module.general.messages.pool-name-not-empty"));
                    oldValue = (String) node.getProperty(Constants.PROPERTY_NAME);
                    updateQuery.append("SET poolNode.").append(Constants.PROPERTY_NAME).append("= $value ");
                    break;
                case Constants.PROPERTY_DESCRIPTION:
                    oldValue = (String) node.getProperty(Constants.PROPERTY_DESCRIPTION);
                    updateQuery.append("SET poolNode.").append(Constants.PROPERTY_DESCRIPTION).append("= $value ");
                    break;
                default:
                    throw new InvalidArgumentException(
                            String.format(ts.getTranslatedString("module.general.messages.invalid-property"), propertyToUpdate));
            }

            connectionManager.getConnectionHandler().execute(updateQuery.toString(), params);
            tx.success();

            ChangeDescriptor changeDescriptor = new ChangeDescriptor(propertyToUpdate, oldValue, value,
                    String.format(ts.getTranslatedString("module.scheduleJob.actions.update-scheduleJob-pool.ui.update-log"), poolId));
//            aem.createObjectActivityLogEntry(userName, "scheduledJobsPools", poolId, ActivityLogEntry.ACTIVITY_TYPE_UPDATE_APPLICATION_OBJECT, changeDescriptor);

            aem.createGeneralActivityLogEntry(userName, ActivityLogEntry.ACTIVITY_TYPE_UPDATE_APPLICATION_OBJECT, changeDescriptor);

            return changeDescriptor;
        }
    }

    @Override
    public void deleteScheduleJobsPools (String poolId) throws ApplicationObjectNotFoundException {
        try (Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", poolId);
            Result searchQuery = connectionManager.getConnectionHandler().execute(
                    "MATCH (poolNode:scheduledJobsPools {_uuid: $id}) RETURN poolNode", params);

            if (!searchQuery.hasNext())
                throw new ApplicationObjectNotFoundException(String.format(
                        ts.getTranslatedString("module.scheduleJob.actions.job-pool-not-found"), poolId));

            String query = "MATCH (poolNode:scheduledJobsPools {_uuid: $id}) " +
                    "OPTIONAL MATCH (poolNode)<-[:CHILD_OF_SPECIAL]-(scheduleJob:scheduledJobs)" +
                    "DETACH DELETE (scheduleJob)" +
                    "DETACH DELETE (poolNode)";
            connectionManager.getConnectionHandler().execute(query, params);
            tx.success();
        }
    }

    // </editor-fold>

    // <editor-fold desc="Users" defaultstate="collapsed">

    @Override
    public ChangeDescriptor assignUserToJob(long userId, String jobId) throws InvalidArgumentException,
            ExecutionException, ApplicationObjectNotFoundException{
        try (Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            Map<String, Object> params = new HashMap<>();
            params.put("jobId", jobId);
            params.put("userId", userId);

            String searchQuery = "MATCH (scheduleJob:scheduledJobs {jobId: $jobId}) " +
                    "MATCH (user:users) WHERE ID(user) = $userId " +
                    "RETURN CASE WHEN EXISTS((scheduleJob)-[:HAS_USER]->(user)) THEN TRUE ELSE FALSE END AS exists";

            Result searchResult = connectionManager.getConnectionHandler().execute(searchQuery, params);

            if (searchResult.hasNext()) {
                if ( !(boolean) searchResult.next().get("exists")) {
                    Node userNode;
                    List<Node> usersNodes = new ArrayList<>();
                    List<UserProfileLight> users = new ArrayList<>();
                    String userName = "";
                    String jobName = "";

                    String query = "MATCH (scheduleJob:scheduledJobs {jobId: $jobId}) " +
                            "MATCH (user:users) WHERE ID(user) = $userId " +
                            "CREATE (scheduleJob)-[:HAS_USER]->(user) " +
                            "WITH scheduleJob, user " +
                            "OPTIONAL MATCH (scheduleJob)-[:HAS_USER]->(users)  " +
                            "RETURN scheduleJob.name AS jobName, user.name AS userName, users";
                    Result result = connectionManager.getConnectionHandler().execute(query, params);

                    while (result.hasNext()) {
                        Map<String, Object> row = result.next();
                        jobName = (String) row.get("jobName");
                        userName = (String) row.get("userName");
                        userNode = row.containsKey("users") ? (Node) row.get("users") : null;
                        if (userNode != null)
                            usersNodes.add(userNode);
                    }

                    if (jobsMap.containsKey(jobId)) {

                        usersNodes.forEach(node -> {
                            users.add(new UserProfileLight(node.getId(),
                                    (String)node.getProperty(UserProfile.PROPERTY_NAME),
                                    (String)node.getProperty(UserProfile.PROPERTY_FIRST_NAME),
                                    (String)node.getProperty(UserProfile.PROPERTY_LAST_NAME),
                                    (boolean)node.getProperty(UserProfile.PROPERTY_ENABLED),
                                    (long)node.getProperty(UserProfile.PROPERTY_CREATION_DATE),
                                    node.hasProperty(UserProfile.PROPERTY_TYPE) ?  //To keep backward compatibility
                                            (int)node.getProperty(UserProfile.PROPERTY_TYPE) : UserProfile.USER_TYPE_GUI,
                                    node.hasProperty(UserProfile.PROPERTY_EMAIL) ? //To keep backward compatibility
                                            (String) node.getProperty(UserProfile.PROPERTY_EMAIL) : null));
                        });

                        for (ExecuteJob runningJob: runningJobs) {
                            if (runningJob.getJobId().equals(jobId)) {
                                if (runningJob.getState() == 1)
                                    throw new ExecutionException(ts.getTranslatedString("module.scheduleJob.error.actions.update-running-job"));

                                ScheduledFuture<?> scheduledTask = jobsMap.get(jobId);
                                scheduledTask.cancel(true);
                                jobsMap.remove(jobId);

                                runningJob.setUsers(users);
                                ScheduledFuture<?> jobSchedule = taskScheduler.schedule(runningJob,
                                        new CronTrigger(runningJob.getCronExpression(), TimeZone
                                                .getTimeZone(TimeZone.getDefault().getID())));
                                jobsMap.put(runningJob.getJobId(), jobSchedule);
                                log.writeLogMessage(LoggerType.INFO, SchedulingServiceImpl.class, 
                                        String.format("Job %s - %s scheduled", runningJob.getName(), runningJob.getJobId()));
                            }
                        }
                    }

                    tx.success();

                    return new ChangeDescriptor("", "", "",
                            String.format(ts.getTranslatedString("module.scheduleJob.user.actions.new-job-user-log"), userName, jobName));
                } else
                    throw new InvalidArgumentException(ts.getTranslatedString("module.scheduleJob.user.error.actions.assign-user-to-job-invalid-user"));
            } else
                throw new ApplicationObjectNotFoundException(ts.getTranslatedString("module.scheduleJob.user.error.actions.assign-user-to-job"));
        }
    }

    @Override
    public ChangeDescriptor deleteUserFromJob(long userId, String jobId) throws ApplicationObjectNotFoundException, ExecutionException {
        try (Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            Map<String, Object> params = new HashMap<>();
            params.put("jobId", jobId);
            params.put("userId", userId);

            String query = "MATCH (scheduleJob:scheduledJobs {jobId: $jobId})" +
                    "-[r:HAS_USER]->(user:users) WHERE ID(user) = $userId " +
                    "DELETE r " +
                    "WITH scheduleJob, user " +
                    "OPTIONAL MATCH (scheduleJob)-[:HAS_USER]->(users)  " +
                    "RETURN scheduleJob.name AS jobName, user.name AS userName, users";
            Result result = connectionManager.getConnectionHandler().execute(query, params);
            if (!result.hasNext())
                throw new ApplicationObjectNotFoundException(ts.getTranslatedString("module.scheduleJob.user.error.actions.deleting-user-to-job"));

            Node userNode;
            List<Node> usersNodes = new ArrayList<>();
            List<UserProfileLight> users = new ArrayList<>();
            String userName = "";
            String jobName = "";

            while (result.hasNext()) {
                Map<String, Object> row = result.next();
                jobName = (String) row.get("jobName");
                userName = (String) row.get("userName");
                userNode = row.containsKey("users") ? (Node) row.get("users") : null;
                if (userNode != null)
                    usersNodes.add(userNode);
            }

            if (jobsMap.containsKey(jobId)) {
                usersNodes.forEach(node -> {
                    users.add(new UserProfileLight(node.getId(),
                            (String)node.getProperty(UserProfile.PROPERTY_NAME),
                            (String)node.getProperty(UserProfile.PROPERTY_FIRST_NAME),
                            (String)node.getProperty(UserProfile.PROPERTY_LAST_NAME),
                            (boolean)node.getProperty(UserProfile.PROPERTY_ENABLED),
                            (long)node.getProperty(UserProfile.PROPERTY_CREATION_DATE),
                            node.hasProperty(UserProfile.PROPERTY_TYPE) ?  //To keep backward compatibility
                                    (int)node.getProperty(UserProfile.PROPERTY_TYPE) : UserProfile.USER_TYPE_GUI,
                            node.hasProperty(UserProfile.PROPERTY_EMAIL) ? //To keep backward compatibility
                                    (String) node.getProperty(UserProfile.PROPERTY_EMAIL) : null));
                });

                for ( ExecuteJob runningJob: runningJobs) {
                    if (runningJob.getJobId().equals(jobId)) {
                        if (runningJob.getState() == 1)
                            throw new ExecutionException(ts.getTranslatedString("module.scheduleJob.error.actions.update-running-job"));

                        ScheduledFuture<?> scheduledTask = jobsMap.get(jobId);
                        scheduledTask.cancel(true);
                        jobsMap.remove(jobId);

                        runningJob.setUsers(users);
                        ScheduledFuture<?> jobSchedule = taskScheduler.schedule(runningJob,
                                new CronTrigger(runningJob.getCronExpression(), TimeZone
                                        .getTimeZone(TimeZone.getDefault().getID())));
                        jobsMap.put(runningJob.getJobId(), jobSchedule);
                        log.writeLogMessage(LoggerType.INFO, SchedulingServiceImpl.class, 
                                        String.format("Job %s - %s scheduled", runningJob.getName(), runningJob.getJobId()));
                    }
                }
            }

            tx.success();

            return new ChangeDescriptor("", "", "",
                    String.format(ts.getTranslatedString("module.scheduleJob.user.actions.delete-task-user-log"), userName, jobName));
        }
    }

    @Override
    public List<UserProfileLight> getAssignUsersToJob(String jobId, int skip, int limit) {
        try (Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            List<UserProfileLight> users = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();
            params.put("jobId", jobId);
            params.put("skip", skip);
            params.put("limit", limit);

            Result result = connectionManager.getConnectionHandler().execute(
                    "MATCH (scheduleJob:scheduledJobs {jobId: $jobId})" +
                    "MATCH (scheduleJob)-[:HAS_USER]->(user:users) " +
                    "RETURN user AS users SKIP $skip LIMIT $limit", params);
            ResourceIterator<Node> nodes = result.columnAs( "users" );
            while (nodes.hasNext()) {
                Node node = nodes.next();
                users.add(new UserProfileLight(node.getId(), (String) node.getProperty("name")));
            }
            tx.success();
            return users;
        }
    }

    @Override
    public ChangeDescriptor updateUsersForJob(ExecuteJob selectedJob, List<UserProfile> users) throws ExecutionException {
        try (Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            List<Long> usersIds = users.stream()
                    .map(UserProfile::getId)
                    .collect(Collectors.toList());

            if (jobsMap.containsKey(selectedJob.getJobId())) {
                for (ExecuteJob job : runningJobs) {
                    if (job.getJobId().equals(selectedJob.getJobId())) {
                        if (job.getState() == 1)
                            throw new ExecutionException(ts.getTranslatedString("module.scheduleJob.error.actions.update-running-job"));

                        ScheduledFuture<?> scheduledTask = jobsMap.get(selectedJob.getJobId());
                        scheduledTask.cancel(true);
                        jobsMap.remove(selectedJob.getJobId());

                        job.setUsers(new ArrayList<>(users));
                        ScheduledFuture<?> jobSchedule = taskScheduler.schedule(job,
                                new CronTrigger(job.getCronExpression(), TimeZone
                                        .getTimeZone(TimeZone.getDefault().getID())));
                        jobsMap.put(job.getJobId(), jobSchedule);
                        log.writeLogMessage(LoggerType.INFO, SchedulingServiceImpl.class,
                                String.format("Job %s - %s updated and scheduled", job.getName(), job.getJobId()));
                    }
                }
            }

            Map<String, Object> params = new HashMap<>();
            params.put("jobId", selectedJob.getJobId());

            // Find currently assigned users
            Result result = connectionManager.getConnectionHandler().execute(
                    "MATCH (scheduleJob:scheduledJobs {jobId: $jobId}) " +
                            "MATCH (scheduleJob)-[:HAS_USER]->(user:users) " +
                            "RETURN user", params);

            Map<Long, String> assignedUsersMap = new HashMap<>();
            ResourceIterator<Node> nodes = result.columnAs("user");
            while (nodes.hasNext()) {
                Node node = nodes.next();
                assignedUsersMap.put(node.getId(), node.getProperty("name").toString());
            }

            // Users to remove
            List<Long> usersToRemove = assignedUsersMap.keySet().stream()
                    .filter(id -> !usersIds.contains(id))
                    .collect(Collectors.toList());
            List<String> usersToRemoveNames = usersToRemove.stream()
                    .map(assignedUsersMap::get)
                    .collect(Collectors.toList());

            // Users to add
            List<Long> usersToAdd = usersIds.stream()
                    .filter(id -> !assignedUsersMap.containsKey(id))
                    .collect(Collectors.toList());
            List<String> usersToAddNames = users.stream()
                    .filter(user -> usersToAdd.contains(user.getId()))
                    .map(UserProfile::getUserName)
                    .collect(Collectors.toList());

            // Remove relationships
            if (!usersToRemove.isEmpty()) {
                params.put("usersToRemove", usersToRemove);
                connectionManager.getConnectionHandler().execute(
                        "MATCH (scheduleJob:scheduledJobs {jobId: $jobId}) " +
                                "MATCH (scheduleJob)-[r:HAS_USER]->(user:users) " +
                                "WHERE ID(user) IN $usersToRemove " +
                                "DELETE r", params);
            }

            // Add relationships
            if (!usersToAdd.isEmpty()) {
                params.put("usersToAdd", usersToAdd);
                connectionManager.getConnectionHandler().execute(
                        "MATCH (scheduleJob:scheduledJobs {jobId: $jobId}) " +
                                "MATCH (user:users) " +
                                "WHERE ID(user) IN $usersToAdd " +
                                "CREATE (scheduleJob)-[:HAS_USER]->(user)", params);
            }

            tx.success();

            String usersRemovedString = String.join(", ", usersToRemoveNames);
            String usersAddedString = String.join(", ", usersToAddNames);

            return new ChangeDescriptor("", usersRemovedString, usersAddedString,
                    String.format(ts.getTranslatedString("module.scheduleJob.user.actions.update-user-log"), selectedJob.getName()));
        }
    }

    @Override
    public ChangeDescriptor updateTaskForJob(ExecuteJob selectedJob, Task task) throws InvalidArgumentException, ApplicationObjectNotFoundException, ExecutionException {
        try (Transaction tx = connectionManager.getConnectionHandler().beginTx()) {
            Map<String, Object> params = new HashMap<>();
            String oldTaskName = "";
            params.put("jobId", selectedJob.getJobId());

            // Find currently assigned task
            Result result = connectionManager.getConnectionHandler().execute(
                    "MATCH (scheduleJob:scheduledJobs {jobId: $jobId}) " +
                            "OPTIONAL MATCH (scheduleJob)-[:HAS_TASK]->(task:tasks) " +
                            "RETURN task", params);

            if (jobsMap.containsKey(selectedJob.getJobId())) {
                for (ExecuteJob job : runningJobs) {
                    if (job.getJobId().equals(selectedJob.getJobId())) {
                        if (job.getState() == 1)
                            throw new ExecutionException(ts.getTranslatedString("module.scheduleJob.error.actions.update-running-job"));

                        ScheduledFuture<?> scheduledTask = jobsMap.get(selectedJob.getJobId());
                        scheduledTask.cancel(true);
                        jobsMap.remove(selectedJob.getJobId());

                        job.setTaskId(task.getId());
                        ScheduledFuture<?> jobSchedule = taskScheduler.schedule(job,
                                new CronTrigger(job.getCronExpression(), TimeZone
                                        .getTimeZone(TimeZone.getDefault().getID())));
                        jobsMap.put(job.getJobId(), jobSchedule);
                        log.writeLogMessage(LoggerType.INFO, SchedulingServiceImpl.class,
                                String.format("Job %s - %s update and scheduled", job.getName(), job.getJobId()));
                    }
                }
            }

            if (result.hasNext()) {
                long assignedTaskId = 0;
                ResourceIterator<Node> nodes = result.columnAs("task");
                while (nodes.hasNext()) {
                    Node node = nodes.next();
                    oldTaskName = node.getProperty("name").toString();
                    assignedTaskId = (node.getId());
                }

                // Add relationships
                if (assignedTaskId != task.getId()) {
                    params.put("taskToAdd", task.getId());
                    connectionManager.getConnectionHandler().execute(
                            "MATCH (scheduleJob:scheduledJobs {jobId: $jobId}) " +
                                    "MATCH (scheduleJob)-[r:HAS_TASK]->(task:tasks) "+
                                    "DELETE r " +
                                    "WITH scheduleJob MATCH (task:tasks) " +
                                    "WHERE ID(task) = $taskToAdd " +
                                    "CREATE (scheduleJob)-[:HAS_TASK]->(task)", params);
                } else
                    throw new InvalidArgumentException(ts.getTranslatedString("module.scheduleJob.task.error.actions.assign-task-to-job-invalid-task"));
                tx.success();
            } else
                throw new ApplicationObjectNotFoundException(ts.getTranslatedString("module.scheduleJob.task.error.actions.update-task-to-job"));

            return new ChangeDescriptor("", oldTaskName, task.getName(),
                    String.format(ts.getTranslatedString("module.scheduleJob.task.actions.update-task-log"), selectedJob.getName()));
        }
    }

    // </editor-fold>

    @Override
    public List<AbstractProperty> getAbstractPropertiesFromJob(ExecuteJob job) throws ApplicationObjectNotFoundException {
        List<AbstractProperty> objectProperties = new ArrayList<>();

        objectProperties.add(new StringProperty(Constants.PROPERTY_NAME, Constants.PROPERTY_NAME,
                Constants.PROPERTY_NAME, job.getName() == null || job.getName().isEmpty() ?
                AbstractProperty.NULL_LABEL : job.getName(), ts));

        objectProperties.add(new StringProperty(Constants.PROPERTY_DESCRIPTION, Constants.PROPERTY_DESCRIPTION,
                Constants.PROPERTY_DESCRIPTION, job.getDescription() == null || job.getDescription().isEmpty() ?
                AbstractProperty.NULL_LABEL : job.getDescription(), ts));

        objectProperties.add(new BooleanProperty(Constants.PROPERTY_ENABLED, Constants.PROPERTY_ENABLED,
                Constants.PROPERTY_ENABLED, job.isEnabled(), ts));;

        objectProperties.add(new BooleanProperty(Constants.PROPERTY_LOG_RESULTS, Constants.PROPERTY_LOG_RESULTS,
                Constants.PROPERTY_LOG_RESULTS, job.isLogResults(), ts));

        objectProperties.add(new CronProperty(Constants.PROPERTY_CRON, Constants.PROPERTY_CRON,
                Constants.PROPERTY_CRON, CronDefinition.getCronSummary(job.getCronExpression(), ts), ts));

        objectProperties.add(new UserProperty(Constants.LABEL_USER, Constants.LABEL_USER, Constants.LABEL_USER,
                this.getAssignUsersToJob(job.getJobId(), 0, Integer.MAX_VALUE), ts, aem));

        objectProperties.add(new TaskProperty(Constants.LABEL_TASKS, Constants.LABEL_TASKS, Constants.LABEL_TASKS,
                aem.getTask(job.getTaskId()), ts, aem));

        return objectProperties;
    }

    @Override
    public void addJobUpdateListener(JobExecutionListener listener) {
        jobUpdateListeners.add(listener);
    }

    @Override
    public void removeJobUpdateListener(JobExecutionListener listener) {
        jobUpdateListeners.remove(listener);
    }

}