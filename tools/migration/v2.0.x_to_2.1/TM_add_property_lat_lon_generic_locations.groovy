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
  * description: Change if attributes 'longitude' and 'latitude' exist on GenericLocation class and his childrens and adds them if necessary.
  * commitOnExecute: true
  * parameters: none
  * 
  * @author Orlando Paz {@literal <orlando.paz@kuwaiba.org>}
  */

import org.neotropic.kuwaiba.core.apis.persistence.application.TaskResult;

def taskResult = new TaskResult();
try {
    def cypherQuery = "MATCH (cs:classes {name:'GenericLocation'}) <- [:EXTENDS*] - (subClass:classes) MERGE (cs)-[:HAS_ATTRIBUTE]->(attLat:attributes {name:'latitude'}) MERGE (cs)-[:HAS_ATTRIBUTE]->(attLon:attributes {name:'longitude'}) MERGE (subClass)-[:HAS_ATTRIBUTE]->(attLatSubClass:attributes {name:'latitude'}) MERGE (subClass)-[:HAS_ATTRIBUTE]->(attLonSubClass:attributes {name:'longitude'})" + 
                "set attLat.administrative=false, attLat.creationDate=1471034759179, attLat.isVisible=true, attLat.description='', attLat.displayName='', attLat.type='Float', attLat.noCopy=false, attLat.readOnly=false, attLat.unique=false, " + 
                "attLon.administrative=false, attLon.creationDate=1471034759179, attLon.isVisible=true, attLon.description='', attLon.displayName='', attLon.type='Float', attLon.noCopy=false, attLon.readOnly=false, attLon.unique=false,   " + 
                "attLatSubClass.administrative=false, attLatSubClass.creationDate=1471034759179, attLatSubClass.isVisible=true, attLatSubClass.description='', attLatSubClass.displayName='', attLatSubClass.type='Float', attLatSubClass.noCopy=false, attLatSubClass.readOnly=false,  attLatSubClass.unique=false," + 
                "attLonSubClass.administrative=false, attLonSubClass.creationDate=1471034759179, attLonSubClass.isVisible=true, attLonSubClass.description=''," + 
                "attLonSubClass.displayName='', attLonSubClass.type='Float', attLonSubClass.noCopy=false, attLonSubClass.readOnly=false, attLonSubClass.unique=false";
    def result = connectionHandler.execute(cypherQuery);
    taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("%s relations created", 
                                    result.getQueryStatistics().getRelationshipsCreated())));
} catch(Exception ex) {
    taskResult.getMessages().add(TaskResult.createErrorMessage(String.format("Unexpected error: %s", ex.getMessage())));
}
return taskResult;

