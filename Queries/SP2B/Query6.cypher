//Return, for each year, the set of all publications authored
by persons that have not published in years before.

MATCH(class)-[IS_A]-(:Document), 
(doc:class)-[:creator]-(author),
OPTIONAL  MATCH  (class2)-[IS_A]-(:Document),  
(doc2:class2)-[:creator]-(author2), 
WHERE NOT exists (author2) AND author.name = author2.name AND doc2.yr2 > doc.yr 
RETURN yr, author.name, doc
