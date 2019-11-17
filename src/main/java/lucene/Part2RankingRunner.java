package lucene;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used to run the test data given in part 2 to create the ranking file
 * @author Bobby
 *
 */
public class Part2RankingRunner {
	public static void main(String [] args) throws IOException {
		ArrayList<HashMap<String, Float>> allScores = new ArrayList<HashMap<String, Float>>();
		//Create ranking function 1's list
		HashMap<String, Float> r1 = new HashMap<String, Float>();
		r1.put("D1", (float) 1);
		r1.put("D2", (float) (1.0/2));
		r1.put("D3", (float) (1.0/3));
		r1.put("D4", (float) (1.0/4));
		r1.put("D5", (float) (1.0/5));
		r1.put("D6", (float) (1.0/6));
		//Create ranking function 2's list
		HashMap<String, Float> r2 = new HashMap<String, Float>();
		r2.put("D2", (float) 1);
		r2.put("D5", (float) (1.0/2));
		r2.put("D6", (float) (1.0/3));
		r2.put("D7", (float) (1.0/4));
		r2.put("D8", (float) (1.0/5));
		r2.put("D9", (float) (1.0/6));
		r2.put("D10", (float) (1.0/7));
		r2.put("D11", (float) (1.0/8));
		//Create ranking function 3's list
		HashMap<String, Float> r3 = new HashMap<String, Float>();
		r3.put("D1", (float) 1);
		r3.put("D2", (float) (1.0/2));
		r3.put("D5", (float) (1.0/3));
		//Create ranking function 4's list
		HashMap<String, Float> r4 = new HashMap<String, Float>();
		r1.put("D1", (float) 1);
		r1.put("D2", (float) (1.0/2));
		r1.put("D8", (float) (1.0/3));
		r1.put("D10", (float) (1.0/4));
		r1.put("D12", (float) (1.0/5));
		
		//Create the relevancy hashmap
		HashMap<String, Integer> relevantDocs = new HashMap<String, Integer>();
		relevantDocs.put("D2", 1);
		relevantDocs.put("D3", 1);
		relevantDocs.put("D5", 1);
		
		//Add all of the ranks to the allScores arraylist in their numerical order
		allScores.add(r1);
		allScores.add(r2);
		allScores.add(r3);
		allScores.add(r4);
		
		//Create the writer to be used to create the ranklib file
		String path = "./src/main/java/ranking/TestRankLibFile.txt";
    	Files.deleteIfExists(Paths.get(path));
    	File rankOutput = new File(path);
    	rankOutput.createNewFile();
    	BufferedWriter writer = new BufferedWriter(new FileWriter(path));
    	
    	//Now call the search method that will generate the output file
    	SearcherRankLib.createRankLibFile("testData", writer, allScores, relevantDocs);
	}
}
