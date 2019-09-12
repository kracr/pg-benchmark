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

import java.io.*;
import java.util.*;
import java.io.File;
import org.eclipse.rdf4j.repository.manager.LocalRepositoryManager;

import org.eclipse.rdf4j.sail.memory.MemoryStore;
public class RDFConverter {
	
	static File file = new File("F:\\Mtech\\Semester 3\\Capstone Project\\Sample data\\log_sample.ttl");
	static String baseURI = "http://example.org/example/local";
	
	public static String readFileAsString(String fileName)throws Exception 
	  { 
	    String data = ""; 
	    data = new String(Files.readAllBytes(Paths.get(fileName))); 
	    return data; 
	  } 
	
	public static void addEdges() {
		String queryString = "select ?literal where { \r\n" + 
		   		"  ?s ?p ?literal \r\n" + 
		   		"  filter isLiteral(?literal)\r\n" + 
		   		"}";
	}
	
	public static ArrayList<String> addVertexProperty(String sub, RepositoryConnection c) throws IOException{
	String queryString = "select ?literal where { \r\n" + 
			   		"  <"  + sub +"> ?p ?literal \r\n" + 
			   		"  filter isLiteral(?literal)\r\n" + 
			   		"}";
		c.add(file, baseURI, RDFFormat.TURTLE);
		int count=0,i;
		ArrayList<String> literal_value = new ArrayList<String>();
		TupleQuery tupleQuery = c.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
		try (TupleQueryResult result = tupleQuery.evaluate()) {
			   List<String> bindingNames = result.getBindingNames();
			      while (result.hasNext()) {
			    	   BindingSet bindingSet = result.next();
			    	   literal_value.add(bindingSet.getValue(bindingNames.get(0)).toString());
			    	   count++;
			      }
			      return literal_value;

		   }
		   catch (RDF4JException e) {
			   System.out.println("Ex=" + e);
			   return literal_value;
		   }
	}

	public static void main(String[] args) throws FileNotFoundException  {
	
		File dataDir = new File("F:\\Mtech\\Semester 3\\Capstone Project\\MyRepository");
		Repository repo = new SailRepository(new NativeStore(dataDir));
		repo.initialize();
		try {
		
		   RepositoryConnection con = repo.getConnection();
		   con.add(file, baseURI, RDFFormat.TURTLE);
		   
		   String queryString = "select distinct ?s { \r\n" + 
		   		"  ?s ?p ?literal \r\n" + 
		   		"  filter isLiteral(?literal)\r\n" + 
		   		"}";
		   int count=0,i;
		   ArrayList<String> subject = new ArrayList<String>();
		   ArrayList<String> literal_value = new ArrayList<String>();
		   TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
		   try (TupleQueryResult result = tupleQuery.evaluate()) {
			   List<String> bindingNames = result.getBindingNames();
			      while (result.hasNext()) {
			    	   BindingSet bindingSet = result.next();
			    	   	 System.out.print(bindingSet.getValue(bindingNames.get(0)).toString());
			    	   	 subject.add(bindingSet.getValue(bindingNames.get(0)).toString());
						 count++;
			    	   System.out.println();
			      }
			      System.out.println(count);
			      PrintWriter pw = new PrintWriter("C:\\Users\\MEENAKSHI\\Desktop\\JSONExample.json"); 
			      for(i=0;i<count;i++) {
			    	  
			    	  JSONObject jo = new JSONObject();
			    	  //jo.put("graphType", "Property Graph"); 
			    	  JSONArray ja = new JSONArray(); 
			    	  for(i=0;i<count;i++) {
			    		  Map m = new LinkedHashMap(4);
			    		  m.put("id",i);
			    		  m.put("transaction_no", subject.get(i));
			    		  literal_value=addVertexProperty(subject.get(i),con);
			    		  m.put("processDateTime",literal_value.get(0));
			    		  m.put("statusCode", literal_value.get(1));
			    		  ja.add(m);
			      		}
			    	  jo.put("vertices", ja);
			    	  pw.write(jo.toJSONString());
			      }
			      pw.flush(); 
			      pw.close();
		   }

		   catch (RDF4JException e) {
			   System.out.println("Ex=" + e);
		   }
		catch(Exception e) {
			System.out.println("Ex=" + e);
		}

	}
	catch(Exception e) {
		System.out.println("Ex=" + e);
		}
	}
}

