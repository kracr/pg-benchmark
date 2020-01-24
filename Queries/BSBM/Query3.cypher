MATCH(product:ProductType)-[:productFeature]-(ProductFeature1)
OPTIONAL MATCH (product:ProductType)-[productFeature]-(ProductFeature2)
WHERE product.productPropertyNumeric1>x AND product.productPropertyNumeric3<y AND NOT exists product.label 
RETURN product, product.label 
ORDER BY product.label 
LIMIT 10
