//Returns the no. of persons who are creators of an Article as well as an Inproceedings

MATCH(article:Article)-[:creator]-(person), 
(inproc:Inproceedings)-[:creator]-(person) 
RETURN DISTINCT person, person.name count(*)
