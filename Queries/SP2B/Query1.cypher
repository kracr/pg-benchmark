MATCH(journal:Journal)-[:issued]-(yr) 
WHERE  journal.title  =“Journal 1 (1940)” 
RETURN yr
