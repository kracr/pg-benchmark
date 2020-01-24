MATCH(X:Student)-[:takesCourse]-(Y:Course)-[:teacherOf]-(Z:AssociateProfeesor) 
WHERE Z.name= “http://www.Department0.University0.edu/AssociateProfessor0” 
RETURN X, Y
