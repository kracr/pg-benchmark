//Returns all articles with property swrc:isbn

MATCH(article:Article)-[swrc:isbn]-(value) 
RETURN article
