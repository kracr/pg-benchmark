//Returns 10 results for 'seeAlso' section of a publication after skipping first 50 results.

MATCH(publication)-[:seeAlso]-[ee] 
RETURN ee 
ORDER BY ee
SKIP 50 
LIMIT 10
