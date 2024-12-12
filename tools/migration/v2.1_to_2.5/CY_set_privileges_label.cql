// Script to add label privileges to end node of relationships HAS_PRIVILEGE
//
// @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}

MATCH (:users)-[:HAS_PRIVILEGE]->(privilege)
SET privilege:privileges;