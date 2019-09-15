package lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;

import edu.unh.cs.treccar_v2.Data.Page;
import edu.unh.cs.treccar_v2.read_data.DeserializeData;

public class PrecisionatR {
	
	public PrecisionatR() {

	}

	public static void main(String[] args) {
		calculatePrecisionR();
	}
	
	public static int calculatePrecisionR() {
		// This is the main function to run the PrecisionatR
	    String defaultRankOutputPath = "./src/main/java/output/DefaultRankingOutput.txt";
	    String customRankOutputPath = "./src/main/java/output/CustomRankingOutput.txt";
		//First read the qrel file -> shows which paragraphs are relevant and not relevant
	    //Taken from args[1], if empty defaults to 
	    String qrelFilePath;
	    //if(args.length > 1) qrelFilePath = args[1];
	    //else
	    qrelFilePath = "./src/main/java/data/test200/test200-train/train.pages.cbor-article.qrels";
	    
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
	    		String[] arrayLine = line.split(" ");
	    		//System.out.println(line);
	    		
	    		if (relevanceMap.containsKey(arrayLine[0])){
	    			//increase the count by one if relevant
	    			if (Integer.parseInt(arrayLine[3]) == 1) {
	    				int currentCount = relevanceMap.get(arrayLine[0]);
	    				relevanceMap.replace(arrayLine[0], currentCount + 1);
	    				Map<String, Integer> tempMap = docMap.get(arrayLine[0]);
	    				tempMap.put(arrayLine[2], 0);
	    			}
	    		}else {
	    			//first time query exist create spot in hashmap
	    			if (Integer.parseInt(arrayLine[3]) == 1) {
	    				relevanceMap.put(arrayLine[0],1);
	    				
	    				HashMap<String, Integer> tempMap = new HashMap<String, Integer>();
	    				tempMap.put(arrayLine[2], 0);
	    				docMap.put(arrayLine[0], tempMap);
	    			}
	    		}
	    		line = reader.readLine();
	    	}
	    	reader.close();
	    	// at this point R should be stored in the Hashmap for each querry
	    	// Create an array of each query's P value
	    	BufferedReader readerDefault = new BufferedReader(new FileReader(defaultRankOutputPath));
	    	System.out.println(returnArray( readerDefault, relevanceMap, docMap ));
	    	
	    	
	    } 
	    catch (Exception e) {
	    	System.out.println("Error found!:");
	    	e.printStackTrace();
	    }
	    return 0;
	}
	private static HashMap<String, Float> returnArray( BufferedReader outputFile, Map<String, Integer> relevanceMap, HashMap<String, Map<String, Integer>> docMap ) {
	    HashMap<String, Float> precisionMap = new HashMap<String, Float>();
	    
	    //go line by line in our ranking file (looping R times for the query and calculating the P value)
	    try {
	    	String line = outputFile.readLine();
	    	String currentQuery = null;
	    	int rValue = 0;
	    	int rCount = 0;
	    	int tpValue = 0;
	    	while(line !=null) {
	    		//Loop each line
	    		String[] arrayLine = line.split(" ");
	    		String queryID = arrayLine[0];
	    		
	    		if( currentQuery == null ) { 
	    			currentQuery = queryID;
	    		}
	    		rValue = relevanceMap.get(arrayLine[0]); 
	    		//System.out.println("RValue: " + rValue);
	    		//System.out.println("RCount: " +rCount);
	    		//System.out.println("queryID: " +queryID);
	    		//System.out.println("current: " +currentQuery);
	    		
	    		if ( (rCount == rValue) || (!queryID.equals(currentQuery))) {
	    			if ( !queryID.equals(currentQuery)) {
	    				currentQuery = queryID;
	    			}
	    			//Calculate the Precision, store in Hashmap
	    			//Reset the values 
	    			Float precision = (float)tpValue/(float)rValue;
	    			precisionMap.put(arrayLine[0], precision);
	    			while (line != null && (queryID.equals(currentQuery))) {
	    				//loop lines until its different 
	    				line = outputFile.readLine();
	    				if(line == null) break;
	    				
	    				arrayLine = line.split(" ");
	    				queryID = arrayLine[0];
	    			}
	    			currentQuery = queryID;
	    			rCount = 0; 
	    			tpValue = 0;
	    		}
	    		else { 
	    			//Check if the doc Id exist 
	    			String rankedDocID = arrayLine[2];
	    			Map<String, Integer> tempDocMap = docMap.get(arrayLine[0]);
	    			if ( tempDocMap.containsKey(rankedDocID)) {
	    				tpValue++; 
	    			}
	    			System.out.println("HIT");	
	    			//increment every loop
	    			
	    			rCount++; 
	    			line = outputFile.readLine();
	    			if(line == null) break;
	    		}
	    	}
	    } catch (Exception e) {
	    	System.out.println("Error found!:");
	    	e.printStackTrace();
	    }
	    
	    return precisionMap;
	}

}
