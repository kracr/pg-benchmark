import java.io.FileNotFoundException; 
import java.io.PrintWriter; 
import java.util.LinkedHashMap; 
import java.util.Map; 
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.*;
import org.eclipse.lyo.validation.*;

import java.util.List;
import org.eclipse.rdf4j.RDF4JException;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.repository.util.*;
import org.eclipse.rdf4j.model.*;

import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.nativerdf.NativeStore;

import org.eclipse.rdf4j.query.*;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.rio.RDFFormat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;

import java.io.*;
import java.util.*;
import java.util.LinkedHashMap; 
import java.util.Map; 
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.rdf4j.repository.manager.LocalRepositoryManager;

import org.eclipse.rdf4j.sail.memory.MemoryStore;
import java.util.Arrays; 
import java.util.stream.IntStream;

import org.eclipse.rdf4j.rio.turtle.TurtleParser;
public class RDFtoPGConverter{
	
	 static List<String> vertices = new ArrayList<String>(); 
	 static JSONObject jo = new JSONObject();
	 static JSONArray va = new JSONArray();
	 static JSONArray ea = new JSONArray();
	 
	public static void addVertex(Statement st,int c){
		Map m =new LinkedHashMap(2);
	    m.put("id",c+1);
	    m.put("vname", st.getSubject().toString());
	    va.add(m); 
	}
	
	public static void addEdge(Statement st, int c) {
		Map m =new LinkedHashMap(4);
		m.put("id", c+1);
		m.put("type",st.getPredicate().toString());
	    m.put("out",st.getSubject().toString());
	    m.put("in", st.getObject().toString());
	    ea.add(m); 
	}
	
	public static void addVertexProperty(Statement st,int i) {
		String pred = st.getPredicate().toString();
		int index = pred.lastIndexOf("/");
		String edgename = pred.substring(index+1, pred.length());
		ObjectMapper oMapper = new ObjectMapper();
		Map<String, String> m1 = oMapper.convertValue(va.get(i), Map.class);
		Map m = new LinkedHashMap(1);
		m.put(edgename,st.getObject().toString());
		m1.putAll(m);
		va.remove(i);
		va.add(i, m1);
	}
	
	public static void writeToJSON() throws FileNotFoundException{
		PrintWriter pw = new PrintWriter("C:\\Users\\MEENAKSHI\\Desktop\\JSONExample.json");
		jo.put("edges", ea);
		jo.put("vertices", va);
	    pw.write(jo.toJSONString());
		pw.flush();
		pw.close();
	}

	public static void main(String[] args) throws IOException  {
	
		File initialFile = new File("F:\\Mtech\\Semester 3\\Capstone Project\\Sample data\\sp2b.n3");
	    InputStream inputStream = new FileInputStream(initialFile);	
		String baseURI = initialFile.toString();
		RDFFormat format = RDFFormat.TURTLE;
		
		try (GraphQueryResult res = QueryResults.parseGraphBackground(inputStream, baseURI, format)) {
			int count=0;
			while (res.hasNext()) {
		    Statement st = res.next();
		    
		    if(vertices.contains(st.getSubject().toString())==false) {
		    	addVertex(st,count);
	    		vertices.add(st.getSubject().toString());
	    		count++;
		    	}
		    
		    if(st.getObject() instanceof Literal) {
		    		int index = vertices.indexOf(st.getSubject().toString());
		    		addVertexProperty(st,index);
		    }
		    else {
		    	if(vertices.contains(st.getObject().toString())==false) {
		    		addVertex(st,count);
		    		vertices.add(st.getObject().toString());
		    		count++;
		    	}
		    	addEdge(st,count);
		    	count++;
		    }
		 }
			writeToJSON();
		}
		catch (RDF4JException e) {
		  // handle unrecoverable error
		}
		finally {
		  inputStream.close();
		}


	}
	
}
