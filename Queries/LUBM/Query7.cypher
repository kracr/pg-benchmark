//Returns all students who've taken a course taught by Associate Professsor of Department0 of University0

MATCH(X:Student)-[:takesCourse]-(Y:Course)-[:teacherOf]-(Z:AssociateProfeesor) 
WHERE Z.name= “http://www.Department0.University0.edu/AssociateProfessor0” 
RETURN X, Y
