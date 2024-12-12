// Add label folders to favorite folders
// @author Lina Sofia Cardona Martinez {@literal <lina.cardona@kuwaiba.org>}

MATCH (user:users)-[:HAS_BOOKMARK]->(folder)
SET folder:folders
RETURN folder