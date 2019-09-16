package analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * This class will take in the file output from trec_eval and calculate 
 * the mean and standard deviations of the data
 * @author Bobby Chisholm
 *
 */
class EvalCalc {
	//Paths to files that will be read
	private String filePath = "";
	public float calculateMean() {
		try {
	    	BufferedReader reader = new BufferedReader(new FileReader(filePath));
	    	String line = reader.readLine();
	    	while(line !=null) {
	    		line = reader.readLine();
	    	}
	    	reader.close();

		return 0;
	}
	
	public float calculateStandardDev() {
		return 0;
	}

}
