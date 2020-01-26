//Return all Research Groups which are sub-organization of University0

MATCH(X:ResearchGroup) 
WHERE X:subOrganizationOf = “http://www.University0.edu” 
RETURN X
