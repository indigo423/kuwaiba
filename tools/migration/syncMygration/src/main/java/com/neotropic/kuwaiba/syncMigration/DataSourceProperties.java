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
package com.neotropic.kuwaiba.syncMigration;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.graphdb.Node;

/**
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 * created on 28/07/2022-09:30
 */
@Getter
@Setter
public class DataSourceProperties {
    private List<String> keys;
    private Node properties;

    public DataSourceProperties(List<String> keys, Node properties){
        this.keys = keys;
        this.properties = properties;
    }
}
