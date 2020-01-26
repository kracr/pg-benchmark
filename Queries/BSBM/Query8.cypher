//Returns title and other information of Product whose title=title, text=text, reviewDate=reviewDate, reviewer=reviewer, name=reviewerName
optionally satisfy conditions, rating2=rating2, rating3=rating3, rating4=rating4 and language=en order by descending order of review date

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
