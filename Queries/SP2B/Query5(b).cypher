MATCH(article:Article)-[:creator]-(person), 
(inproc:Inproceedings)-[:creator]–(person) 
RETURN DISTINCT person, person.name
