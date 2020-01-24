MATCH(publication)-[:seeAlso]-[ee] 
RETURN ee 
ORDER BY ee
SKIP 50 
LIMIT 10
