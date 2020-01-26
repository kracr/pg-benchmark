//Returns reviewer of ReviewXYZ

MATCH(X:reviewer)-[:reviewer]-(ReviewXYZ)
RETURN X
