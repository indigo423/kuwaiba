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
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadataLight;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.commercial.softman.SoftwareManagerService;
import org.neotropic.kuwaiba.modules.core.logging.LoggerType;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;
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
 * Software Manager Rest Controller.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@RestController
@RequestMapping(SoftwareRestController.PATH)
public class SoftwareRestController implements SoftwareRestOpenApi {
    /**
     * Reference to the Software Manager Service.
     */
    @Autowired
    private SoftwareManagerService sms;
    
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
    public static final String PATH = "/v2.1.1/software-manager/"; //NOI18N
    
    // <editor-fold desc="software-manager" defaultstate="collapsed">
    /**
     * Creates a license pool.
     * @param poolName The pool name. Must be subclass of GenericSoftwareAsset.
     * @param poolDescription The pool description.
     * @param poolClassName The pool class name. What kind of objects can this pool contain?
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     * @return The id of the newly created license pool.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "createLicensePool/{poolName}/{poolDescription}/{poolClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createLicensePool(
            @PathVariable(RestConstants.POOL_NAME) String poolName,
            @PathVariable(RestConstants.POOL_DESCRIPTION) String poolDescription,
            @PathVariable(RestConstants.POOL_CLASS_NAME) String poolClassName,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("createLicensePool", "127.0.0.1", sessionId);
            return sms.createLicensePool(poolName, poolDescription, poolClassName, userName);
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, SoftwareRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, SoftwareRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Creates a license.
     * @param poolId The parent pool id.
     * @param licenseClassName The class name of the license to be created (temporarily).
     * @param licenseName The license name.
     * @param licenseProduct The license product.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     * @return The id of the newly created license.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "createLicense/{poolId}/{licenseClassName}/{licenseName}/{licenseProduct}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createLicense(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.LICENSE_CLASS_NAME) String licenseClassName,
            @PathVariable(RestConstants.LICENSE_NAME) String licenseName,
            @PathVariable(RestConstants.LICENSE_PRODUCT) String licenseProduct,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("createLicense", "127.0.0.1", sessionId);
            return sms.createLicense(poolId, licenseClassName, licenseName, licenseProduct, userName);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, SoftwareRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, SoftwareRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Deletes a license pool.
     * @param poolId The pool id.
     * @param poolClassName The pool class name.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.DELETE,
            value = "deleteLicensePool/{poolId}/{poolClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteLicensePool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.POOL_CLASS_NAME) String poolClassName,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("deleteLicensePool", "127.0.0.1", sessionId);
            sms.deleteLicensePool(poolId, poolClassName, userName);
        } catch (InvalidArgumentException | OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, SoftwareRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, SoftwareRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Deletes a license and delete its association with the related inventory objects. 
     * These objects will remain untouched.
     * @param licenseClassName The license class name.
     * @param licenseId The license id.
     * @param userName The user name of the session.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.DELETE,
            value = "deleteLicense/{licenseClassName}/{licenseId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteLicense(
            @PathVariable(RestConstants.LICENSE_CLASS_NAME) String licenseClassName,
            @PathVariable(RestConstants.LICENSE_ID) String licenseId,
            @PathVariable(RestConstants.USER_NAME) String userName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("deleteLicense", "127.0.0.1", sessionId);
            sms.deleteLicense(licenseClassName, licenseId, userName);
        } catch (InvalidArgumentException | OperationNotPermittedException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException
                | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, SoftwareRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, SoftwareRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Retrieves the license pools list.
     * @param sessionId The session token id.
     * @return The available license pools.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getLicensePools/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<InventoryObjectPool> getLicensePools(@PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getLicensePools", "127.0.0.1", sessionId);
            return sms.getLicensePools();
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, SoftwareRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, SoftwareRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Gets the license pool properties.
     * @param poolId The pool id.
     * @param poolClassName The pool class name.
     * @param sessionId The session token id.
     * @return The pool properties.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getLicensePool/{poolId}/{poolClassName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public InventoryObjectPool getLicensePool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.POOL_CLASS_NAME) String poolClassName,
            @PathVariable(RestConstants.SESSION_ID) String sessionId) 
    {
        try {
            aem.validateCall("getLicensePool", "127.0.0.1", sessionId);
            return sms.getLicensePool(poolId, poolClassName);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, SoftwareRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, SoftwareRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Retrieves the licenses list.
     * @param sessionId The session token id.
     * @return The available licenses.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getAllLicenses/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getAllLicenses(@PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getAllLicenses", "127.0.0.1", sessionId);
            return sms.getAllLicenses();
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, SoftwareRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, SoftwareRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /** 
     * Gets the licenses inside a license pool.
     * @param poolId The pool id.
     * @param limit The results limit per page. Use -1 to retrieve all.
     * @param sessionId The session token id.
     * @return The licenses list.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getLicensesInPool/{poolId}/{limit}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getLicensesInPool(
            @PathVariable(RestConstants.POOL_ID) String poolId,
            @PathVariable(RestConstants.LIMIT) int limit,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getLicensesInPool", "127.0.0.1", sessionId);
            return sms.getLicensesInPool(poolId, limit);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (ApplicationObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, SoftwareRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, SoftwareRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Get license properties.
     * @param licenseClassName The license class name. Must be subclass of GenericSoftwareAsset.
     * @param licenseId The license id.
     * @param sessionId The session token id.
     * @return The license properties.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getLicense/{licenseClassName}/{licenseId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public BusinessObject getLicense(
            @PathVariable(RestConstants.LICENSE_CLASS_NAME) String licenseClassName, 
            @PathVariable(RestConstants.LICENSE_ID) String licenseId,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getLicense", "127.0.0.1", sessionId);
            return sms.getLicense(licenseClassName, licenseId);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, SoftwareRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, SoftwareRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Gets all available license type, which are basically the non-abstract subclasses of 
     * <code>GenericSoftwareAsset</code>.
     * @param sessionId The session token id.
     * @return The list of available license types.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getAllLicenseTypes/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ClassMetadataLight> getAllLicenseTypes(@PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getAllLicenseTypes", "127.0.0.1", sessionId);
            return sms.getAllLicenseTypes();
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, SoftwareRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, SoftwareRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Gets all available products.
     * @param sessionId The session token id.
     * @return The list of available products.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "getAllProducts/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getAllProducts(@PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("getAllProducts", "127.0.0.1", sessionId);
            return sms.getAllProducts();
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, SoftwareRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, SoftwareRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Relates an object or a set of objects to an existing software or hardware license.
     * @param licenseClassName The class name of the license.
     * @param licenseId The id of the license.
     * @param objectClassName The class name of the object.
     * @param objectId The id of the object.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "relateObjectToLicense/{licenseClassName}/{licenseId}/{objectClassName}/{objectId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void relateObjectToLicense(
            @PathVariable(RestConstants.LICENSE_CLASS_NAME) String licenseClassName,
            @PathVariable(RestConstants.LICENSE_ID) String licenseId,
            @PathVariable(RestConstants.OBJECT_CLASS_NAME) String objectClassName,
            @PathVariable(RestConstants.OBJECT_ID) String objectId,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("relateObjectToLicense", "127.0.0.1", sessionId);
            sms.relateObjectToLicense(licenseClassName, licenseId, objectClassName, objectId);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, SoftwareRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, SoftwareRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Releases an object from another object whose relationship is "licenseHas".
     * @param sourceObjectClassName The source object class name.
     * @param sourceObjectId The source object id.
     * @param targetObjectId The target object id. The object we want to be released from.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "releaseRelationship/{sourceObjectClassName}/{sourceObjectId}/{targetObjectId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void releaseRelationship(
            @PathVariable(RestConstants.SOURCE_OBJECT_CLASS_NAME) String sourceObjectClassName,
            @PathVariable(RestConstants.SOURCE_OBJECT_ID) String sourceObjectId,
            @PathVariable(RestConstants.TARGET_OBJECT_ID) String targetObjectId,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("releaseRelationship", "127.0.0.1", sessionId);
            sms.releaseRelationship(sourceObjectClassName, sourceObjectId, targetObjectId);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, SoftwareRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, SoftwareRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    
    /**
     * Releases all the relationships with the given names associated to the license provided.
     * @param licenseClassName The license class name.
     * @param licenseId The license id.
     * @param sessionId The session token id.
     */
    @RequestMapping(method = RequestMethod.PUT,
            value = "releaseLicense/{licenseClassName}/{licenseId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void releaseLicense(
            @PathVariable(RestConstants.LICENSE_CLASS_NAME) String licenseClassName,
            @PathVariable(RestConstants.LICENSE_ID) String licenseId,
            @PathVariable(RestConstants.SESSION_ID) String sessionId)
    {
        try {
            aem.validateCall("releaseLicense", "127.0.0.1", sessionId);
            sms.releaseLicense(licenseClassName, licenseId);
        } catch (InvalidArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (NotAuthorizedException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception ex) {
            log.writeLogMessage(LoggerType.ERROR, SoftwareRestController.class, ex.getMessage());
            log.writeLogMessage(LoggerType.DEBUG, SoftwareRestController.class, "", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ts.getTranslatedString("api.rest.general.error.unexpected-error"));
        }
    }
    // </editor-fold>
}