/*
 *  Copyright 2010-2022 Neotropic SAS <contact@neotropic.co>.
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

 /**
  * Kuwaiba Version: 2.1
  * Script Version: 1.0
  * name: TM_create_virtual_warehouse_pool
  * description: Created Virtual Warehouse Pool
  * commitOnExecute: true
  * parameters: none
  * 
  * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
  */
import org.neotropic.kuwaiba.core.apis.persistence.application.TaskResult

def taskResult = TaskResult.newInstance()

try {
    connectionHandler.execute("""
        MERGE (virtualWarehousePool:pools {className: 'VirtualWarehouse', type: 2})
        ON CREATE SET
            virtualWarehousePool._uuid = randomUUID(),
            virtualWarehousePool.name = 'Virtual Warehouses',
            virtualWarehousePool.description = 'Virtual Warehouses'
    """)
    taskResult.getMessages().add(TaskResult.createInformationMessage('Created Virtual Warehouse Pool'))

} catch(Exception ex) {
    taskResult.getMessages().add(TaskResult.createErrorMessage(String.format("Unexpected error: %s", ex.getMessage())))
}
taskResult