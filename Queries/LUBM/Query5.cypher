//Returns all members of Department0 of University0

MATCH(X:Person)
WHERE X.memberOf = ”http://www.Department0.University0.edu” 
RETURN X
