//Returns a Professor having name Y1, email Y2 and telephone Y3, and who works for Department0 of University0.

MATCH(X:Professor)-[:name]-(Y1), 
(X:Professor)-[:emailAddress]-(Y2), 
(X:Professor)-[:telephone]-(Y3) 
WHERE X.worksFor = “http://www.Department0.University0.edu” 
RETURN X,Y1,Y2,Y3
