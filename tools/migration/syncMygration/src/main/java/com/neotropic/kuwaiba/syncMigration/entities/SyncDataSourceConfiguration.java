/*
 *  Copyright 2010-2019 Neotropic SAS <contact@neotropic.co>.
 *
 *   Licensed under the EPL License, Version 1.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *        http://www.eclipse.org/legal/epl-v10.html
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package com.neotropic.kuwaiba.syncMigration.entities;

import com.neotropic.kuwaiba.syncMigration.helpers.ParameterItemDataSource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Basically a hash-map that stores a set of configuration parameters
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 * created on 01/08/2022-14:56
 */
@Node
@NoArgsConstructor
public class SyncDataSourceConfiguration {
    /**
     * Configuration id
     */
    @Getter @Setter
    private long id;
    /**
     * Configuration name
     */
    @Getter @Setter
    private String name;
    /**
     * The parameters stored in this configuration entry
     */
    @Getter @Setter
    private HashMap<String, String> parameters;
    /**
     * Common parameters used  in this data source by example ip and type
     */
    @Getter @Setter
    private SyncDataSourceCommonParameters commonParameters;
    /**
     * Description about data source
     */
    @Getter @Setter
    private String description;
    /**
     * Template data source used
     */
    @Getter @Setter
    private TemplateDataSource templateDataSource;
    /**
     * Business ObjectLight Id
     */
    @Getter @Setter
    private String businessObjectLightId;
    /**
     * Temporal parameters stored in this configuration entry
     */
    @Getter
    private final List<ParameterItemDataSource> listOfParameters = new ArrayList<>();

    /**
     * Create a new specific parameter for data source
     * @param key property name
     * @param value property value
     */
    public void addParameter(String key, String value){
        ParameterItemDataSource item = new ParameterItemDataSource(key, value);
        if(!listOfParameters.contains(item))
            listOfParameters.add(item);
    }
}
