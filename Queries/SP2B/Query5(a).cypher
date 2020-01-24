MATCH(article:Article):[:creator]-(person), 
(inproc:Inproceedings)-[:creator]-(person2) 
WHERE person.name = person2.name2 
RETURN DISTINCT person, name
