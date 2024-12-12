// Update the validator definition nodes to improve the integrity
// of data stored creating the relationship HAS_VALIDATOR_DEFINITION
// with a class node (the corresponding class node
// are defined by the className property in the validator
// definition node) and remove the validator definition
// property className.
//
// @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}

MATCH (validatorDefinition:validatorDefinitions)
MATCH (class:classes)
WHERE validatorDefinition.className = class.name
CREATE (class)-[:HAS_VALIDATOR_DEFINITION]->(validatorDefinition)
REMOVE validatorDefinition.className;
