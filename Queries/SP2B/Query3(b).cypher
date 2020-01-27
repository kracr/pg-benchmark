//Returns all articles with property swrc:month

MATCH(article:Article)-[swrc:month]-(value) 
RETURN article
