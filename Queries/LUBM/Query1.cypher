//Returns all Graduate students of Department0 and University0 who have taken course GraduateCourse0

MATCH(X:GraduateStudent) 
WHERE X.takesCourse = “http://www.Department0.University0.edu/GraduateCourse0” 
RETURN X

