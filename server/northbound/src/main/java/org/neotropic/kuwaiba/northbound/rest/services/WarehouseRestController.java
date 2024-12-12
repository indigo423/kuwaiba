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

import com.neotropic.kuwaiba.modules.commercial.whman.persistence.WarehousesService;
import java.util.HashMap;
import java.util.List;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
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
 * Warehouses Manager Rest Controller.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@RestController
@RequestMapping(WarehouseRestController.PATH)
public class WarehouseRestController implements WarehouseRestOpenApi {
    /**
     * Reference to the Warehouse Manager Service.
     */
    @Autowired 
    private WarehousesService ws;
    
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
    public static final String PATH = "/v2.1.1/warehouse-manager/"; //NOI18N
    
    // <editor-fold desc="warehouse-manager" defaultstate="collapsed">
    /**
     * Creates a warehouse inside a pool.
     * @param poolId Parent pool id.
     * @param poolClassName Class this warehouse is going to be instance of.
     * @param attributes The list of attributes to be set initially. The values are serialized objects.
     * @param templateId The id of the template to be used to create this object. 
     * This id was probably retrieved by {@link ApplicationEntityManager.getTemplatesForClass(String)} before. 
     * Use "null" as string or empty string to not use a template.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     * @return The id of the newly created warehouse.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "createWarehouse/{poolId}/{poolClassName}/{templateId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createWarehouse(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.POOL_CLASS_NAME) String poolClassName,
            @RequestBody(required = false) HashMap<String, String> attributes,
            @PathVariable(RestConstants.TEMPLATE_ID) String templateId,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("createWarehouse", "127.0.0.1", sessionId);
            return ws.createWarehouse(
                    poolId,
                    poolClassName,
                    attributes != null ? attributes : new HashMap<String, String>(),
                    templateId.equals("null") ? null : templateId,
                    userName
            );
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, WarehouseRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, WarehouseRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Creates a pool inside a warehouse.
     * @param warehouseClassName Class name of the parent warehouse.
     * @param warehouseId Id of the parent object.
     * @param poolName Pool name.
     * @param poolDescription Pool description.
     * @param instancesOfClassName What kind of objects can this pool contain?
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     * @return The id of the new pool.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "createPoolInWarehouse/{warehouseClassName}/{warehouseId}/{poolName}/{poolDescription}/"
                    + "{instancesOfClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createPoolInWarehouse(
            @PathVariable(RestConstants.WAREHOUSE_CLASS_NAME) String warehouseClassName,
            @PathVariable(RestConstants.WAREHOUSE_ID) String warehouseId,
            @PathVariable(RestConstants.POOL_NAME) String poolName,
            @PathVariable(RestConstants.POOL_DESCRIPTION) String poolDescription,
            @PathVariable(RestConstants.INSTANCES_OF_CLASS_NAME) String instancesOfClassName,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("createPoolInWarehouse", "127.0.0.1", sessionId);
            return ws.createPoolInWarehouse(warehouseClassName, warehouseId, 
                    poolName, poolDescription,instancesOfClassName, userName);
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, WarehouseRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, WarehouseRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
     /**
     * Creates a spare part inside a pool.
     * @param poolId Spare pool id.
     * @param className Class this spare part is going to be instance of.
     * @param attributes The list of attributes to be set initially. The values are serialized objects.
     * @param templateId The id of the template to be used to create this object.
     * This id was probably retrieved by {@link ApplicationEntityManager.getTemplatesForClass(String)} before. 
     * Use "null" as string or empty string to not use a template.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     * @return The id of the newly created spare part.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "createSparePart/{poolId}/{className}/{templateId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createSparePart(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.CLASS_NAME) String className,
            @RequestBody(required = false) HashMap<String, String> attributes,
            @PathVariable(RestConstants.TEMPLATE_ID) String templateId,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("createSparePart", "127.0.0.1", sessionId);
            return ws.createSparePart(
                    poolId,
                    className,
                    attributes != null ? attributes : new HashMap<String, String>(),
                    templateId.equals("null") ? null : templateId,
                    userName
            );
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, WarehouseRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, WarehouseRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Deletes a warehouse and delete its association with the related inventory objects. 
     * These objects will remain untouched.
     * @param warehouseClassName The class name of the warehouse.
     * @param warehouseId The id of the warehouse.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.DELETE,
            value = "deleteWarehouse/{warehouseClassName}/{warehouseId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteWarehouse(
            @PathVariable(RestConstants.WAREHOUSE_CLASS_NAME) String warehouseClassName,
            @PathVariable(RestConstants.WAREHOUSE_ID) String warehouseId,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId) 
    {
        try {
            aem.validateCall("deleteWarehouse", "127.0.0.1", sessionId);
            ws.deleteWarehouse(warehouseClassName, warehouseId, userName);
        } catch (InvalidArgumentException | OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, WarehouseRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, WarehouseRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Deletes a spare pool.
     * @param poolId The spare pool id.
     * @param poolClassName The spare pool class name.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.DELETE,
            value = "deleteSparePool/{poolId}/{poolClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteSparePool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.POOL_CLASS_NAME) String poolClassName,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("deleteSparePool", "127.0.0.1", sessionId);
            ws.deleteSparePool(poolId, poolClassName, userName);
        } catch (OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, WarehouseRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, WarehouseRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Gets the Warehouse Module Root Pools.
     * @param sessionId The session token id.
     * @return A list of root pools.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getWarehouseRootPools/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<InventoryObjectPool> getWarehouseRootPools(@PathVariable(RestConstants.SESSION_ID) String sessionId) 
    {
        try {
            aem.validateCall("getWarehouseRootPools", "127.0.0.1", sessionId);
            return ws.getWarehouseRootPools();
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, WarehouseRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, WarehouseRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Get the warehouses of a pool.
     * @param poolId Root pool id.
     * @param limit Result limit. -1 To return all.
     * @param sessionId The session token id.
     * @return List of warehouses.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getWarehousesInPool/{poolId}/{limit}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getWarehousesInPool(
            @PathVariable(RestConstants.POOL_ID) String poolId, 
            @PathVariable(RestConstants.LIMIT) int limit,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getWarehousesInPool", "127.0.0.1", sessionId);
            return ws.getWarehousesInPool(poolId, limit);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, WarehouseRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, WarehouseRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Get the pools of a warehouses.
     * @param objectClassName Warehouse class name.
     * @param objectId Warehouse id.
     * @param sessionId The session token id.
     * @return List of spare pools.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getPoolsInWarehouse/{objectClassName}/{objectId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<InventoryObjectPool> getPoolsInWarehouse(
            @PathVariable(RestConstants.OBJECT_CLASS_NAME) String objectClassName,
            @PathVariable(RestConstants.OBJECT_ID) String objectId,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getPoolsInWarehouse", "127.0.0.1", sessionId);
            return ws.getPoolsInWarehouse(objectClassName, objectId);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, WarehouseRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, WarehouseRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Get the objects of a pool.
     * @param poolId Root pool id.
     * @param limit Result limit. -1 To return all.
     * @param sessionId The session token id.
     * @return List of objects.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getObjectsInSparePool/{poolId}/{limit}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getObjectsInSparePool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.LIMIT) int limit,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getObjectsInSparePool", "127.0.0.1", sessionId);
            return ws.getObjectsInSparePool(poolId, limit);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, WarehouseRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, WarehouseRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Creates a copy of an object in a warehouse. And optionally its children objects.
     * @param poolId The spare pool id.
     * @param objectClassName The object class name.
     * @param objectId The object id.
     * @param recursive If this operation should also copy the children objects recursively.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "copyObjectToWarehouse/{poolId}/{objectClassName}/{objectId}/{recursive}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void copyObjectToWarehouse(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.OBJECT_CLASS_NAME) String objectClassName,
            @PathVariable(RestConstants.OBJECT_ID) String objectId,
            @PathVariable(RestConstants.RECURSIVE) boolean recursive,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("copyObjectToWarehouse", "127.0.0.1", sessionId);
            ws.copyObjectToWarehouse(poolId, objectClassName, objectId, recursive, userName);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException 
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, WarehouseRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, WarehouseRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Moves an object from a warehouse to another warehouse.
     * @param poolId The spare pool id.
     * @param objectClassName The object class name.
     * @param objectId The object id.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "moveObjectToWarehouse/{poolId}/{objectClassName}/{objectId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void moveObjectToWarehouse(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.OBJECT_CLASS_NAME) String objectClassName,
            @PathVariable(RestConstants.OBJECT_ID) String objectId,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("moveObjectToWarehouse", "127.0.0.1", sessionId);
            ws.moveObjectToWarehouse(poolId, objectClassName, objectId, userName);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException 
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, WarehouseRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, WarehouseRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    // </editor-fold>
}