MATCH(X:review)-[:reviewFor]-(ProductXYZ),
(X:review)-[:title]-(title), 
(X:review)-[:text]-(text), 
(X:review)-[:reviewDate]-(reviewDate), 
(X:review)-[:reviewer]-(reviewer), 
(Y:reviewer)-[:name]-(reviewerName)
OPTIONAL MATCH(X:review)-[:rating1]-(rating1), 
(X:review)-[:rating2]-(rating2),
(X:review)-[:rating3]-(rating3), 
(X:review)-[:rating4]-(rating4) 
WHERE text.language= “en” 
RETURN title,text,reviewDate,reviewer,reviewerName,rating1,rating2,rating3,rating4
ORDER BY reviewDATE DESC 
LIMIT 20
