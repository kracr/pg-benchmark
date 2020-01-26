//Return product and its label where label = "word"

MATCH(product:Product) 
WHERE product.label = “word” 
RETURN product, product.label
