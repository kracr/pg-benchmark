//Return top 10 product and their labels having ProductFeature1 and ProductFeature2 where productPropertyNumeric1 >x

MATCH(product:ProductType)-[:productFeature]-(ProductFeature1),(product:ProductType)-[:productFeature]-(ProductFeature2), 
WHERE product.productPropertyNumeric1>x  
RETURN  DISTINCT  product, product.label 
ORDER BY product.label 
LIMIT 10
