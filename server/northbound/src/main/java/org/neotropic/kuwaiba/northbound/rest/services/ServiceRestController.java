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
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObject;
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
import org.neotropic.kuwaiba.modules.optional.serviceman.ServiceManagerService;
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
 * Service Manager Rest Controller.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@RestController
@RequestMapping(ServiceRestController.PATH)
public class ServiceRestController implements ServiceRestOpenApi {
    /**
     * Reference to the Service Manager Service.
     */
    @Autowired 
    private ServiceManagerService sms;
    
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
    public static final String PATH = "/v2.1.1/service-manager/"; //NOI18N
    
    // <editor-fold desc="service-manager" defaultstate="collapsed">
    /**
     * Creates a customer pool.
     * @param poolName The pool name.
     * @param poolDescription The pool description.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     * @return The id newly created customer pool.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "createCustomerPool/{poolName}/{poolDescription}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createCustomerPool(
            @PathVariable(RestConstants.POOL_NAME) String poolName,
            @PathVariable(RestConstants.POOL_DESCRIPTION) String poolDescription,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("createCustomerPool", "127.0.0.1", sessionId);
            return sms.createCustomerPool(poolName, poolDescription, userName);
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException| MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Creates a customer.
     * @param poolId Parent pool id.
     * @param customerClassName This customer is going to be instance of.
     * @param attributes The list of attributes to be set initially. The values are serialized objects.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     * @return The id newly created customer.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "createCustomer/{poolId}/{customerClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createCustomer(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.CUSTOMER_CLASS_NAME) String customerClassName,
            @RequestBody(required = false) HashMap<String, String> attributes,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("createCustomer", "127.0.0.1", sessionId);
            return sms.createCustomer(
                    poolId,
                    customerClassName,
                    attributes != null ? attributes : new HashMap<String, String>(),
                    userName
            );
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Creates a service pool.
     * @param customerClassName Customer class name.
     * @param customerId Customer id.
     * @param poolName The service pool name.
     * @param poolDescription The service pool description.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     * @return The id newly created service pool.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "createServicePool/{customerClassName}/{customerId}/{poolName}/{poolDescription}"
                    + "/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createServicePool(
            @PathVariable(RestConstants.CUSTOMER_CLASS_NAME) String customerClassName, 
            @PathVariable(RestConstants.CUSTOMER_ID) String customerId, 
            @PathVariable(RestConstants.POOL_NAME) String poolName,
            @PathVariable(RestConstants.POOL_DESCRIPTION) String poolDescription, 
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("createServicePool", "127.0.0.1", sessionId);
            return sms.createServicePool(customerClassName, customerId, poolName, poolDescription, userName);
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException| BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Creates a service.
     * @param poolId Parent pool id.
     * @param serviceClassName This service is going to be instance of.
     * @param attributes The list of attributes to be set initially. The values are serialized objects.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     * @return The id newly created service.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "createService/{poolId}/{serviceClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createService(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.SERVICE_CLASS_NAME) String serviceClassName,
            @RequestBody(required = false) HashMap<String, String> attributes,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("createService", "127.0.0.1", sessionId);
            return sms.createService(
                    poolId,
                    serviceClassName,
                    attributes != null ? attributes : new HashMap<String, String>(),
                    userName
            );
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Updates a customer pool.
     * @param poolId The pool id.
     * @param poolClassName The pool class name.
     * @param poolName The pool name.
     * @param poolDescription The pool description.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "updateCustomerPool/{poolId}/{poolClassName}/{poolName}/{poolDescription}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateCustomerPool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.POOL_CLASS_NAME) String poolClassName,
            @PathVariable(RestConstants.POOL_NAME) String poolName,
            @PathVariable(RestConstants.POOL_DESCRIPTION) String poolDescription,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("updateCustomerPool", "127.0.0.1", sessionId);
            sms.updateCustomerPool(poolId, poolClassName, poolName, poolDescription, userName);
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Updates a customer.
     * @param customerClassName Customer class name.
     * @param customerId Customer id.
     * @param attributes The attributes to be updated (the key is the attribute name, 
     * the value is and array with the value -or values in case of MANY TO MANY list type attributes-).
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "updateCustomer/{customerClassName}/{customerId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateCustomer(
            @PathVariable(RestConstants.CUSTOMER_CLASS_NAME) String customerClassName,
            @PathVariable(RestConstants.CUSTOMER_ID) String customerId,
            @RequestBody(required = false) HashMap<String, String> attributes,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("updateCustomer", "127.0.0.1", sessionId);
            sms.updateCustomer(
                    customerClassName,
                    customerId,
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
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Updates a service pool.
     * @param poolId The pool id.
     * @param poolClassName The pool class name.
     * @param poolName The pool name.
     * @param poolDescription The pool description.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "updateServicePool/{poolId}/{poolClassName}/{poolName}/{poolDescription}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateServicePool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.POOL_CLASS_NAME) String poolClassName,
            @PathVariable(RestConstants.POOL_NAME) String poolName,
            @PathVariable(RestConstants.POOL_DESCRIPTION) String poolDescription,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("updateServicePool", "127.0.0.1", sessionId);
            sms.updateServicePool(poolId, poolClassName, poolName, poolDescription, userName);
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Updates a service.
     * @param serviceClassName Service class name.
     * @param serviceId Service id.
     * @param attributes The attributes to be updated (the key is the attribute name, 
     * the value is and array with the value -or values in case of MANY TO MANY list type attributes-).
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "updateService/{serviceClassName}/{serviceId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateService(
            @PathVariable(RestConstants.SERVICE_CLASS_NAME) String serviceClassName,
            @PathVariable(RestConstants.SERVICE_ID) String serviceId,
            @RequestBody(required = false) HashMap<String, String> attributes,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("updateService", "127.0.0.1", sessionId);
            sms.updateService(
                    serviceClassName,
                    serviceId,
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
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Deletes a customer pool.
     * @param poolId The pool id.
     * @param poolClassName The pool class name.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.DELETE,
            value = "deleteCustomerPool/{poolId}/{poolClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteCustomerPool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.POOL_CLASS_NAME) String poolClassName,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("deleteCustomerPool", "127.0.0.1", sessionId);
            sms.deleteCustomerPool(poolId, poolClassName, userName);
        } catch (OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Deletes a customer.
     * @param customerClassName Customer class name.
     * @param customerId Customer id.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.DELETE,
            value = "deleteCostumer/{customerClassName}/{customerId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteCostumer(
            @PathVariable(RestConstants.CUSTOMER_CLASS_NAME) String customerClassName,
            @PathVariable(RestConstants.CUSTOMER_ID) String customerId,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("deleteCostumer", "127.0.0.1", sessionId);
            sms.deleteCostumer(customerClassName, customerId, userName);
        } catch (InvalidArgumentException | OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Deletes a service pool.
     * @param poolId The pool id.
     * @param poolClassName The pool class name.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.DELETE,
            value = "deleteServicePool/{poolId}/{poolClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteServicePool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.POOL_CLASS_NAME) String poolClassName,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("deleteServicePool", "127.0.0.1", sessionId);
            sms.deleteServicePool(poolId, poolClassName, userName);
        } catch (OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Deletes a service.
     * @param serviceClassName Service class name.
     * @param serviceId Service id.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.DELETE,
            value = "deleteService/{serviceClassName}/{serviceId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteService(
            @PathVariable(RestConstants.SERVICE_CLASS_NAME) String serviceClassName,
            @PathVariable(RestConstants.SERVICE_ID) String serviceId,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("deleteService", "127.0.0.1", sessionId);
            sms.deleteService(serviceClassName, serviceId, userName);
        } catch (InvalidArgumentException | OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Retrieves the list of customer pools.
     * @param sessionId The session token id.
     * @return A set of customer pools.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getCustomerPools/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<InventoryObjectPool> getCustomerPools(@PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getCustomerPools", "127.0.0.1", sessionId);
            return sms.getCustomerPools();
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Gets a customer pool.
     * @param poolId The pool id.
     * @param poolClassName The pool class name.
     * @param sessionId The session token id.
     * @return The pool as a Pool object.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getCustomerPool/{poolId}/{poolClassName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public InventoryObjectPool getCustomerPool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.POOL_CLASS_NAME) String poolClassName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getCustomerPool", "127.0.0.1", sessionId);
            return sms.getCustomerPool(poolId, poolClassName);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Gets all the customer.
     * @param filters Map of filters key: attribute name, value: attribute value.
     * @param page Page or number of elements to skip.
     * @param limit Max count of child per page.
     * @param sessionId The session token id.
     * @return The customers list.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "getAllCustomers/{page}/{limit}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getAllCustomers(
            @RequestBody(required = false) HashMap<String, String> filters,
            @PathVariable(RestConstants.PAGE) long page,
            @PathVariable(RestConstants.LIMIT) long limit,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getAllCustomers", "127.0.0.1", sessionId);
            return sms.getAllCustomers(filters, page, limit);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Retrieves the list of customers from a pool.
     * @param poolId Parent pool id.
     * @param customerClassName A given className to retrieve a set of objects of that className form the pool 
     * used when the pool is a Generic class and could have objects of different class. Use "null" to get all.
     * @param page The number of values of the result to skip or the page 0 to avoid.
     * @param limit The results limit. Per page 0 to avoid the limit.
     * @param sessionId The session token id.
     * @return The list of customers inside the pool.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getCustomersInPool/{poolId}/{customerClassName}/{page}/{limit}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getCustomersInPool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.CUSTOMER_CLASS_NAME) String customerClassName,
            @PathVariable(RestConstants.PAGE) int page,
            @PathVariable(RestConstants.LIMIT) int limit,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getCustomersInPool", "127.0.0.1", sessionId);
            return sms.getCustomersInPool(
                    poolId,
                    customerClassName.equals("null" ) ? null : customerClassName,
                    page,
                    limit
            );
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Gets the detailed information about a customer.
     * @param customerClassName Customer class name.
     * @param customerId Customer id.
     * @param sessionId The session token id.
     * @return A detailed representation of the requested customer.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getCustomer/{customerClassName}/{customerId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public BusinessObject getCustomer(
            @PathVariable(RestConstants.CUSTOMER_CLASS_NAME) String customerClassName,
            @PathVariable(RestConstants.CUSTOMER_ID) String customerId,
            @PathVariable(RestConstants.SESSION_ID) String sessionId) 
    {
        try {
            aem.validateCall("getCustomer", "127.0.0.1", sessionId);
            return sms.getCustomer(customerClassName, customerId);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Retrieves the pools associated to a particular customer.
     * @param customerClassName The parent customer class name.
     * @param customerId The parent customer id.
     * @param servicePoolClassName The class name used to filter the results.
     * @param sessionId The session token id.
     * @return A set of service pools.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getServicePoolsInCostumer/{customerClassName}/{customerId}/{servicePoolClassName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<InventoryObjectPool> getServicePoolsInCostumer(
            @PathVariable(RestConstants.CUSTOMER_CLASS_NAME) String customerClassName,
            @PathVariable(RestConstants.CUSTOMER_ID) String customerId,
            @PathVariable(RestConstants.SERVICE_POOL_CLASS_NAME) String servicePoolClassName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getServicePoolsInCostumer", "127.0.0.1", sessionId);
            return sms.getServicePoolsInCostumer(
                    customerClassName,
                    customerId,
                    servicePoolClassName
            );
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Gets a service pool.
     * @param poolId The pool id.
     * @param poolClassName The pool class name.
     * @param sessionId The session token id.
     * @return The pool as a Pool object.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getServicePool/{poolId}/{poolClassName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public InventoryObjectPool getServicePool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.POOL_CLASS_NAME) String poolClassName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId) 
    {
        try {
            aem.validateCall("getServicePool", "127.0.0.1", sessionId);
            return sms.getServicePool(poolId, poolClassName);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Gets all the services.
     * @param filters Map of filters key: attribute name, value: attribute value.
     * @param page Page or number of elements to skip.
     * @param limit Max count of child per page.
     * @param sessionId The session token id.
     * @return The services list.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "getAllServices/{page}/{limit}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getAllServices(
            @RequestBody(required = false) HashMap<String, String> filters,
            @PathVariable(RestConstants.PAGE) long page,
            @PathVariable(RestConstants.LIMIT) long limit,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getAllServices", "127.0.0.1", sessionId);
            return sms.getAllServices(filters, page, limit);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Retrieves the list of services from a pool.
     * @param poolId Parent pool id.
     * @param serviceClassName A given className to retrieve a set of objects of that className form the pool 
     * used when the pool is a Generic class and could have objects of different class. Use "null" to get all.
     * @param page The number of values of the result to skip or the page 0 to avoid.
     * @param limit The results limit. Per page 0 to avoid the limit.
     * @param sessionId The session token id.
     * @return The list of services inside the pool.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getServicesInPool/{poolId}/{serviceClassName}/{page}/{limit}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getServicesInPool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.SERVICE_CLASS_NAME) String serviceClassName,
            @PathVariable(RestConstants.PAGE) int page,
            @PathVariable(RestConstants.LIMIT) int limit,
            @PathVariable(RestConstants.SESSION_ID) String sessionId) 
    {
        try {
            aem.validateCall("getServicesInPool", "127.0.0.1", sessionId);
            return sms.getServicesInPool(
                    poolId,
                    serviceClassName.equals("null" ) ? null : serviceClassName,
                    page,
                    limit
            );
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Gets the detailed information about a service.
     * @param serviceClassName Service class name.
     * @param serviceId Service id.
     * @param sessionId The session token id.
     * @return A detailed representation of the requested service.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getService/{serviceClassName}/{serviceId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public BusinessObject getService(
            @PathVariable(RestConstants.SERVICE_CLASS_NAME) String serviceClassName,
            @PathVariable(RestConstants.SERVICE_ID) String serviceId,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getService", "127.0.0.1", sessionId);
            return sms.getService(serviceClassName, serviceId);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Retrieves the objects related to service.
     * @param serviceClassName The service class name.
     * @param serviceId The service id.
     * @param sessionId The session token id.
     * @return A list of objects related to service.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getObjectsRelatedToService/{serviceClassName}/{serviceId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getObjectsRelatedToService(
            @PathVariable(RestConstants.SERVICE_CLASS_NAME) String serviceClassName,
            @PathVariable(RestConstants.SERVICE_ID) String serviceId,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getObjectsRelatedToService", "127.0.0.1", sessionId);
            return sms.getObjectsRelatedToService(serviceClassName, serviceId);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Relates an inventory object (resource) to a service.
     * @param objectClassName The class name of the object.
     * @param objectId The id of the object.
     * @param serviceClassName The class name of the service.
     * @param serviceId The id of the service.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "relateObjectToService/{objectClassName}/{objectId}/{serviceClassName}/{serviceId}/"
                    + "{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void relateObjectToService(
            @PathVariable(RestConstants.OBJECT_CLASS_NAME) String objectClassName,
            @PathVariable(RestConstants.OBJECT_ID) String objectId,
            @PathVariable(RestConstants.SERVICE_CLASS_NAME) String serviceClassName,
            @PathVariable(RestConstants.SERVICE_ID) String serviceId,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("relateObjectToService", "127.0.0.1", sessionId);
            sms.relateObjectToService(objectClassName, objectId, serviceClassName, serviceId, userName);
        } catch (InvalidArgumentException | OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Relates inventory objects (resources) to a service.
     * @param objectsClassNames The class names of the objects.
     * @param objectsIds The ids of the objects.
     * @param serviceClassName The class name of the service.
     * @param serviceId The id of the service.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "relateObjectsToService/{objectsClassNames}/{objectsIds}/{serviceClassName}/{serviceId}/"
                    + "{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void relateObjectsToService(
            @PathVariable(RestConstants.OBJECTS_CLASS_NAMES) String[] objectsClassNames, 
            @PathVariable(RestConstants.OBJECTS_IDS) String[] objectsIds,
            @PathVariable(RestConstants.SERVICE_CLASS_NAME) String serviceClassName,
            @PathVariable(RestConstants.SERVICE_ID) String serviceId,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("relateObjectsToService", "127.0.0.1", sessionId);
            sms.relateObjectsToService(
                    objectsClassNames, objectsIds,
                    serviceClassName, serviceId,
                    userName);
        } catch (InvalidArgumentException | OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Releases an inventory object from a service.
     * @param serviceClassName The class name of the service.
     * @param serviceId The id of the service.
     * @param objectId The id of the object.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "releaseObjectFromService/{serviceClassName}/{serviceId}/{objectId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void releaseObjectFromService(
            @PathVariable(RestConstants.SERVICE_CLASS_NAME) String serviceClassName,
            @PathVariable(RestConstants.SERVICE_ID) String serviceId,
            @PathVariable(RestConstants.OBJECT_ID) String objectId,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("releaseObjectFromService", "127.0.0.1", sessionId);
            sms.releaseObjectFromService(serviceClassName, serviceId, objectId, userName);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, ServiceRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, ServiceRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    // </editor-fold>
}