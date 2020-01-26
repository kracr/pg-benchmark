//Returns Students and their email addresses department wise for Univesity0

MATCH(X:Student)-[:memberOf]-(Y:Department),  
(X:Student)-[:emailAddress]-(Z) 
WHERE Y:subOrganizationOf = “http://www.University0.edu"
RETURN X,Y,Z
