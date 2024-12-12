/**
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

/**
  * Kuwaiba Version: 2.1
  * 
  * name: change_view_tag_to_has_layout_
  * Description: Change the relationship tag 'HAS_VIEW' to 'HAS_LAYOUT'
  * commitOnExecute: true
  * parameters: none
  * 
  * @author Orlando Paz {@literal <orlando.paz@kuwaiba.org>}
  */

import org.neotropic.kuwaiba.core.apis.persistence.application.TaskResult;

def taskResult = new TaskResult();
try {
    def cypherQuery = "MATCH (lti:listTypeItems)-[rel:HAS_VIEW]->(layout:layouts) MERGE (lti)-[:HAS_LAYOUT]->(layout) DELETE rel";
    def result = connectionHandler.execute(cypherQuery);
    taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("%s relations updated", 
                                    result.getQueryStatistics().getRelationshipsCreated())));
} catch(Exception ex) {
    taskResult.getMessages().add(TaskResult.createErrorMessage(String.format("Unexpected error: %s", ex.getMessage())));
}
return taskResult;

