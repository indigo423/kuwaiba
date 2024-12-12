MATCH (n:users {name:'admin'}) MERGE (n) -[:HAS_PRIVILEGE] - (p {featureToken:'userman', accessLevel:2}) RETURN p
