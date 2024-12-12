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

import org.neo4j.graphdb.RelationshipType;

/**
 * Possible relationship types
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 * created on 28/09/2022-13:04
 */
public enum RelTypes implements RelationshipType {
    /**
     * Used to relate a synchronization data source configuration with an object.
     * inventoryObjects -> syncDatasourceConfiguration
     * cardinality (1..*)
     */
    HAS_SYNC_CONFIGURATION,
    /**
     * Used to relate a synchronization data source configuration with a common properties it have data source type.
     * syncDatasourceConfiguration -> syncDatasourceCommonProperties
     * cardinality (*..1)
     */
    HAS_SYNC_COMMON_PROPERTIES,
    /**
     * Used to associate a synchronization data source to a synchronization group
     * syncDatasourceConfiguration -> syncGroups
     * cardinality (*..1)
     */
    BELONGS_TO_SYNC_GROUP,
    /**
     * Used to associate a synchronization data source to a template data source
     * syncDatasourceConfiguration -> templateDataSource
     * cardinality (*..1)
     */
    EXTENDS_FROM_TEMPLATE,
    /**
     * Old associate a synchronization data source to a synchronization group
     */
    BELONGS_TO_GROUP
}
