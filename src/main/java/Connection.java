
import java.util.List;
import org.eclipse.rdf4j.RDF4JException;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;

import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.rio.RDFFormat;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.File;
import org.eclipse.rdf4j.repository.manager.LocalRepositoryManager;

import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

public class Connection {

	public static void main(String[] args) {
	
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