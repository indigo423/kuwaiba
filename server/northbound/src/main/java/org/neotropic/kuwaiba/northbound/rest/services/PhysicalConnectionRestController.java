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

import java.util.HashMap;
import java.util.List;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.NotAuthorizedException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.OperationNotPermittedException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.logging.LoggerType;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;
import org.neotropic.kuwaiba.modules.optional.physcon.PhysicalConnectionsService;
import org.neotropic.kuwaiba.northbound.rest.RestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Physical Connection Rest Controller.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@RestController
@RequestMapping(PhysicalConnectionRestController.PATH)
public class PhysicalConnectionRestController implements PhysicalConnectionRestOpenApi {
    /**
     * Reference to the Physical Connections Service.
     */
    @Autowired
    private PhysicalConnectionsService pcs;
            
    /**
     * Reference to the Application Entity Manager.
     */
    @Autowired
    private ApplicationEntityManager aem;
    
    /**
     * Reference to the translation service.
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
    public static final String PATH = "/v2.1.1/physical-connections/"; //NOI18N
    
    // <editor-fold desc="physical-connections" defaultstate="collapsed">
    /**
     * Creates a physical connection.
     * @param aObjectClassName The class name of the first object to related.
     * @param aObjectId The id of the first object to related.
     * @param bObjectClassName The class name of the second object to related.
     * @param bObjectId The id of the first object to related.
     * @param name The connection name.
     * @param connectionClassName The class name of the connection. Must be subclass of GenericPhysicalConnection.
     * @param templateId Template id to be used to create the current object. 
     * Use "null" as string or empty string to not use a template.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     * @return The id of the newly created physical connection.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "createPhysicalConnection/{aObjectClassName}/{aObjectId}/{bObjectClassName}/{bObjectId}/"
                    + "{name}/{connectionClassName}/{templateId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createPhysicalConnection(
            @PathVariable(RestConstants.A_OBJECT_CLASS_NAME) String aObjectClassName,
            @PathVariable(RestConstants.A_OBJECT_ID) String aObjectId,
            @PathVariable(RestConstants.B_OBJECT_CLASS_NAME) String bObjectClassName,
            @PathVariable(RestConstants.B_OBJECT_ID) String bObjectId,
            @PathVariable(RestConstants.NAME) String name,
            @PathVariable(RestConstants.CONNECTION_CLASS_NAME) String connectionClassName,
            @PathVariable(RestConstants.TEMPLATE_ID) String templateId,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("createPhysicalConnection", "127.0.0.1", sessionId);
            return pcs.createPhysicalConnection(
                    aObjectClassName, aObjectId, 
                    bObjectClassName, bObjectId,
                    name, connectionClassName,
                    templateId.equals("null") ? null : templateId,
                    userName);
        } catch (IllegalStateException | OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, PhysicalConnectionRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, PhysicalConnectionRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
            
    /**
     * Deletes a physical connection.
     * @param objectClassName The class name of the object.
     * @param objectId The id of the object.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.DELETE,
            value = "deletePhysicalConnection/{objectClassName}/{objectId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deletePhysicalConnection(
            @PathVariable(RestConstants.OBJECT_CLASS_NAME) String objectClassName,
            @PathVariable(RestConstants.OBJECT_ID) String objectId,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("deletePhysicalConnection", "127.0.0.1", sessionId);
            pcs.deletePhysicalConnection(objectClassName, objectId,  userName);
        } catch (IllegalStateException | InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, PhysicalConnectionRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, PhysicalConnectionRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Finds the physical path from one port to another.
     * @param objectClassName The source port class name.
     * @param objectId The source port id.
     * @param sessionId The session token id.
     * @return A list of objects that make part of the physical trace.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getPhysicalPath/{objectClassName}/{objectId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getPhysicalPath(
            @PathVariable(RestConstants.OBJECT_CLASS_NAME) String objectClassName,
            @PathVariable(RestConstants.OBJECT_ID) String objectId,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getPhysicalPath", "127.0.0.1", sessionId);
            return pcs.getPhysicalPath(objectClassName, objectId);
        } catch (IllegalStateException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, PhysicalConnectionRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, PhysicalConnectionRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Gets A tree representation of all physical paths as a hash map.
     * @param objectClassName The source port class name.
     * @param objectId The source port id.
     * @param sessionId The session token id.
     * @return A tree representation of all physical paths as a hash map.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getPhysicalTree/{objectClassName}/{objectId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<BusinessObjectLight, List<BusinessObjectLight>> getPhysicalTree(
            @PathVariable(RestConstants.OBJECT_CLASS_NAME) String objectClassName,
            @PathVariable(RestConstants.OBJECT_ID) String objectId,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getPhysicalTree", "127.0.0.1", sessionId);
            return pcs.getPhysicalTree(objectClassName, objectId);
        } catch (IllegalStateException | InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, PhysicalConnectionRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, PhysicalConnectionRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    // </editor-fold>
}