MATCH(subject)-[predicate]-(person:Person) 
RETURN DISTINCT predicate 
UNION MATCH(person:Person)-[predicate]-(object) 
RETURN DISTINCT predicate
