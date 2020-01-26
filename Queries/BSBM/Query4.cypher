//After union of two queries return 10 results after skipping first 10
Query 1: Return products and their labels having ProductFeature1 and ProductFeature2 where productPropertyNumeric1 > x
Query 2: Return products and their labels having ProductFeature1 and ProductFeature3 where productPropertyNumeric2 > y

MATCH(product:ProductType)-[:productFeature]-(productFeature1),(product:ProductType)-[:productFeature]-(productFeature2), 
WHERE product.productPropertyNumeric1>x 
RETURN product, product.label 
UNIONMATCH(product:ProductType)-[:productFeature]-(productFeature1), (product:ProductType)-[:productFeature]-(productFeature3), 
WHERE product.productPropertyNumeric2>y 
RETURN product, product.label 
ORDER BY product.label 
SKIP 10
LIMIT 10
