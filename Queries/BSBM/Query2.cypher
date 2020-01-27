//Returns label, comment, productFeatures and other product properties of Products
having comment, same producer and publisher and productFeature

MATCH(ProductXYZ)-[:comment]-(comment), 
(ProductXYZ)-[:producer]-(p), 
(ProductXYZ)-[:publisher]-(p), 
(ProductXYZ)-[:producerFeature]-(f)-[:label]-(productFeature), 
OPTIONAL MATCH(ProductXYZ)-[:productPropertyTextual4]-(propertyTextual4), 
(ProductXYZ)-[:productPropertyTextual5]-(propertyTextual5),
RETURN label,comment,producer,productFeature,producer.propertyTextual1,producer.propertyTextual2, producer.propertyTextual3,
producer.propertyTextual4, producer.propertyTextual5, producer.propertyNumeric1, producer.propertyNumeric2,producer.propertyNumeric4
