//Returns the year in which 'Journal 1 (1940)' was issued.

MATCH(journal:Journal)-[:issued]-(yr) 
WHERE  journal.title  =“Journal 1 (1940)” 
RETURN yr
