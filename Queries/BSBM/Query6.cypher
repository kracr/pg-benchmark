MATCH(product:Product) 
WHERE product.label = “word” 
RETURN product, product.label
