import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.api.java.JavaSparkContext ;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.functions ;
import org.apache.spark.util.CollectionAccumulator;
import org.apache.spark.util.LongAccumulator;
import org.apache.spark.api.java.function.ForeachFunction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.Instant;
import java.time.Duration;
	
public class ScalableRDF2PG {
	public static void main(String[] args)throws IOException, URISyntaxException {
		String dataPath = args[0];
		String writePath = args[1];
		int threshold = Integer.valueOf(args[2]);
		Instant start = Instant.now();
		SparkSession spark = SparkSession
				  .builder()
				  .appName("ScalableRDF2PG")
				  .getOrCreate();
		final LongAccumulator properties_count = spark.sparkContext().longAccumulator();
		long triples_processed = 0 ;
		long files_processed =0;
		final CollectionAccumulator<HashMap> vertexList = spark.sparkContext().collectionAccumulator(); // vertex accumulator
		final CollectionAccumulator<HashMap> edgeList = spark.sparkContext().collectionAccumulator(); // edge accumulator

		// begin the conversion
		Configuration conf = new Configuration();
	    FileSystem fs = FileSystem.get(new URI(dataPath), conf);
	    FileStatus[] files = fs.listStatus(new Path(dataPath));		
		if (files == null) {	System.out.println("directory not found!");}		
		else {
			System.out.println("directory found!");
		   for (FileStatus child : files) {
			   

			   // Load the file and create dataset
			   Dataset<Row> df = spark.read().format("text").load(child.getPath().toString());
		// Splitting into 3 columns:
		Column split_col = functions.split(df.col("value"), " ");
		df = df.withColumn("Subject", split_col.getItem(0));
		df = df.withColumn("Predicate", split_col.getItem(1));
		df = df.withColumn("Object", split_col.getItem(2));		
		df = df.drop("value");
		df = df.filter(df.col("Subject").notEqual("<>"));  
		triples_processed+=df.count();
		files_processed+=1;
		System.out.println("Processing File:"+files_processed);
		// Create a dataFrame<"Subject",[["predicate","object"],[],[],[]]> 
		Dataset<Row> df_agg =  df.withColumn("Values", functions.struct(df.col("Predicate"), df.col("Object")))
						   .groupBy("Subject")
			   .agg(functions.collect_list("Values").alias("Values"));

				
		// Accumulate Subject vertices and all the edges from forEach on dataFrame<"Subject",[["predicate","object"],[],[],[]]> into vl and el		

		// THE SUBJECT PASS 
		df_agg.foreach(new ForeachFunction<Row>() {
			public void call(Row r){
				try {
				HashMap v_hm = new HashMap(); // vertex HashMap
				// POPULATE THE TWO HASHMAPS 		
				String sub = r.getString(0);
				List tuples = r.getList(1);
				String[] temp_tuple = new String[3];
				temp_tuple[0] = sub;
				temp_tuple[0] = temp_tuple[0].substring(1,temp_tuple[0].length()-1); // get the label remove the leading and trailing <>
				v_hm.put("label",temp_tuple[0]); // add that subject in v_hm;
 			    for(Object O : tuples){ // iterate through all predicate,object tuples 
					  Row R = (Row)O; // contains a [predicate,object]
				      temp_tuple[2] = R.getString(1); // Object		      
					  if(temp_tuple[2].charAt(0)== '"' ) { // Object is a literal
						 // Add property to vertex 	
						  properties_count.add(1);
						  temp_tuple[1] = R.getString(0); // Predicate
					      temp_tuple[1]= temp_tuple[1].substring(1,temp_tuple[1].length()-1); // Predicate filter...remove <>			   
						  temp_tuple[2] = temp_tuple[2].substring(1,temp_tuple[2].lastIndexOf('"')); 
						  if(!v_hm.containsKey(temp_tuple[1])) {v_hm.put(temp_tuple[1],new ArrayList<String>());} // There can be multiple Objects for same subject predicate pair
		 				  ArrayList a = (ArrayList)v_hm.get(temp_tuple[1]);
		 				  a.add(temp_tuple[2]);	
						  
					  }
					  else {
							HashMap e_hm = new HashMap(); // edge hashmap						  
						 // Add an edge to e_hm
						    temp_tuple[1] = R.getString(0); // Predicate
					        temp_tuple[1]=temp_tuple[1].substring(1,temp_tuple[1].length()-1); // Predicate filter...remove <>					  
					        if(temp_tuple[2].charAt(0)!='_') {temp_tuple[2] = temp_tuple[2].substring(1,temp_tuple[2].length()-1);}								
					        else {temp_tuple[2] = temp_tuple[2].substring(2,temp_tuple[2].length()-1);} // Blank node ... starts with _: thus substring from index 2					        						      
							e_hm.put("label",temp_tuple[1]); // pred
							e_hm.put("in",temp_tuple[2]); // object
							e_hm.put("out",temp_tuple[0]); // subject
							edgeList.add(e_hm); // after each edge added need to append to accumulator list
					  }
					  
 			    }	
 			    vertexList.add(v_hm); // vertex hm gets accumulated only after parsing the entire row 
			}catch(Exception e){
				properties_count.add(1);
				System.out.println("Error in processing file's row in Subject pass:" + e.toString());
				}
			}
		}
		);
		
		// Obtain Object df by df.distinct("Object")
		df_agg = df.dropDuplicates("Object");
		Dataset<Row> df_cols = df_agg.select("Object");
		
		// Accumulate object vertices into another vertex Accumulator -- vol	
		df_cols.foreach(new ForeachFunction<Row>() {
			public void call(Row r){
				try {
				HashMap v_hm = new HashMap(); // vertex HashMap
				String obj = r.getString(0);
				if(obj.charAt(0)!='"') { // Only if object is not a literal
			        if(obj.charAt(0)!='_') {obj = obj.substring(1,obj.length()-1);} // means it's an IRI								
			        else {obj = obj.substring(2,obj.length()-1);} // Blank node ... starts with _: thus substring from index 2					        						      
			        v_hm.put("label",obj);
			        vertexList.add(v_hm);
				}
				}catch(Exception e){
					System.out.println("Error in processing file's row in Object pass:" + e.toString());
					}
			}
		});							
	}
		}
		
		// Write the log and lists to a file
		Instant end = Instant.now();
		Duration elapsed_time = Duration.between(start,end);

		// Write Logs:
	Configuration configuration1 = new Configuration();
	FileSystem hdfs1 = FileSystem.get( new URI( writePath ), configuration1 );
	Path file1 = new Path(writePath + "/RDF2PG_log.txt");
	if ( hdfs1.exists( file1 )) { hdfs1.delete( file1, true ); } 
	OutputStream os1 = hdfs1.create(file1);
	BufferedWriter br = new BufferedWriter( new OutputStreamWriter( os1, "UTF-8" ) );
	StringBuilder fileContent = new StringBuilder();	     
    fileContent.append("Execution Time:" + elapsed_time + "\n");
    fileContent.append("Edges:" + edgeList.value().size() + "\n");
    fileContent.append("Vertices:" + vertexList.value().size() + "\n");
    fileContent.append("Attribute Triples/Properties :" + properties_count.value() + "\n");
    fileContent.append("Triples Processed:" + triples_processed + "\n");	    
    fileContent.append("Conversion is Accurate!" + "triples_processed = #edges(relationship triples)+#properties(attribute triples)"+ "\n");	    	    		
	br.write(fileContent.toString());
	br.close();
	hdfs1.close();									
	
	// Write the Lists to File:
	Configuration configuration = new Configuration();
	FileSystem hdfs = FileSystem.get( new URI( writePath ), configuration );
	Path file = new Path(writePath + "/PropertyGraph.serialized");
	if ( hdfs.exists( file )) { hdfs.delete( file, true ); } 
	OutputStream os = hdfs.create(file);
	ObjectOutputStream o = new ObjectOutputStream(os);
	// Write objects to file
	o.writeObject(edgeList.value());
	o.writeObject(vertexList.value());
	o.close();
	hdfs.close();				
	
	}
	}


