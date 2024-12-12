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
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.northbound.rest.RestConstants;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Swagger documentation for warehouses manager.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@RequestMapping(WarehouseRestOpenApi.PATH)
public interface WarehouseRestOpenApi {
    // <editor-fold desc="warehouse-manager" defaultstate="collapsed">
    /**
     * Path that includes the Kuwaiba version and module
     */
    public static final String PATH = "/v2.1.1/warehouse-manager/"; //NOI18N
    
    @Operation(summary = "Creates a warehouse inside a pool.", description = "The id of the newly created warehouse.", 
            tags = {"warehouse-manager"})
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
            value = "createWarehouse/{poolId}/{poolClassName}/{templateId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createWarehouse(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Parent pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Class this warehouse is going to be instance of.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_CLASS_NAME, required = true) String poolClassName,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The list of attributes to be set initially. The values are serialized objects.",
                    required = false, content = @Content(schema = @Schema(implementation = HashMap.class)))
            @Valid @RequestBody HashMap<String, String> attributes,
            @Parameter(in = ParameterIn.PATH, description = "The id of the template to be used to create this object. "
                    + "This id was probably retrieved by {@link ApplicationEntityManager.getTemplatesForClass(String)} before. "
                    + "Use \"null\" as string or empty string to not use a template.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.TEMPLATE_ID, required = true) String templateId,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Creates a pool inside a warehouse.", description = "The id of the new pool.", 
            tags = {"warehouse-manager"})
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
            value = "createPoolInWarehouse/{warehouseClassName}/{warehouseId}/{poolName}/{poolDescription}/"
                    + "{instancesOfClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createPoolInWarehouse(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Class name of the parent warehouse.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.WAREHOUSE_CLASS_NAME, required = true) String warehouseClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Id of the parent object.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.WAREHOUSE_ID, required = true) String warehouseId,
            @Parameter(in = ParameterIn.PATH, description = "Pool name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_NAME, required = true) String poolName,
            @Parameter(in = ParameterIn.PATH, description = "Pool description.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_DESCRIPTION, required = true) String poolDescription,
            @Parameter(in = ParameterIn.PATH, description = "What kind of objects can this pool contain?",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.INSTANCES_OF_CLASS_NAME, required = true) String instancesOfClassName,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Creates a spare part inside a pool.", description = "The id of the newly created spare part.", 
            tags = {"warehouse-manager"})
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
            value = "createSparePart/{poolId}/{className}/{templateId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createSparePart(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Spare pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Class this spare part is going to be instance of.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.CLASS_NAME, required = true) String className,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The list of attributes to be set initially. The values are serialized objects.",
                    required = false, content = @Content(schema = @Schema(implementation = HashMap.class)))
            @Valid @RequestBody HashMap<String, String> attributes,
            @Parameter(in = ParameterIn.PATH, description = "The id of the template to be used to create this object. "
                    + "This id was probably retrieved by {@link ApplicationEntityManager.getTemplatesForClass(String)} before. "
                    + "Use \"null\" as string or empty string to not use a template.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.TEMPLATE_ID, required = true) String templateId,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Deletes a warehouse and delete its association with the related inventory objects. "
            + "These objects will remain untouched.", tags = {"warehouse-manager"})
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
            value = "deleteWarehouse/{warehouseClassName}/{warehouseId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteWarehouse(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The class name of the warehouse.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.WAREHOUSE_CLASS_NAME, required = true) String warehouseClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The id of the warehouse.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.WAREHOUSE_ID, required = true) String warehouseId,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Deletes a spare pool.", tags = {"warehouse-manager"})
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
            value = "deleteSparePool/{poolId}/{poolClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteSparePool(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The spare pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The spare pool class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_CLASS_NAME, required = true) String poolClassName,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Gets the Warehouse Module Root Pools.", description = "A list of root pools.", 
            tags = {"warehouse-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = InventoryObjectPool.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.GET,
            value = "getWarehouseRootPools/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<InventoryObjectPool> getWarehouseRootPools(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Get the warehouses of a pool.", description = "List of warehouses.", 
            tags = {"warehouse-manager"})
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
            value = "getWarehousesInPool/{poolId}/{limit}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getWarehousesInPool(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Root pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Result limit. -1 To return all.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LIMIT, required = true) int limit,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Get the pools of a warehouses.", description = "List of spare pools.", 
            tags = {"warehouse-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = InventoryObjectPool.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.GET,
            value = "getPoolsInWarehouse/{objectClassName}/{objectId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<InventoryObjectPool> getPoolsInWarehouse(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Warehouse class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECT_CLASS_NAME, required = true) String objectClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Warehouse id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECT_ID, required = true) String objectId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Get the objects of a pool.", description = "List of objects.", 
            tags = {"warehouse-manager"})
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
            value = "getObjectsInSparePool/{poolId}/{limit}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getObjectsInSparePool(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Root pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Result limit. -1 To return all.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LIMIT, required = true) int limit,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Creates a copy of an object in a warehouse. And optionally its children objects.",
            tags = {"warehouse-manager"})
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
            value = "copyObjectToWarehouse/{poolId}/{objectClassName}/{objectId}/{recursive}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void copyObjectToWarehouse(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The spare pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The object class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECT_CLASS_NAME, required = true) String objectClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The object id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECT_ID, required = true) String objectId,
            @NotNull @Parameter(in = ParameterIn.PATH, 
                    description = "If this operation should also copy the children objects recursively.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.RECURSIVE, required = true) boolean recursive,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Moves an object from a warehouse to another warehouse.", tags = {"warehouse-manager"})
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
            value = "moveObjectToWarehouse/{poolId}/{objectClassName}/{objectId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void moveObjectToWarehouse(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The spare pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The object class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECT_CLASS_NAME, required = true) String objectClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The object id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECT_ID, required = true) String objectId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    // </editor-fold>
}