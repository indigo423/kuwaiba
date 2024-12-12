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
 * created on 28/09/2022-12:15
 */
@Node
@NoArgsConstructor
public class SyncDataSourceCommonParameters {
    /**
     * Configuration id
     */
    @Setter @Getter
    private long id;
    /**
     * Data source type
     */
    @Setter @Getter
    private String dataSourcetype;
    /**
     * The parameters stored in this configuration entry
     */
    @Setter @Getter
    private HashMap<String, String> parameters;
    /**
     * Temporal parameters stored in this configuration entry
     */
    @Getter
    private final List<ParameterItemDataSource> listOfParameters = new ArrayList<>();

    public SyncDataSourceCommonParameters(String dataSourcetype) {
        this.dataSourcetype = dataSourcetype;
    }

    /**
     * Create a new common parameter for data source
     * @param key property name
     * @param value property value
     */
    public void addParameter(String key, String value){
        ParameterItemDataSource item = new ParameterItemDataSource(key, value);
        if(!listOfParameters.contains(item))
            listOfParameters.add(item);
    }
}
