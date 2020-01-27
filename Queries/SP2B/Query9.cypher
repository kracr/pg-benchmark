//Return incoming and outgoing properties of persons

MATCH(subject)-[predicate]-(person:Person)
RETURN DISTINCT predicate 
UNION MATCH(person:Person)-[predicate]-(object) 
RETURN DISTINCT predicate
