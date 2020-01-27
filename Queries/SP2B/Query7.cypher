//Return the titles of all papers that have been cited at least
once, but not by any paper that has not been cited itself

MATCH(class)-[ISA]-(:Document), 
(doc:class)-[:member2]-(bag2),
(doc2)-[:references]-(bag2) 
OPTIONAL MATCH (class3)-[ISA]-(:Document),
(class4)-[ISA]-(:Document), 
(doc3:class3)-[:references]-(bag3)-[member], 
(doc4:class4)-[:references]-(bag4)-[:member4]-(doc3)
WHERE NOT exists doc4 AND NOTexists doc3 RETURN DISTINCT doc.title
