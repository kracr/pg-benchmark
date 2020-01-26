//Returns alumnus of University0

MATCH(X: Person)-[:hasAlumnus]-(Y:University) 
WHERE Y.name= “http://www.University0.edu” 
RETURN X
