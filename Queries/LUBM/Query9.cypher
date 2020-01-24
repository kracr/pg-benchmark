MATCH(X:Student)-[:advisor]-(Y:Faculty), 
(Y:Faculty)-[:teacherOf]-(Z:Course), 
(X:Student)-[:takesCourse]-(Z:Course) 
RETURN X,Y,Z
