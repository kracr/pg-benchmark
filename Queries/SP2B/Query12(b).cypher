//Returns the no. of results after union of the two queries.
Query 1: Return author names who are creators of a document where Paul Erdoes is also a creator.
Query 2: Return author names who are creator and author of a document and author not same as erdoes.

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
