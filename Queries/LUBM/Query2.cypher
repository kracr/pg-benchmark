//Returns Graduate Students of a Department in a University 

MATCH(X:GraduateStudent)-[:memberOf]-(Z:Department), 
(Z:Department)-[:subOrganizationOf]-(Y:University),
(X:GraduateStudent)-[:undergraduateDegreeFrom]-(Y:University) 
RETURN X, Y, Z
