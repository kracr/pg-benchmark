# PGBench Documentation
This document provides documentation for the first version of PGBench. PGBench is a Property Graph Benchmark that takes an RDF graph as input and converts it to a property graph. Further, it runs queries on two well known Graphstores: Neo4j and RedisGraph.

# Table of Contents
1 [Introduction](#Intoduction)<br>
2 [About the Repository](#About-the-Repository)<br>
3 [Usage](#Usage)<br>
4 [Future Work](#Future-Work)

## Introduction
Property Graphs are one of the most common representation for Knowledge Graphs, another one being RDF graphs. Even though there exist several RDF benchmarks but none exist for property graphs. This makes evaluation of property graphstores difficult. Therefore, to bridge this gap, we developed a Proprty Graph benchmark named PGBench. 

Our benchmark provides an RDF to Property Graph converter. For evaluation, we have used three RDF benchmarks Lehigh University Benchmark (LUBM), SP^2 Benchmark, and Berlin SPARQL Benchmark (BSBM) to generate RDF graphs of different sizes and convert them to property graphs. We translated the SPARQL queries from each of these RDF benchmarks to their equivalent Cypher queries. Finally, we used two different property graphs databases, Neo4j and RedisGraph, for evaluating the loading time and the querying time.


## About the Repository
Repository consists of the following directories and files: 
src/main/java is a java source code directory which contains java code for RDF to Property Graph converter. The directory named Queries consists cypher queries for three different RDF benchmarks, BSBM, SP^2 Bench and LUBM. These queries were originally SPARQL and have been converted to Cypher. These queries were used to evaluation. One xml file, pom.xml which contains the maven dependencies required to build the converter. The releases section contains a jar which can be used for the direct execution of the RDF to Property Graph converter.

## Usage
In order to directly run the converter, download the file named RDFtoPGConverter.jar from the releases section. Note that the input RDF file can be in either .ttl format or .nt format. Run the converter using the command **java -jar RDFtoPGConverter.jar input.ttl**
The converter gives a JSON format file as an output which represents a property graph corresponding to the input RDF data.

To load the output Property Graph into Graph Database (Neo4j or RedisGraph) use the following command:



## Future Work
The next stage of the work will aim at:
1. Making the benchmark efficient.
2. Covering other graphstores for benchmark evaluation.
