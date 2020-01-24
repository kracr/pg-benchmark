MATCH(X:Professor)-[:name]-(Y1), 
(X:Professor)-[:emailAddress]-(Y2), 
(X:Professor)-[:telephone]-(Y3) 
WHERE X.worksFor = “http://www.Department().University().edu” 
RETURN X,Y1,Y2,Y3
