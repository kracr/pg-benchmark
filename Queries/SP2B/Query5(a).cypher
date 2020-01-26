//Returns names of people who are creators of an article as well as an Inproceedings

MATCH(article:Article):[:creator]-(person), 
(inproc:Inproceedings)-[:creator]-(person2) 
WHERE person.name = person2.name2 
RETURN DISTINCT person.name
