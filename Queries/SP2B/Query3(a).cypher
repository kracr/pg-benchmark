//Returns article with number of pages #value

MATCH(article:Article)-[swrc:pages]-(value) 
RETURN article
