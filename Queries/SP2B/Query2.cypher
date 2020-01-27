=//Returns the booktitle, abstract, issued year and other information of an Inproceedings having author, homepage=url, 
pages=#page, seeAlso=ee and partof=proc. Order the results by year of issue. 

MATCH(inproc:Inproceedings)-[:creator]-(author), 
(inproc:Inproceedings)-[:partof]-(proc), 
(inproc:Inproceedings)-[:seeAlso]-(ee), 
(inproc:Inproceedings)-[:pages]-(page), 
(inproc:Inproceedings)-[:homepage]-(url) 
OPTIONAL MATCH(inproc:Inproceedings)-[:abstract]-(abstract) 
RETURN inproc, author, inproc.booktitle, inproc.title,proc, ee, page, url, inproc.issuedyr, abstract 
ORDER BY inproc.issuedyr
