//Returns all articles with property swrc:pages

MATCH(article:Article)-[swrc:pages]-(value) 
RETURN article
