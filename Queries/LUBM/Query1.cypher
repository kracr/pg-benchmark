//Returns all Graduate students who have taken course GraduateCourse0

MATCH(X:GraduateStudent) 
WHERE X.takesCourse = “http://www.Department0.University0.edu/GraduateCourse0” 
RETURN X

