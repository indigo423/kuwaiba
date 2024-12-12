// Script to add label objectRelatedViews to the end node of relationships HAS_VIEW
// 
// @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}

MATCH (a)-[:HAS_VIEW]->(b)
SET b:objectRelatedViews;