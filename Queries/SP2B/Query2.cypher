MATCH(inproc:Inproceedings)-[:creator]-(author), 
(inproc:Inproceedings)-[:partof]-(proc), 
(inproc:Inproceedings)-[:seeAlso]-(ee), 
(inproc:Inproceedings)-[:pages]-(page), 
(inproc:Inproceedings)-[:homepage]-(url) 
OPTIONAL MATCH(inproc:Inproceedings)-[:abstract]-(abstract) 
RETURN inproc, author, inproc.booktitle, inproc.title,proc, ee, page, url, inproc.issuedyr, abstract 
ORDER BY inproc.issuedyr
