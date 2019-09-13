package lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import edu.unh.cs.treccar_v2.Data.Page;
import edu.unh.cs.treccar_v2.read_data.DeserializeData;

public class PrecisionatR {
	
	

	public static void main(String[] args) {
		// This is the main function to run the PrecisionatR
	    String defaultRankOutputPath = "./src/main/java/output/DefaultRankingOutput.txt";
	    String customRankOutputPath = "./src/main/java/output/CustomRankingOutput.txt";
		//First read the qrel file -> shows which paragraphs are relevant and not relevant
	    //Taken from args[1], if empty defaults to 
	    String qrelFilePath;
	    if(args.length > 1) qrelFilePath = args[1];
	    else qrelFilePath = "./src/main/java/data/test200-train/train.pages.cbor-article.entity.qrels";
	    
	    //Map that maps query -> Rvalue
	    Map<String, Integer> relevanceMap = new HashMap<String, Integer>();
	    
	    //Map that keys a query -> Map<docID, int>
	    HashMap<String, Map<String, Integer>> docMap = new HashMap<String, Map<String, Integer>>();
	    //open the file and scan line by line and record the queryID and the number of relevant times
	    try {
	    	BufferedReader reader = new BufferedReader(new FileReader(qrelFilePath));
	    	String line = reader.readLine();
	    	while(line !=null) {
	    		//Loop each line
	    		String[] arrayLine = line.split("\\s+");
	    		//System.out.println(Arrays.toString(arrayLine));
	    		
	    		if (relevanceMap.containsKey(arrayLine[0])){
	    			//increase the count by one if relevant
	    			if (Integer.parseInt(arrayLine[3]) == 1) {
	    				int currentCount = relevanceMap.get(arrayLine[0]);
	    				relevanceMap.replace(arrayLine[0], currentCount + 1);
	    			}
	    		}else {
	    			//first time query exist create spot in hashmap
	    			if (Integer.parseInt(arrayLine[3]) == 1) {
	    				relevanceMap.put(arrayLine[0],1);
	    			}
	    		}
	    		line = reader.readLine();
	    	}
	    	reader.close();
	    	// at this point R should be stored in the Hashmap for each querry
	    	// Create an array of each query's P value
	    	BufferedReader readerDefault = new BufferedReader(new FileReader(defaultRankOutputPath));
	    	//returnArray( readerDefault );
	    	//System.out.println(relevanceMap.toString());
	    	
	    } 
	    catch (Exception e) {
	    	System.out.println("Error found!:");
	    	e.printStackTrace();
	    }

	}
	private static HashMap<String, Integer> returnArray( BufferedReader outputFile, Map<String, Integer> relevanceMap ) {
	    HashMap<String, Integer> queryMap = new HashMap<String, Integer>();
	    
	    //go line by line in our ranking file (looping R times for the query and calculating the P value)
	    try {
	    	String line = outputFile.readLine();
	    	while(line !=null) {
	    		//Loop each line
	    		String[] arrayLine = line.split("\\s+");
	    		String queryID = arrayLine[0];
	    		if ( !relevanceMap.containsKey(arrayLine[0])) {
	    			//skip line 
	    			line = outputFile.readLine();
	    			continue;
	    		}
	    		for ( int i =0; i < relevanceMap.get(arrayLine[0]); i++)
	    		line = outputFile.readLine();
	    	}
	    } catch (Exception e) {
	    	System.out.println("Error found!:");
	    	e.printStackTrace();
	    }
	    
	    
	    
	    return queryMap;
	}

}
