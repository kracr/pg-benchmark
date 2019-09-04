import java.io.FileNotFoundException; 
import java.io.PrintWriter; 
import java.util.LinkedHashMap; 
import java.util.Map; 
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 

import java.nio.file.*;
import java.io.PrintStream;
import org.eclipse.lyo.validation.*;

import java.util.List;
import org.eclipse.rdf4j.RDF4JException;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.repository.util.*;

import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.nativerdf.NativeStore;

import org.eclipse.rdf4j.query.*;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.rio.RDFFormat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;

import java.io.File;
import org.eclipse.rdf4j.repository.manager.LocalRepositoryManager;

import org.eclipse.rdf4j.sail.memory.MemoryStore;
public class RDFConverter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File dataDir = new File("F:\\Mtech\\Semester 3\\Capstone Project\\MyRepository");
		Repository repo = new SailRepository(new MemoryStore());
		repo.initialize(); 
		File file = new File("F:\\Mtech\\Semester 3\\Capstone Project\\Sample data\\log_sample.ttl");
		String baseURI = "http://example.org/example/local";
		try {
			BufferedWriter writer = new BufferedWriter(
					new FileWriter("C:\\Users\\MEENAKSHI\\Desktop\\OutputFile.txt"));
		   RepositoryConnection con = repo.getConnection();
		   con.add(file, baseURI, RDFFormat.TURTLE);
		   long startTime = System.currentTimeMillis();
		   
		   String queryString = "select distinct ?literal { \r\n" + 
		   		"  ?s ?p ?literal \r\n" + 
		   		"  filter isLiteral(?literal)\r\n" + 
		   		"}";
		   TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
		   TupleQueryResult result = tupleQuery.evaluate();
		   JSONObject jo = new JSONObject(); 
	          
	        // putting data to JSONObject 
	        jo.put("firstName", "John"); 
	        jo.put("lastName", "Smith"); 
	        jo.put("age", 25); 
	          
	        Map m = new LinkedHashMap(4); 
	        m.put("streetAddress", "21 2nd Street"); 
	        m.put("city", "New York"); 
	        m.put("state", "NY"); 
	        m.put("postalCode", 10021); 
	           
	        jo.put("address", m);   
	        JSONArray ja = new JSONArray(); 
	          
	        m = new LinkedHashMap(2); 
	        m.put("type", "home"); 
	        m.put("number", "212 555-1234"); 
	           
	        ja.add(m); 
	          
	        m = new LinkedHashMap(2); 
	        m.put("type", "fax"); 
	        m.put("number", "212 555-1234"); 
	           
	        ja.add(m);  
	        jo.put("phoneNumbers", ja); 
	          
	        PrintWriter pw = new PrintWriter("JSONExample.json"); 
	        pw.write(jo.toJSONString()); 
	          
	        pw.flush(); 
	        pw.close(); 
		   try {
			   List<String> bindingNames = result.getBindingNames();
			   int i=0;
		      while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Value firstValue = bindingSet.getValue(bindingNames.get(0));
				i++;
		      }
				int count = i;
				System.out.println("Count = " + count);
				writer.write("Count: " + count);
				writer.write("\n");
			long endTime = System.currentTimeMillis();
			long totalTimeTaken = (endTime - startTime);
			writer.write("Total time for loading in milliseconds: " + totalTimeTaken);
			System.out.println(totalTimeTaken);
			writer.close();
		}
		catch (RDF4JException e) {
			System.out.println("Ex=" + e);
		}
		catch(java.io.IOException e) {
			System.out.println("Ex=" + e);
		}

}
		catch(Exception e) {
			System.out.println("Ex=" + e);
		}
	}
}

