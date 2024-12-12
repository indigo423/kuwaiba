/*
 *  Copyright 2010-2021 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License")
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
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadata
 /**
  * Kuwaiba Version: 2.1
  * Version: 1.0
  * name: TM_create_GenericLastMileCircuit_class
  * description: Creates the GenericLastMileCircuit class as subclass of GenericLogicalConnection and its subclasses Circuit and Path
  * commitOnExecute: true
  * parameters: none
  *
  * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
  */
def genericLogicalConnectionClassName = "GenericLogicalConnection"
def genericLastMileCircuitClassName = "GenericLastMileCircuit"
def circuitClassName = "Circuit"
def pathClassName = "Path"

def genericLastMileCircuitClass = new ClassMetadata()
genericLastMileCircuitClass.setName(genericLastMileCircuitClassName)
genericLastMileCircuitClass.setParentClassName(genericLogicalConnectionClassName)
genericLastMileCircuitClass.setAbstract(true)
genericLastMileCircuitClass.setInDesign(false)
mem.createClass(genericLastMileCircuitClass)

def circuitClass = new ClassMetadata()
circuitClass.setName(circuitClassName)
circuitClass.setParentClassName(genericLastMileCircuitClassName)
circuitClass.setAbstract(false)
circuitClass.setInDesign(false)
mem.createClass(circuitClass)

def pathClass = new ClassMetadata()
pathClass.setName(pathClassName)
pathClass.setParentClassName(genericLastMileCircuitClassName)
pathClass.setAbstract(false)
pathClass.setInDesign(false)
mem.createClass(pathClass)

def taskResult = new TaskResult()
taskResult.getMessages().add(TaskResult.createInformationMessage(String.format("Created classes %s, %s and %s", genericLastMileCircuitClassName, circuitClassName, pathClassName)))

taskResult