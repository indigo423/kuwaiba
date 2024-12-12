/*
 *  Copyright 2010-2021 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://apache.org/licenses/LICENSE-2.0.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import org.neotropic.kuwaiba.core.apis.persistence.application.TaskResult
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants

/**
 * Task to create the scripted queries pool ospman.geo
 * and the scripted queries
 * Nearby Physical Node
 * Nearby Manholes and Poles
 * ONT with Firmware
 * 
 * name: create_pool_ospman_geo
 * enabled: true
 * commitOnExecute: true
 * 
 * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
 */

def taskResult = new TaskResult()

def poolName = 'ospman.geo'
def poolDescription = ''
def pool = null
try {
  pool = aem.getScriptedQueriesPoolByName(poolName)
} catch (Exception ex) {
  aem.createScriptedQueriesPool(poolName, poolDescription)
  pool = aem.getScriptedQueriesPoolByName(poolName)
  taskResult.getMessages().add(TaskResult.createInformationMessage(String.format('Created scripted queries pool %s', poolName)))
}
// Nearby Physical Nodes
def scriptedQueryName = 'Nodes with Equipment with Free Ports'
def scriptedQueryScriptLink = 'https://sourceforge.net/p/kuwaiba/code/HEAD/tree/server/trunk/scripts/SQ_nodes_with_equipment_with_free_ports.groovy'
def scriptedQueryId = aem.createScriptedQuery(pool.getId(), scriptedQueryName, null, null, true)
aem.createScriptedQueryParameter(scriptedQueryId, 'latitude', null, Constants.DATA_TYPE_DOUBLE, true, null)
aem.createScriptedQueryParameter(scriptedQueryId, 'longitude', null, Constants.DATA_TYPE_DOUBLE, true, null)
aem.createScriptedQueryParameter(scriptedQueryId, 'viewNodes', null, null, true, null)
aem.createScriptedQueryParameter(scriptedQueryId, 'radius', 'Radius', Constants.DATA_TYPE_DOUBLE, true, 0.0D)
taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("Created scripted query %s. Download the script from %s", scriptedQueryName, scriptedQueryScriptLink)))

// Nearby Manholes and Poles
scriptedQueryName = 'Manholes and Poles'
scriptedQueryScriptLink = 'https://sourceforge.net/p/kuwaiba/code/HEAD/tree/server/trunk/scripts/SQ_manholes_and_poles.groovy'
scriptedQueryId = aem.createScriptedQuery(pool.getId(), scriptedQueryName, null, null, true)
aem.createScriptedQueryParameter(scriptedQueryId, 'latitude', null, Constants.DATA_TYPE_DOUBLE, true, null)
aem.createScriptedQueryParameter(scriptedQueryId, 'longitude', null, Constants.DATA_TYPE_DOUBLE, true, null)
aem.createScriptedQueryParameter(scriptedQueryId, 'viewNodes', null, null, true, null)
aem.createScriptedQueryParameter(scriptedQueryId, 'radius', 'Radius', Constants.DATA_TYPE_DOUBLE, true, 0.0D)
taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("Created scripted query %s. Download the script from %s", scriptedQueryName, scriptedQueryScriptLink)))

// ONT with Firmware
scriptedQueryName = 'Houses with ONTs with Firmware'
scriptedQueryScriptLink = 'https://sourceforge.net/p/kuwaiba/code/HEAD/tree/server/trunk/scripts/SQ_houses_with_onts_with_firmware.groovy'
scriptedQueryId = aem.createScriptedQuery(pool.getId(), scriptedQueryName, null, null, true)
aem.createScriptedQueryParameter(scriptedQueryId, 'latitude', null, Constants.DATA_TYPE_DOUBLE, true, null)
aem.createScriptedQueryParameter(scriptedQueryId, 'longitude', null, Constants.DATA_TYPE_DOUBLE, true, null)
aem.createScriptedQueryParameter(scriptedQueryId, 'viewNodes', null, null, true, null)
aem.createScriptedQueryParameter(scriptedQueryId, 'radius', 'Radius', Constants.DATA_TYPE_DOUBLE, true, 0.0D)
aem.createScriptedQueryParameter(scriptedQueryId, 'firmware', 'Firmware', Constants.DATA_TYPE_STRING, true, null)
taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("Created scripted query %s. Download the script from %s", scriptedQueryName, scriptedQueryScriptLink)))

// Nearby Houses without ONTs
scriptedQueryName = 'Houses without ONT Installed'
scriptedQueryScriptLink = 'https://sourceforge.net/p/kuwaiba/code/HEAD/tree/server/trunk/scripts/SQ_houses_without_ont_installed.groovy'
scriptedQueryId = aem.createScriptedQuery(pool.getId(), scriptedQueryName, null, null, true)
aem.createScriptedQueryParameter(scriptedQueryId, 'latitude', null, Constants.DATA_TYPE_DOUBLE, true, null)
aem.createScriptedQueryParameter(scriptedQueryId, 'longitude', null, Constants.DATA_TYPE_DOUBLE, true, null)
aem.createScriptedQueryParameter(scriptedQueryId, 'viewNodes', null, null, true, null)
aem.createScriptedQueryParameter(scriptedQueryId, 'radius', 'Radius', Constants.DATA_TYPE_DOUBLE, true, 0.0D)
taskResult.getMessages().add(TaskResult.createWarningMessage(String.format("Created scripted query %s. Download the script from %s", scriptedQueryName, scriptedQueryScriptLink)))

taskResult