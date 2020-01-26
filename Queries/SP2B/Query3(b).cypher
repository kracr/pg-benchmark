//Returns article with publish month #value

MATCH(article:Article)-[swrc:month]-(value) 
RETURN article
