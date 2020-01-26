//Returns article with isbn #value

MATCH(article:Article)-[swrc:isbn]-(value) 
RETURN article
