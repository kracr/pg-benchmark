//Returns all distinct pairs of article author names for authors
that have published in the same journal.

MATCH(article1:Article)-[:creator]-(author1),  
(article1:Article)-[:journal]-(journal), 
(article2:Article)-[:creator]–(author2), 
(article2:Article)-[:journal]-(journal), 
WHERE author1.name1, author2.name2 
RETURN DISTINCT name1,name2
