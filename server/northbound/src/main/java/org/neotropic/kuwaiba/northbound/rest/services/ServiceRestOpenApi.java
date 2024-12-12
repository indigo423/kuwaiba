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
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObject;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.northbound.rest.RestConstants;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Swagger documentation for service manager.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@RequestMapping(ServiceRestOpenApi.PATH)
public interface ServiceRestOpenApi {
    // <editor-fold desc="service-manager" defaultstate="collapsed">
    /**
     * Path that includes the Kuwaiba version and module
     */
    public static final String PATH = "/v2.1.1/service-manager/"; //NOI18N
    
    @Operation(summary = "Creates a customer pool.", description = "The id newly created customer pool.", 
            tags = {"service-manager"})
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
            value = "createCustomerPool/{poolName}/{poolDescription}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createCustomerPool(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_NAME, required = true) String poolName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool description.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_DESCRIPTION, required = true) String poolDescription,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Creates a customer.", description = "The id newly created customer.", 
            tags = {"service-manager"})
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
            value = "createCustomer/{poolId}/{customerClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createCustomer(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Parent pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "This customer is going to be instance of.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.CUSTOMER_CLASS_NAME, required = true) String customerClassName,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The list of attributes to be set initially. The values are serialized objects.",
                    required = false, content = @Content(schema = @Schema(implementation = HashMap.class)))
            @Valid @RequestBody HashMap<String, String> attributes,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Creates a service pool.", description = "The id newly created service pool.", 
            tags = {"service-manager"})
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
            value = "createServicePool/{customerClassName}/{customerId}/{poolName}/{poolDescription}"
                    + "/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createServicePool(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Customer class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.CUSTOMER_CLASS_NAME, required = true) String customerClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Customer id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.CUSTOMER_ID, required = true) String customerId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The service pool name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_NAME, required = true) String poolName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The service pool description.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_DESCRIPTION, required = true) String poolDescription,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Creates a service.", description = "The id newly created service.", 
            tags = {"service-manager"})
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
            value = "createService/{poolId}/{serviceClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createService(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Parent pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "This service is going to be instance of.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SERVICE_CLASS_NAME, required = true) String serviceClassName,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The list of attributes to be set initially. The values are serialized objects.",
                    required = false, content = @Content(schema = @Schema(implementation = HashMap.class)))
            @Valid @RequestBody HashMap<String, String> attributes,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Updates a customer pool.", tags = {"service-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.PUT,
            value = "updateCustomerPool/{poolId}/{poolClassName}/{poolName}/{poolDescription}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateCustomerPool(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_CLASS_NAME, required = true) String poolClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_NAME, required = true) String poolName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool description.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_DESCRIPTION, required = true) String poolDescription,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Updates a customer.", tags = {"service-manager"})
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
            value = "updateCustomer/{customerClassName}/{customerId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateCustomer(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Customer class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.CUSTOMER_CLASS_NAME, required = true) String customerClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Customer id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.CUSTOMER_ID, required = true) String customerId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The attributes to be updated (the key is the attribute name, the value is and array "
                            + "with the value -or values in case of MANY TO MANY list type attributes-).",
                    required = false, content = @Content(schema = @Schema(implementation = HashMap.class)))
            @Valid @RequestBody HashMap<String, String> attributes,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Updates a service pool.", tags = {"service-manager"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.PUT,
            value = "updateServicePool/{poolId}/{poolClassName}/{poolName}/{poolDescription}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateServicePool(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_CLASS_NAME, required = true) String poolClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_NAME, required = true) String poolName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool description.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_DESCRIPTION, required = true) String poolDescription,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Updates a service.", tags = {"service-manager"})
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
            value = "updateService/{serviceClassName}/{serviceId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateService(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Service class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SERVICE_CLASS_NAME, required = true) String serviceClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Service id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SERVICE_ID, required = true) String serviceId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The attributes to be updated (the key is the attribute name, the value is and array "
                            + "with the value -or values in case of MANY TO MANY list type attributes-).",
                    required = false, content = @Content(schema = @Schema(implementation = HashMap.class)))
            @Valid @RequestBody HashMap<String, String> attributes,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Deletes a customer pool.", tags = {"service-manager"})
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
            value = "deleteCustomerPool/{poolId}/{poolClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteCustomerPool(
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
    
    @Operation(summary = "Deletes a customer.", tags = {"service-manager"})
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
            value = "deleteCostumer/{customerClassName}/{customerId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteCostumer(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Customer class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.CUSTOMER_CLASS_NAME, required = true) String customerClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Customer id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.CUSTOMER_ID, required = true) String customerId,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Deletes a service pool.", tags = {"service-manager"})
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
            value = "deleteServicePool/{poolId}/{poolClassName}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteServicePool(
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
    
    @Operation(summary = "Deletes a service.", tags = {"service-manager"})
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
            value = "deleteService/{serviceClassName}/{serviceId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteService(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Service class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SERVICE_CLASS_NAME, required = true) String serviceClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Service id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SERVICE_ID, required = true) String serviceId,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Retrieves the list of customer pools.", description = "A set of customer pools.", 
            tags = {"service-manager"})
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
            value = "getCustomerPools/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<InventoryObjectPool> getCustomerPools(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Gets a customer pool.", description = "The pool as a Pool object.", 
            tags = {"service-manager"})
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
            value = "getCustomerPool/{poolId}/{poolClassName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public InventoryObjectPool getCustomerPool(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_CLASS_NAME, required = true) String poolClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Gets all the customer.", description = "The customers list.", 
            tags = {"service-manager"})
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
    @RequestMapping(method = RequestMethod.POST,
            value = "getAllCustomers/{page}/{limit}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getAllCustomers(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Map of filters key: attribute name, value: attribute value.",
                    required = false, content = @Content(schema = @Schema(implementation = HashMap.class)))
            @Valid @RequestBody HashMap<String, String> filters,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Page or number of elements to skip.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.PAGE, required = true) long page,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Max count of child per page.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LIMIT, required = true) long limit,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Retrieves the list of customers from a pool.", 
            description = "The list of customers inside the pool.", tags = {"service-manager"})
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
            value = "getCustomersInPool/{poolId}/{customerClassName}/{page}/{limit}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getCustomersInPool(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Parent pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, 
                    description = "A given className to retrieve a set of objects of that className form the pool "
                            + "used when the pool is a Generic class and could have objects of different class. "
                            + "Use \"null\" to get all.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String customerClassName,
            @NotNull @Parameter(in = ParameterIn.PATH,
                    description = "The number of values of the result to skip or the page 0 to avoid.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.PAGE, required = true) int page,
            @NotNull @Parameter(in = ParameterIn.PATH, 
                    description = "The results limit. Per page 0 to avoid the limit.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LIMIT, required = true) int limit,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Gets the detailed information about a customer.", 
            description = "A detailed representation of the requested customer.", tags = {"service-manager"})
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
            value = "getCustomer/{customerClassName}/{customerId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public BusinessObject getCustomer(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Customer class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.CUSTOMER_CLASS_NAME, required = true) String customerClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Customer id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.CUSTOMER_ID, required = true) String customerId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Retrieves the pools associated to a particular customer.", 
            description = "A set of service pools.", tags = {"service-manager"})
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
            value = "getServicePoolsInCostumer/{customerClassName}/{customerId}/{servicePoolClassName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<InventoryObjectPool> getServicePoolsInCostumer(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The parent customer class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.CUSTOMER_CLASS_NAME, required = true) String customerClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The parent customer id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.CUSTOMER_ID, required = true) String customerId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The class name used to filter the results.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SERVICE_POOL_CLASS_NAME, required = true) String servicePoolClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Gets a service pool.", description = "The pool as a Pool object.",
            tags = {"service-manager"})
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
            value = "getServicePool/{poolId}/{poolClassName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public InventoryObjectPool getServicePool(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The pool class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_CLASS_NAME, required = true) String poolClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Gets all the services.", description = "The services list.",
            tags = {"service-manager"})
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
    @RequestMapping(method = RequestMethod.POST,
            value = "getAllServices/{page}/{limit}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getAllServices(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Map of filters key: attribute name, value: attribute value.",
                    required = false, content = @Content(schema = @Schema(implementation = HashMap.class)))
            @Valid @RequestBody HashMap<String, String> filters,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Page or number of elements to skip.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.PAGE, required = true) long page,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Max count of child per page.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LIMIT, required = true) long limit,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Retrieves the list of services from a pool.", 
            description = "The list of services inside the pool.", tags = {"service-manager"})
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
            value = "getServicesInPool/{poolId}/{serviceClassName}/{page}/{limit}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getServicesInPool(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Parent pool id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.POOL_ID, required = true) String poolId,
            @NotNull @Parameter(in = ParameterIn.PATH, 
                    description = "A given className to retrieve a set of objects of that className form the pool "
                            + "used when the pool is a Generic class and could have objects of different class. "
                            + "Use \"null\" to get all.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SERVICE_CLASS_NAME, required = true) String serviceClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, 
                    description = "The number of values of the result to skip or the page 0 to avoid.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.PAGE, required = true) int page,
            @NotNull @Parameter(in = ParameterIn.PATH,
                    description = "The results limit. Per page 0 to avoid the limit.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.LIMIT, required = true) int limit,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Gets the detailed information about a service.", 
            description = "A detailed representation of the requested service.", tags = {"service-manager"})
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
            value = "getService/{serviceClassName}/{serviceId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public BusinessObject getService(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Service class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SERVICE_CLASS_NAME, required = true) String serviceClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "Service id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SERVICE_ID, required = true) String serviceId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Retrieves the objects related to service.", 
            description = "A list of objects related to service.", tags = {"service-manager"})
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
            value = "getObjectsRelatedToService/{serviceClassName}/{serviceId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getObjectsRelatedToService(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The service class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SERVICE_CLASS_NAME, required = true) String serviceClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The service id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SERVICE_ID, required = true) String serviceId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Relates an inventory object (resource) to a service.", tags = {"service-manager"})
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
            value = "relateObjectToService/{objectClassName}/{objectId}/{serviceClassName}/{serviceId}/"
                    + "{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void relateObjectToService(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The class name of the object.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECT_CLASS_NAME, required = true) String objectClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The id of the object.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECT_ID, required = true) String objectId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The class name of the service.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SERVICE_CLASS_NAME, required = true) String serviceClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The id of the service.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SERVICE_ID, required = true) String serviceId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Relates inventory objects (resources) to a service.", tags = {"service-manager"})
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
            value = "relateObjectsToService/{objectsClassNames}/{objectsIds}/{serviceClassName}/{serviceId}/"
                    + "{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void relateObjectsToService(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The class names of the objects.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECTS_CLASS_NAMES, required = true) String[] objectsClassNames,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The ids of the objects.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECTS_IDS, required = true) String[] objectsIds,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The class name of the service.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SERVICE_CLASS_NAME, required = true) String serviceClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The id of the service.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SERVICE_ID, required = true) String serviceId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Releases an inventory object from a service.", tags = {"service-manager"})
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
            value = "releaseObjectFromService/{serviceClassName}/{serviceId}/{objectId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void releaseObjectFromService(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The class name of the service.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SERVICE_CLASS_NAME, required = true) String serviceClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The id of the service.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SERVICE_ID, required = true) String serviceId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The id of the object.",
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