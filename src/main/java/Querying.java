
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

public class Querying {
	
	public static String readFileAsString(String fileName)throws Exception 
	  { 
	    String data = ""; 
	    data = new String(Files.readAllBytes(Paths.get(fileName))); 
	    return data; 
	  } 

	public static void main(String[] args) {
	
		File dataDir = new File("F:\\Mtech\\Semester 3\\Capstone Project\\MyRepository");
		Repository repo = new SailRepository(new NativeStore(dataDir));
		repo.initialize();
		File file = new File("F:\\Mtech\\Semester 3\\Capstone Project\\Sample data\\log_sample.ttl");
		String baseURI = "http://example.org/example/local";
		try {
			BufferedWriter writer = new BufferedWriter(
					new FileWriter("C:\\Users\\MEENAKSHI\\Desktop\\Query_results.txt"));
			//PrintStream out = new PrintStream("C:\\Users\\MEENAKSHI\\Desktop\\Query_results.txt");
		
		   RepositoryConnection con = repo.getConnection();
		   con.add(file, baseURI, RDFFormat.TURTLE);
		   
		   String queryString = "select distinct ?p { \r\n" + 
		   		"  ?s ?p ?literal \r\n" + 
		   		"  filter isLiteral(?literal)\r\n" + 
		   		"}";
		   //String queryString = readFileAsString("C:\\Users\\MEENAKSHI\\Desktop\\MT18081\\Query Files\\Query9.txt"); 
		   
		   TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
		   try (TupleQueryResult result = tupleQuery.evaluate()) {
			   List<String> bindingNames = result.getBindingNames();
			      while (result.hasNext()) {
			    	   BindingSet bindingSet = result.next();
			    	   	 System.out.print(bindingSet.getValue(bindingNames.get(0)).toString());
						 writer.write(bindingSet.getValue(bindingNames.get(0)).toString());
						 writer.write("\n");
			    	   System.out.println();
			      }
			      writer.close();
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
