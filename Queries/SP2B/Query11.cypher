//Return (up to) 10 electronic edition URLs starting from
the 51th publication, in lexicographical order.


MATCH(publication)-[:seeAlso]-[ee] 
RETURN ee 
ORDER BY ee
SKIP 50 
LIMIT 10
