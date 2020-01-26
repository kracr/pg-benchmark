//Returns the publications of AssistantProfessor0 of Department0 in University0

MATCH(X:Publication) 
WHERE X.publicationAuthor = “http://www.Department0.University0.edu/AssitantProfessor0” 
RETURN X
