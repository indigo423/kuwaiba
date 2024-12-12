package org.neotropic.kuwaiba.core.services.scheduling.schemas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.ResultMessage;
import org.neotropic.kuwaiba.core.apis.persistence.application.TaskResult;
import org.neotropic.kuwaiba.core.apis.persistence.application.UserProfileLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.neotropic.kuwaiba.modules.core.logging.LoggerType;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

/**
 * Class that representing a scheduled job
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class ExecuteJob extends ScheduleJobs {

    private ApplicationEntityManager aem;

    private long taskId;

    private List<UserProfileLight> users = new ArrayList<>();

    private List<JobExecutionListener> listeners = new ArrayList<>();
    
    private LoggingService log;

    public ExecuteJob(String name, String description,
                      String cronExpression, boolean enabled,
                      boolean logResults, ApplicationEntityManager aem,
                      List<UserProfileLight> users, LoggingService log)
    {
        super(name, description, cronExpression, enabled, logResults);
        this.aem = aem;
        this.users = users;
        this.log = log;
    }

    public ExecuteJob(String name, String description,
                      String cronExpression, boolean enabled, boolean logResults) {
        super(name, description, cronExpression, enabled, logResults);
    }

    @Override
    public void run() {
        try {
            if (isEnabled()) {
                setState(STATE_RUNNING);
                notifyJobExecuted();
                log.writeLogMessage(LoggerType.INFO, ExecuteJob.class, 
                        String.format("The execution of the job %s - %s began ", getName(), getJobId()));
                
                TaskResult taskResult = aem.executeTask(taskId);

                printLogResultTask(taskResult);

                setState(STATE_EXECUTED);
                notifyJobExecuted();
                log.writeLogMessage(LoggerType.INFO, ExecuteJob.class, 
                        String.format("The execution of the job %s - %s finished ", getName(), getJobId()));
            }
        } catch (ApplicationObjectNotFoundException | InvalidArgumentException | JsonProcessingException | BusinessObjectNotFoundException ex) {
            setState(STATE_KILLED);
            notifyJobExecuted();
            log.writeLogMessage(LoggerType.ERROR, ExecuteJob.class, 
                        String.format("Error executing scheduled job %s - %s", getName(), ex.getMessage()));
        }
    }

    private boolean isValidJson(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(jsonString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void printLogResultTask(TaskResult result) throws JsonProcessingException, ApplicationObjectNotFoundException, BusinessObjectNotFoundException {        
        for (ResultMessage msj : result.getMessages()) {
            if (isValidJson(msj.getMessage())) {
                String affectedProperties = "";
                String oldValue = "";
                String newValue = "";
                String notes = "";
                String className = "";
                String oid = "";
                int type = 0;

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(msj.getMessage());

                if (jsonNode.has("id")) {
                    String id = jsonNode.get("id").asText();
                    JsonNode parametersNode = jsonNode.get("parameters");
                    if (id.equals("createObjectActivityLogEntry")) {
                        for (JsonNode parameterNode : parametersNode) {
                            // Obtener el valor de las claves "key" y "value"
                            switch (parameterNode.get("key").asText()) {
                                case "affectedProperties":
                                    affectedProperties = parameterNode.get("value").asText();
                                    break;
                                case "oldValues":
                                    oldValue = parameterNode.get("value").asText();
                                    break;
                                case "newValues":
                                    newValue = parameterNode.get("value").asText();
                                    break;
                                case "notes":
                                    notes = parameterNode.get("value").asText();
                                    break;
                                case "type":
                                    type = parameterNode.get("value").asInt();
                                    break;
                                case "className":
                                    className = parameterNode.get("value").asText();
                                    break;
                                case "oid":
                                    oid = parameterNode.get("value").asText();
                                    break;
                                default:
                                    break;
                            }

                        }
                        aem.createObjectActivityLogEntry(SYSTEM_LOG, className, oid, type, affectedProperties, oldValue, newValue, notes);
                    } else if (id.equals("createGeneralActivityLogEntry")) {
                        for (JsonNode parameterNode : parametersNode) {
                            if (parameterNode.get("key").asText().equals("notes"))
                                notes = parameterNode.get("value").asText();
                            else if (parameterNode.get("key").asText().equals("type")) {
                                type = parameterNode.get("value").asInt();
                            }
                        }
                        aem.createGeneralActivityLogEntry(SYSTEM_LOG, type, notes);
                    }
                } else 
                    log.writeLogMessage(LoggerType.ERROR, ExecuteJob.class, "JSON malformed");                                                   
            } else
                if (isLogResults())
                    log.writeLogMessage(LoggerType.INFO, ExecuteJob.class, 
                        msj.getMessage());

        }
         
    }

    public void addJobExecutionListener(JobExecutionListener listener) {
        listeners.add(listener);
    }

    public void removeJobExecutionListener(JobExecutionListener listener) {
        listeners.remove(listener);
    }

    private void notifyJobExecuted() {
        for (JobExecutionListener listener : listeners) {
            listener.onJobExecuted(this);
        }
    }
}
