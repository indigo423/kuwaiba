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
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.northbound.rest.RestConstants;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Swagger documentation for physical connections.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@RequestMapping(PhysicalConnectionRestOpenApi.PATH)
public interface PhysicalConnectionRestOpenApi {
    // <editor-fold desc="physical-connections" defaultstate="collapsed">
    /**
     * Path that includes the Kuwaiba version and module
     */
    public static final String PATH = "/v2.1.1/physical-connections/"; //NOI18N
    
    @Operation(summary = "Creates a physical connection.",
            description = "The id of the newly created physical connection.", 
            tags = {"physical-connections"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.POST,
            value = "createPhysicalConnection/{aObjectClassName}/{aObjectId}/{bObjectClassName}/{bObjectId}/"
                    + "{name}/{connectionClassName}/{templateId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String createPhysicalConnection(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The class name of the first object to related.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.A_OBJECT_CLASS_NAME, required = true) String aObjectClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The id of the first object to related.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.A_OBJECT_ID, required = true) String aObjectId,
            @Parameter(in = ParameterIn.PATH, description = "The class name of the second object to related.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.B_OBJECT_CLASS_NAME, required = true) String bObjectClassName,
            @Parameter(in = ParameterIn.PATH, description = "The id of the first object to related.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.B_OBJECT_ID, required = true) String bObjectId,
            @Parameter(in = ParameterIn.PATH, description = "The connection name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.NAME, required = true) String name,
            @Parameter(in = ParameterIn.PATH, 
                    description = "The class name of the connection. Must be subclass of GenericPhysicalConnection.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.CONNECTION_CLASS_NAME, required = true) String connectionClassName,
            @Parameter(in = ParameterIn.PATH, description = "Template id to be used to create the current object. "
                    + "Use \"null\" as string or empty string to not use a template.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.TEMPLATE_ID, required = true) String templateId,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Deletes a physical connection.", tags = {"physical-connections"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.DELETE,
            value = "deletePhysicalConnection/{objectClassName}/{objectId}/{userName}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deletePhysicalConnection(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The class name of the object.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECT_CLASS_NAME, required = true) String objectClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The id of the object.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECT_ID, required = true) String objectId,
            @Parameter(in = ParameterIn.PATH, description = "The user name of the session.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.USER_NAME, required = true) String userName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Finds the physical path from one port to another.",
            description = "A list of objects that make part of the physical trace.", 
            tags = {"physical-connections"})
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
            value = "getPhysicalPath/{objectClassName}/{objectId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<BusinessObjectLight> getPhysicalPath(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The source port class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECT_CLASS_NAME, required = true) String objectClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The source port id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECT_ID, required = true) String objectId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    
    @Operation(summary = "Gets A tree representation of all physical paths as a hash map.",
            description = "A tree representation of all physical paths as a hash map.", 
            tags = {"physical-connections"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = HashMap.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "405", description = "Method not allowed", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An unexpected error occurred. Contact your administrator for details",
                    content = @Content)
    })
    @RequestMapping(method = RequestMethod.GET,
            value = "getPhysicalTree/{objectClassName}/{objectId}/{sessionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public HashMap<BusinessObjectLight, List<BusinessObjectLight>> getPhysicalTree(
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The source port class name.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECT_CLASS_NAME, required = true) String objectClassName,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The source port id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.OBJECT_ID, required = true) String objectId,
            @NotNull @Parameter(in = ParameterIn.PATH, description = "The session token id.",
                    required = true, schema = @Schema())
            @Valid @PathVariable(value = RestConstants.SESSION_ID, required = true) String sessionId);
    // </editor-fold>
}