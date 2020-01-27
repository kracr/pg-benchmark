//Returns the no. of persons who appear as author of at least one inproceeding and article

MATCH(article:Article)-[:creator]-(person), 
(inproc:Inproceedings)-[:creator]-(person) 
RETURN DISTINCT person, person.name count(*)
