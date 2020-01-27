//Return no of authors who have published with Paul Erdos OR with an author
that has published with “Paul Erdoes";

MATCH (erdoes:Person)-[:creator]-(doc), 
(doc)-[:creator]-(author),
(doc2)-[:creator]-(author2), 
(doc2)-[:creator]-(author)
WHERE erdoes.name= “Paul Erdoes” AND (NOT doc2.author = erdoes) 
AND (NOT doc2 =doc)  AND  (NOT  author2  =  erdoes)  
AND  (NOT  author2  =  author)  
RETURN DISTINCT author2.name 
UNION MATCH (erdoes:Person)-[:creator]-(doc),  
(doc)-[:creator]-(author)  
WHERE  NOT  author  =  erdoes  
RETURN DISTINCT author.name count(*) 
