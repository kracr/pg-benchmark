MATCH(X:ResearchGroup) 
WHERE X:subOrganizationOf = “http://www.University0.edu” 
RETURN X
