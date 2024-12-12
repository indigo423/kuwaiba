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
  * description: Sets all attribute metadata readOnly property to false, except if it is the attribute creationDate. 
  * After running it (with the flag commitOnExecute set to true in the Task Manager task), restart the server or rebuild the class metadata cache
  * by modifying the datamodel (i.e. creating and deleting a class).
  * commitOnExecute: true
  * parameters: none
  * 
  * @author Orlando Paz {@literal <orlando.paz@kuwaiba.org>}
  */
import org.neotropic.kuwaiba.core.apis.persistence.application.TaskResult;

//Creates the task result instance using reflection
def taskResult = TaskResult.newInstance();

 try {
     def query = "MATCH (classMetadata)-[:HAS_ATTRIBUTE]->(attributeMetadata) WHERE attributeMetadata.name <> 'creationDate' SET attributeMetadata.readOnly = false";
     def result = connectionHandler.execute(query);
     taskResult.getMessages().add(TaskResult.createInformationMessage("All attributes in the data model were patched correctly"));
 } catch(Exception e) {
     taskResult.getMessages().add(TaskResult.createErrorMessage(String.format("Unexpected error found while executing the script: %s", 
					e.getMessage())));
 }

 taskResult
