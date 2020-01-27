// Return the names of all persons that occur as author
of at least one inproceeding and at least one article

MATCH(article:Article)-[:creator]-(person), 
(inproc:Inproceedings)-[:creator]–(person) 
RETURN DISTINCT person, person.name
