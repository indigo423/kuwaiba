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

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.HashMap;

/**
 * Basically a hash-map that stores a set of configuration parameters
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 * created on 01/08/2022-15:04
 */
@Node
@Data
@NoArgsConstructor
public class TemplateDataSource {
    /**
     * Configuration id
     */
    private long id;
    /**
     * Configuration name
     */
    private String name;
    /**
     * The parameters stored in this configuration entry
     */
    private HashMap<String, String> parameters;
    /**
     * Description about data source
     */
    private String description;

    public TemplateDataSource(long id, String name, String description, HashMap<String, String> parameters) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parameters = parameters;
    }
}
