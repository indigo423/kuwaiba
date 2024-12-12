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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObject;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadataLight;
import org.neotropic.kuwaiba.northbound.rest.RestConstants;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Swagger documentation for software manager.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@RequestMapping(SoftwareRestOpenApi.PATH)
public interface SoftwareRestOpenApi {
    // <editor-fold desc="software-manager" defaultstate="collapsed">
    /**
     * Path that includes the Kuwaiba version and module
     */
    public static final String PATH = "/v2.1.1/software-manager/"; //NOI18N
    
    @Operation(summary = "Creates a license pool.", description = "The id of the newly created license pool.", 
            tags = {"software-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.POST,
            value = "createLicensePool/{poolName}/{poolDescription}/{poolClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createLicensePool(
            @NotNull @Parameter(in = ParameterIn.PATH, 
                    description = "The pool name. Must be subclass of GenericSoftwareAsset.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_NAME, required = true) String poolName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool description.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_DESCRIPTION, required = true) String poolDescription,
            @Parameter(in = ParameterIn.PATH,
                    description = "The pool class name. What kind of objects can this pool contain?",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_CLASS_NAME, required = true) String poolClassName,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Creates a license.", description = "The id of the newly created license.", 
            tags = {"software-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.POST,
            value = "createLicense/{poolId}/{licenseClassName}/{licenseName}/{licenseProduct}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createLicense(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The parent pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, 
                    description = "The class name of the license to be created (temporarily).",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LICENSE_CLASS_NAME, required = true) String licenseClassName,
            @Parameter(in = ParameterIn.PATH, description = "The license name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LICENSE_NAME, required = true) String licenseName,
            @Parameter(in = ParameterIn.PATH, description = "The license product.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LICENSE_PRODUCT, required = true) String licenseProduct,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Deletes a license pool.", tags = {"software-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.DELETE,
            value = "deleteLicensePool/{poolId}/{poolClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteLicensePool(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_CLASS_NAME, required = true) String poolClassName,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Deletes a license and delete its association with the related inventory objects. "
            + "These objects will remain untouched.", tags = {"software-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.DELETE,
            value = "deleteLicense/{licenseClassName}/{licenseId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteLicense(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The license class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LICENSE_CLASS_NAME, required = true) String licenseClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The license id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LICENSE_ID, required = true) String licenseId,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Retrieves the license pools list.", description = "The available license pools.", 
            tags = {"software-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = InventoryObjectPool.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.GET,
            value = "getLicensePools/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<InventoryObjectPool> getLicensePools(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Gets the license pool properties.", description = "The pool properties.", 
            tags = {"software-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InventoryObjectPool.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.GET,
            value = "getLicensePool/{poolId}/{poolClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public InventoryObjectPool getLicensePool(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_CLASS_NAME, required = true) String poolClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Retrieves the licenses list.", description = "The available licenses.", 
            tags = {"software-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = BusinessObjectLight.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.GET,
            value = "getAllLicenses/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getAllLicenses(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Gets the licenses inside a license pool.", description = "The licenses list.", 
            tags = {"software-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = BusinessObjectLight.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.GET,
            value = "getLicensesInPool/{poolId}/{limit}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getLicensesInPool(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH,
                    description = "The results limit per page. Use -1 to retrieve all.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LIMIT, required = true) int limit,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
     @Operation(summary = "Get license properties.", description = "The license properties.", 
            tags = {"software-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BusinessObject.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.GET,
            value = "getLicense/{licenseClassName}/{licenseId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public BusinessObject getLicense(
            @NotNull @Parameter(in = ParameterIn.PATH, 
                    description = "The license class name. Must be subclass of GenericSoftwareAsset.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LICENSE_CLASS_NAME, required = true) String licenseClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The license id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LICENSE_ID, required = true) String licenseId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Gets all available license type, which are basically the non-abstract subclasses of "
            + "GenericSoftwareAsset", description = "The list of available license types.", 
            tags = {"software-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ClassMetadataLight.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.GET,
            value = "getAllLicenseTypes/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ClassMetadataLight> getAllLicenseTypes(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Gets all available products.", description = "The list of available products.", 
            tags = {"software-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = BusinessObjectLight.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.GET,
            value = "getAllProducts/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getAllProducts(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Relates an object or a set of objects to an existing software or hardware license.",
            tags = {"software-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.PUT,
            value = "relateObjectToLicense/{licenseClassName}/{licenseId}/{objectClassName}/{objectId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void relateObjectToLicense(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The class name of the license.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LICENSE_CLASS_NAME, required = true) String licenseClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The id of the license.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LICENSE_ID, required = true) String licenseId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The class name of the object.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECT_CLASS_NAME, required = true) String objectClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The id of the object.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECT_ID, required = true) String objectId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Releases an object from another object whose relationship is \"licenseHas\".",
            tags = {"software-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.PUT,
            value = "releaseRelationship/{sourceObjectClassName}/{sourceObjectId}/{targetObjectId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void releaseRelationship(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The source object class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SOURCE_OBJECT_CLASS_NAME, required = true) String sourceObjectClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The source object id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SOURCE_OBJECT_ID, required = true) String sourceObjectId,
            @NotNull @Parameter(in = ParameterIn.PATH, 
                    description = "The target object id. The object we want to be released from.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.TARGET_OBJECT_ID, required = true) String targetObjectId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Releases all the relationships with the given names associated to the license provided.",
            tags = {"software-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.PUT,
            value = "releaseLicense/{licenseClassName}/{licenseId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void releaseLicense(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The license class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LICENSE_CLASS_NAME, required = true) String licenseClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The license id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LICENSE_ID, required = true) String licenseId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    // </editor-fold>
}