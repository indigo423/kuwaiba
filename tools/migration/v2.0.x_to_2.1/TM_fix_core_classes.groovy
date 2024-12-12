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
  * description: Sets the "custom" flag to false for all non-abstract classes, so they can be deleted. Up to version 2.0.2, 
                 those appeared as "core classes" and thus, could not be removed. This restriction is not really necessary.
  * commitOnExecute: true
  * parameters: none
  * 
  * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
  */

import org.neotropic.kuwaiba.core.apis.persistence.application.TaskResult;

def taskResult = new TaskResult();
try {
    def cypherQuery = "MATCH (aClass:classes) WHERE aClass.abstract <> true SET aClass.custom = true RETURN aClass"
    def result = connectionHandler.execute(cypherQuery);
    taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("%s classes updated", 
                                    result.getQueryStatistics().getPropertiesSet())));
} catch(Exception ex) {
    taskResult.getMessages().add(TaskResult.createErrorMessage(String.format("Unexpected error: %s", ex.getMessage())));
}
return taskResult;