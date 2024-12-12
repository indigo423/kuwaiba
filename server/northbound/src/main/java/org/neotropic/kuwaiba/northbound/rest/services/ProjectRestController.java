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
package org.neotropic.kuwaiba.northbound.rest.services;

import com.neotropic.kuwaiba.modules.commercial.planning.projects.ProjectsService;
import java.util.HashMap;
import java.util.List;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObject;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ArraySizeMismatchException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.NotAuthorizedException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.OperationNotPermittedException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.logging.LoggerType;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;
import org.neotropic.kuwaiba.northbound.rest.RestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Projects Rest Controller.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@RestController
@RequestMapping(ProjectRestController.PATH)
public class ProjectRestController implements ProjectRestOpenApi {
    /**
     * Reference to the Projects Service
     */
    @Autowired
    private ProjectsService ps;
    /**
     * Reference to the Application Entity Manager.
     */
    @Autowired
    private ApplicationEntityManager aem;
    
    /**
     * Reference to the Translation Service.
     */
    @Autowired
    private TranslationService ts;
    
    /**
     * Reference to the Logging service.
     */
    @Autowired
    private LoggingService log;
    
    /**
     * Path that includes the Kuwaiba version and module
     */
    public static final String PATH = "/v2.1.1/planning/projects/"; //NOI18N
    
    // <editor-fold desc="projects" defaultstate="collapsed">
    
    /**
     * Creates a project.
     * @param projectPoolId The project pool id.
     * @param projectClassName The project class name. Must be subclass of GenericProject.
     * @param attributes The set of initial attributes. If no attribute name is specified, an empty string will be used.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     * @return The id of the newly created project.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "createProject/{projectPoolId}/{projectClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createProject(
            @PathVariable(RestConstants.PROJECT_POOL_ID) String projectPoolId,
            @PathVariable(RestConstants.PROJECT_CLASS_NAME) String projectClassName,
            @RequestBody(required = false) HashMap<String, String> attributes,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("createProject", "127.0.0.1", sessionId);
            return ps.createProject(
                    projectPoolId,
                    projectClassName,
                    attributes != null ? attributes : new HashMap<String, String>(),
                    userName
            );
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Creates a project pool.
     * @param poolName The pool name.
     * @param poolDescription The pool description.
     * @param poolClassName The pool class name. What kind of objects can this pool contain? 
     * Must be subclass of GenericProject.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     * @return The id of the newly created project pool.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "createProjectPool/{poolName}/{poolDescription}/{poolClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createProjectPool(
            @PathVariable(RestConstants.POOL_NAME) String poolName,
            @PathVariable(RestConstants.POOL_DESCRIPTION) String poolDescription,
            @PathVariable(RestConstants.POOL_CLASS_NAME) String poolClassName,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("createProjectPool", "127.0.0.1", sessionId);
            return ps.createProjectPool(poolName, poolDescription, poolClassName, userName);
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    } 
    
    /**
     * Creates an Activity inside a project.
     * @param projectId The project id.
     * @param projectClassName The project class name.
     * @param activityClassName The activity class name. Must be subclass of GenericActivity.
     * @param attributes The set of initial attributes. If no attribute name is specified, an empty string will be used.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     * @return The id of the newly created activity.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "createActivity/{projectId}/{projectClassName}/{activityClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createActivity(
            @PathVariable(RestConstants.PROJECT_ID) String projectId,
            @PathVariable(RestConstants.PROJECT_CLASS_NAME) String projectClassName,
            @PathVariable(RestConstants.ACTIVITY_CLASS_NAME) String activityClassName,
            @RequestBody(required = false) HashMap<String, String> attributes,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("createActivity", "127.0.0.1", sessionId);
            return ps.createActivity(
                    projectId,
                    projectClassName,
                    activityClassName,
                    attributes != null ? attributes : new HashMap<String, String>(),
                    userName
            );
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Updates one or many project attributes.
     * @param projectClassName The project class name.
     * @param projectId The project id.
     * @param attributes The set of initial attributes. If no attribute name is specified, an empty string will be used.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "updateProject/{projectClassName}/{projectId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateProject(
            @PathVariable(RestConstants.PROJECT_CLASS_NAME) String projectClassName,
            @PathVariable(RestConstants.PROJECT_ID) String projectId,
            @RequestBody(required = false) HashMap<String, String> attributes,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("updateProject", "127.0.0.1", sessionId);
            ps.updateProject(
                    projectClassName,
                    projectId,
                    attributes != null ? attributes : new HashMap<String, String>(),
                    userName
            );
        } catch (InvalidArgumentException | OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Updates the attributes of a project pool.
     * @param poolId The id of the pool to be updated.
     * @param poolClassName The pool class name.
     * @param poolName The attribute value for pool name.
     * @param poolDescription The attribute value for pool description.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "updateProjectPool/{poolId}/{poolClassName}/{poolName}/{poolDescription}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateProjectPool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.POOL_CLASS_NAME) String poolClassName,
            @PathVariable(RestConstants.POOL_NAME) String poolName,
            @PathVariable(RestConstants.POOL_DESCRIPTION) String poolDescription,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("updateProjectPool", "127.0.0.1", sessionId);
            ps.updateProjectPool(poolId, poolClassName, poolName, poolDescription, userName);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /** 
     * Updates one or many activity attributes.
     * @param activityClassName The activity class name. Must be subclass of GenericActivity.
     * @param activityId The activity id.
     * @param attributes The set of initial attributes. If no attribute name is specified, an empty string will be used.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "updateActivity/{activityClassName}/{activityId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateActivity(
            @PathVariable(RestConstants.ACTIVITY_CLASS_NAME) String activityClassName,
            @PathVariable(RestConstants.ACTIVITY_ID) String activityId,
            @RequestBody(required = false) HashMap<String, String> attributes,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("updateActivity", "127.0.0.1", sessionId);
            ps.updateActivity(
                    activityClassName,
                    activityId,
                    attributes != null ? attributes : new HashMap<String, String>(),
                    userName
            );
        } catch (InvalidArgumentException | OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Deletes a project and delete its association with the related inventory objects. 
     * These objects will remain untouched.
     * @param projectClassName The project class name.
     * @param projectId The project id.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.DELETE,
            value = "deleteProject/{projectClassName}/{projectId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteProject(
            @PathVariable(RestConstants.PROJECT_CLASS_NAME) String projectClassName,
            @PathVariable(RestConstants.PROJECT_ID) String projectId,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("deleteProject", "127.0.0.1", sessionId);
            ps.deleteProject(projectClassName, projectId, userName);
        } catch (InvalidArgumentException | OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Deletes a project pool.
     * @param poolId The pool id.
     * @param poolClassName The pool class name.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.DELETE,
            value = "deleteProjectPool/{poolId}/{poolClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteProjectPool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.POOL_CLASS_NAME) String poolClassName,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("deleteProjectPool", "127.0.0.1", sessionId);
            ps.deleteProjectPool(poolId, poolClassName, userName);
        } catch (InvalidArgumentException | OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Deletes an activity and delete its association with the related inventory objects. 
     * These objects will remain untouched.
     * @param activityClassName The activity class name. Must be subclass of GenericActivity.
     * @param activityId The activity id.
     * @param releaseRelationships Release of existing relationships.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.DELETE,
            value = "deleteActivity/{activityClassName}/{activityId}/{releaseRelationships}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteActivity(
            @PathVariable(RestConstants.ACTIVITY_CLASS_NAME) String activityClassName,
            @PathVariable(RestConstants.ACTIVITY_ID) String activityId,
            @PathVariable(RestConstants.RELEASE_RELATIONSHIPS) boolean releaseRelationships,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("deleteActivity", "127.0.0.1", sessionId);
            ps.deleteActivity(activityClassName, activityId, releaseRelationships, userName);
        } catch (InvalidArgumentException | OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Get all projects, without filters.
     * @param page Page number of results to skip. Use -1 to retrieve all.
     * @param limit Max number of results per page. Use -1 to retrieve all.
     * @param sessionId The session token id.
     * @return The projects list.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getAllProjects/{page}/{limit}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getAllProjects(
            @PathVariable(RestConstants.PAGE) long page,
            @PathVariable(RestConstants.LIMIT) long limit,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getAllProjects", "127.0.0.1", sessionId);
            return ps.getAllProjects(page, limit);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /** 
     * Gets the projects inside a project pool.
     * @param poolId The pool id.
     * @param limit The results limit per page. Use -1 to retrieve all.
     * @param sessionId The session token id.
     * @return The projects list.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getProjectsInPool/{poolId}/{limit}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getProjectsInPool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.LIMIT) int limit,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getProjectsInPool", "127.0.0.1", sessionId);
            return ps.getProjectsInPool(poolId, limit);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Get the projects according to the filter value.
     * @param filters Map of filters key: attribute name, value: attribute value.
     * @param page Page or number of elements to skip.
     * @param limit Max count of child per page.
     * @param sessionId The session token id.
     * @return The projects list.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "getProjectsWithFilter/{page}/{limit}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getProjectsWithFilter(
            @RequestBody HashMap<String, String> filters,
            @PathVariable(RestConstants.PAGE) long page,
            @PathVariable(RestConstants.LIMIT) long limit,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getProjectsWithFilter", "127.0.0.1", sessionId);
            return ps.getProjectsWithFilter(filters, page, limit);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Gets the projects related to an object.
     * @param objectClassName The object class name.
     * @param objectId The object Id.
     * @param sessionId The session token id.
     * @return The list projects related to an object.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getProjectsRelatedToObject/{objectClassName}/{objectId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getProjectsRelatedToObject(
            @PathVariable(RestConstants.OBJECT_CLASS_NAME) String objectClassName,
            @PathVariable(RestConstants.OBJECT_ID) String objectId,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getProjectsRelatedToObject", "127.0.0.1", sessionId);
            return ps.getProjectsRelatedToObject(objectClassName, objectId);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Gets the resources related to a project.
     * @param projectClassName Project class name.
     * @param projectId Project Id.
     * @param sessionId The session token id.
     * @return The project resources list.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getProjectResources/{projectClassName}/{projectId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getProjectResources(
            @PathVariable(RestConstants.PROJECT_CLASS_NAME) String projectClassName,
            @PathVariable(RestConstants.PROJECT_ID) String projectId,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getProjectResources", "127.0.0.1", sessionId);
            return ps.getProjectResources(projectClassName, projectId);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Gets the activities inside a project.
     * @param projectClassName The project class name.
     * @param projectId The project Id.
     * @param sessionId The session token id.
     * @return The activities list.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getProjectActivities/{projectClassName}/{projectId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getProjectActivities(
            @PathVariable(RestConstants.PROJECT_CLASS_NAME) String projectClassName,
            @PathVariable(RestConstants.PROJECT_ID) String projectId,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getProjectActivities", "127.0.0.1", sessionId);
            return ps.getProjectActivities(projectClassName, projectId);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Retrieves the projects pool list.
     * @param sessionId The session token id.
     * @return The available project pools.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getProjectPools/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<InventoryObjectPool> getProjectPools(@PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getProjectPools", "127.0.0.1", sessionId);
            return ps.getProjectPools();
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Gets the project pools properties.
     * @param poolId The pool id.
     * @param poolClassName The pool class name.
     * @param sessionId The session token id.
     * @return The pool properties.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getProjectPool/{poolId}/{poolClassName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public InventoryObjectPool getProjectPool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.POOL_CLASS_NAME) String poolClassName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getProjectPool", "127.0.0.1", sessionId);
            return ps.getProjectPool(poolId, poolClassName);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Get project properties.
     * @param projectClassName The project class name. Must be subclass of GenericProject.
     * @param projectId The project id.
     * @param sessionId The session token id.
     * @return The project properties.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getProject/{projectClassName}/{projectId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public BusinessObject getProject(
            @PathVariable(RestConstants.PROJECT_CLASS_NAME) String projectClassName,
            @PathVariable(RestConstants.PROJECT_ID) String projectId,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getProject", "127.0.0.1", sessionId);
            return ps.getProject(projectClassName, projectId);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Get activity properties.
     * @param activityClassName The activity class name. Must be subclass of GenericActivity.
     * @param activityId The activity id.
     * @param sessionId The session token id.
     * @return The activity properties.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getActivity/{activityClassName}/{activityId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public BusinessObject getActivity(
            @PathVariable(RestConstants.ACTIVITY_CLASS_NAME) String activityClassName,
            @PathVariable(RestConstants.ACTIVITY_ID) String activityId,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getActivity", "127.0.0.1", sessionId);
            return ps.getActivity(activityClassName, activityId);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Creates a copy of a project.
     * @param poolId The pool id.
     * @param projectClassName The project class name.
     * @param projectId The project id.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     * @return The newly created project id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "copyProjectToPool/{poolId}/{projectClassName}/{projectId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String copyProjectToPool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.PROJECT_CLASS_NAME) String projectClassName,
            @PathVariable(RestConstants.PROJECT_ID) String projectId,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("copyProjectToPool", "127.0.0.1", sessionId);
            return ps.copyProjectToPool(poolId, projectClassName, projectId, userName);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Moves a project from a pool to another pool.
     * @param poolId The pool id.
     * @param projectClassName The project class name.
     * @param projectId The project id.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "moveProjectToPool/{poolId}/{projectClassName}/{projectId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void moveProjectToPool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.PROJECT_CLASS_NAME) String projectClassName,
            @PathVariable(RestConstants.PROJECT_ID) String projectId,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("moveProjectToPool", "127.0.0.1", sessionId);
            ps.moveProjectToPool(poolId, projectClassName, projectId, userName);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Relates a set of objects to a project.
     * @param projectClassName The project class name.
     * @param projectId The project id.
     * @param objectsClassNames The objects class names.
     * @param objectsIds The objects ids.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "relateObjectsToProject/{projectClassName}/{projectId}/{objectsClassNames}/{objectsIds}"
                    + "/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void relateObjectsToProject(
            @PathVariable(RestConstants.PROJECT_CLASS_NAME) String projectClassName,
            @PathVariable(RestConstants.PROJECT_ID) String projectId,
            @PathVariable(RestConstants.OBJECTS_CLASS_NAMES) String[] objectsClassNames, 
            @PathVariable(RestConstants.OBJECTS_IDS) String[] objectsIds,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("relateObjectsToProject", "127.0.0.1", sessionId);
            ps.relateObjectsToProject(
                    projectClassName, projectId,
                    objectsClassNames, objectsIds,
                    userName);
        } catch (ArraySizeMismatchException | InvalidArgumentException | OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Relates an object to a project.
     * @param projectClassName The project class name.
     * @param projectId The project id.
     * @param objectClassName The object class name.
     * @param objectId The object id.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "relateObjectToProject/{projectClassName}/{projectId}/{objectClassName}/{objectId}"
                    + "/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void relateObjectToProject(
            @PathVariable(RestConstants.PROJECT_CLASS_NAME) String projectClassName,
            @PathVariable(RestConstants.PROJECT_ID) String projectId,
            @PathVariable(RestConstants.OBJECT_CLASS_NAME) String objectClassName, 
            @PathVariable(RestConstants.OBJECT_ID) String objectId,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("relateObjectToProject", "127.0.0.1", sessionId);
            ps.relateObjectToProject(
                    projectClassName, projectId,
                    objectClassName, objectId,
                    userName);
        } catch (InvalidArgumentException | OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Releases an object from project.
     * @param objectClassName The object class name.
     * @param objectId The object Id.
     * @param projectClassName The project class name.
     * @param projectId The project id.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "releaseObjectFromProject/{objectClassName}/{objectId}/{projectClassName}/{projectId}"
                    + "/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void releaseObjectFromProject(
            @PathVariable(RestConstants.OBJECT_CLASS_NAME) String objectClassName, 
            @PathVariable(RestConstants.OBJECT_ID) String objectId,
            @PathVariable(RestConstants.PROJECT_CLASS_NAME) String projectClassName,
            @PathVariable(RestConstants.PROJECT_ID) String projectId,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("releaseObjectFromProject", "127.0.0.1", sessionId);
            ps.releaseObjectFromProject(
                    objectClassName, objectId,
                    projectClassName, projectId,
                    userName);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ProjectRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ProjectRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    // </editor-fold>
}