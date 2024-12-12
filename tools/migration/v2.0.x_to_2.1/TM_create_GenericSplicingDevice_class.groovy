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
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadata;
import org.neotropic.kuwaiba.core.apis.persistence.application.TaskResult;
 /**
  * Kuwaiba Version: 2.1
  * Version: 1.0
  * name: TM_create_GenericSplicingDevice_class
  * description: creates GenericSplicingDevice class and update SpliceBox and FiberCassette classes as its subclasses
  * commitOnExecute: true
  * parameters: none
  *
  * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
  */
def classGenericSplicingDevice = new ClassMetadata()
classGenericSplicingDevice.setName("GenericSplicingDevice")
classGenericSplicingDevice.setParentClassName("GenericBox")
classGenericSplicingDevice.setAbstract(true)
classGenericSplicingDevice.setInDesign(false)

mem.createClass(classGenericSplicingDevice)

def query = """
    MATCH (class:classes)-[r:EXTENDS]->(parent:classes)
    WHERE class.name IN ['SpliceBox', 'FiberCassette']
    DELETE r
"""

connectionHandler.execute(query)

query = """
    MATCH (class:classes), (parent:classes {name: 'GenericSplicingDevice'})
    WHERE class.name IN ['SpliceBox', 'FiberCassette']
    CREATE (class)-[:EXTENDS]->(parent)
"""

connectionHandler.execute(query)

mem.buildClassCache()

def taskResult = TaskResult.newInstance()
taskResult.getMessages().add(TaskResult.createInformationMessage("Created GenericSplicingDevice class and update SpliceBox and FiberCassette classes as its subclasses"))
taskResult