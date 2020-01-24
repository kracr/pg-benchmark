MATCH(X:Student)-[:memberOf]-(Y:Department),  
(X:Student)-[:emailAddress]-(Z) 
WHERE Y:subOrganizationOf = “http://www.University0.edu"
RETURN X,Y,Z
