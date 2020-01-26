//Return all nodes related to person named PaulErdoes

MATCH (:person  name: ’PaulErdoes’ )<-(pred)
RETURN pred
