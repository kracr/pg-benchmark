MATCH(product:ProductType)-[:productFeature]-(productFeature1),(product:ProductType)-[:productFeature]-(productFeature2), 
WHERE prod-uct.productPropertyNumeric1>x 
RETURN product, product.label 
UNIONMATCH(product:ProductType)-[:productFeature]-(productFeature1), (product:ProductType)-[:productFeature]-(productFeature3), 
WHERE product.productPropertyNumeric2>y 
RETURN product, product.label 
ORDER BY product.label 
SKIP 10
LIMIT 10
