//Returns a count of Persons with name "JohnQPublic"

MATCH(X:Person) 
WHERE X.name = ”JohnQPublic” 
RETURNX count(*)
