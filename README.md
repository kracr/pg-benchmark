PGBench documentation
PGBench is a Property Graph Benchmark that takes an RDF graph as input and converts it to a property graph. Further, it runs queries on two well known Graphstores: Neo4j and Redis.

Goal
Benchmarks play an important role in the development of any new systems. Even though there exist several RDF benchmarks but none exist for property graphs. This makes evaluation of property graphstores difficult. Therefore, to bridge this gap, we developed PGBench.


Usage
To use PGBench on an IDE, download the source code file named RDFtoPGConverter and pom.xml. Compile them as a maven project 
Note that the input RDF file can be in either .ttl format or .nt format. Pass the input file as argument when running the convertor.
The converter gives a JSON format file as an output which represents a property graph corresponding to the input RDF data.


Future work

The next stage of the work will aim at:
1. Making the benchmark efficient.
2. Covering other graphstores for benchmark evaluation.
