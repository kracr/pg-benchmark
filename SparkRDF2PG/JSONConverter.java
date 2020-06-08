import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JSONConverter {
	List<HashMap> edgeList;
	List<HashMap> vertexList;
	String readPath;
	String writePath;
	JSONConverter(String s, String s1){
		readPath=s;
		writePath=s1;
	}
	public void load() {
		try {
		FileInputStream fi = new FileInputStream(new File(readPath+ "PropertyGraph.serialized"));
		ObjectInputStream oi = new ObjectInputStream(fi);
		// Read objects
		edgeList = (List<HashMap>) oi.readObject();
		vertexList = (List<HashMap>) oi.readObject();
		}catch (Exception e) {System.out.println(e.getMessage());}
	}
	void convert(){
		Instant start = Instant.now();		
		Gson gson = new Gson();
		JsonElement element = gson.toJsonTree(edgeList, new TypeToken<List<HashMap>>() {}.getType()); // Convert List to JSON Element
		JsonArray edgeArray = element.getAsJsonArray(); // Convert the obtained JSON element to JSON Array
		element = gson.toJsonTree(vertexList, new TypeToken<List<HashMap>>() {}.getType());
		JsonArray vertexArray = element.getAsJsonArray();
		// JSON Object
		JsonObject jo = new JsonObject();
		jo.add("EdgeList" , edgeArray);
		jo.add("VertexList" , vertexArray);	
		Instant end = Instant.now();
		Duration elapsed_time = Duration.between(start,end);		
		// Write JSON Object to file
		try {
		    StringBuilder fileContent = new StringBuilder();	     
		    fileContent.append(jo.toString());
		    System.out.println("Writing JSON");		    
		    FileWriter fileWriter = new FileWriter(writePath+"PropertyGraph.json");		
		    fileWriter.write(fileContent.toString());
		    fileWriter.close();}catch (Exception E){System.out.println(E.toString());}				
		// JSON_log.text -> time elapsed in conversion + Each JSON array length
		try {
		    StringBuilder content = new StringBuilder();	     
		    content.append("Conversion Time: " + elapsed_time + "\n");
		    content.append("Edge Array: " + ((JsonArray)jo.get("EdgeList")).size() + "\n");
		    content.append("Vertex array: " + ((JsonArray)jo.get("VertexList")).size() + "\n");		    
		    FileWriter f = new FileWriter(writePath + "JSON_log.text");		    
		    f.write(content.toString());
		    f.close();}catch (Exception E){System.out.println(E.toString());}								
	}
	public static void main(String[] args) {
		JSONConverter m = new JSONConverter(args[0],args[1]);
		m.load();
		System.out.println("loading done"); 
		m.convert();
		System.out.println("converison done");		
	}
}