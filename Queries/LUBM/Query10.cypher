//Return all students who've taken course GraduateCourse0

MATCH(X:Student) 
WHERE X.takesCourse = “http://www.Department0.University0.edu/GraduateCourse0” 
RETURN X
