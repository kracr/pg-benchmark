MATCH(X:Chair)-[:worksFor]-(Y:Department) 
WHERE Y:subOrganizationOf= “http://www.University().edu” 
RETURN X,Y
