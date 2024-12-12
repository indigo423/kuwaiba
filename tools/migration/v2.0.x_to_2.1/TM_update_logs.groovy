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
  * name: TM_update_logs
  * description: Set the relationship PERFORMED_BY to log nodes without it
  * commitOnExecute: true
  * parameters: none
  * 
  * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
  */
import org.neotropic.kuwaiba.core.apis.persistence.application.TaskResult

def taskResult = TaskResult.newInstance()

try {
    // Creates the deletedUser node if not exist
    def query = "MERGE (deletedUser:deletedUsers {name: 'deletedUser'})"

    connectionHandler.execute(query)

    query = """
        MATCH (log), (deletedUser:deletedUsers {name: 'deletedUser'})
        WHERE ('objectActivityLogs' IN labels(log) OR 'generalActivityLogs' IN labels(log))
        AND NOT (log)-[:PERFORMED_BY]->(:users)
        AND NOT (log)-[:PERFORMED_BY]->(:deletedUsers)
        CREATE (log)-[:PERFORMED_BY]->(deletedUser)
    """

    connectionHandler.execute(query)

    taskResult.getMessages().add(TaskResult.createInformationMessage('Set the relationship PERFORMED_BY to log nodes without it'))

} catch(Exception ex) {
    taskResult.getMessages().add(TaskResult.createErrorMessage(String.format("Unexpected error: %s", ex.getMessage())))
}
taskResult