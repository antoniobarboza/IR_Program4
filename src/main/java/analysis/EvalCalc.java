package analysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class will take in the file output from trec_eval and calculate 
 * the mean and standard deviations of the data
 * @author Bobby Chisholm
 *
 */
class EvalCalc {
	private String filePath;
	private Float mean;
	/**
	 * Constructor that creates a calculator with a file path
	 * @param filePath path to file to be used
	 */
	public EvalCalc(String filePath) {
		this.filePath = filePath;
	}
	
	//Paths to files that will be read
	public float calculateMean() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
	    	String line = reader.readLine();
	    	line = line.replaceAll("\\s+", " ");
	    	String[] arrayLine = line.split(" ");
	    	float sum = 0;
	    	float count = 0;
	    	while(line != null && !arrayLine[1].equals("all")) {
	    		sum += Float.parseFloat(arrayLine[arrayLine.length-1]);
	    		count++;
	    		//Get next line
	    		line = reader.readLine();
		    	line = line.replaceAll("\\s+", " ");
		    	arrayLine = line.split(" ");
	    	}
	    	reader.close();

	    	this.mean = sum/count;
			return this.mean;
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public float calculateStandardDev() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
	    	String line = reader.readLine();
	    	line = line.replaceAll("\\s+", " ");
	    	String[] arrayLine = line.split(" ");
	    	float numerator = 0;
	    	float count = 0;
	    	while(line != null && !arrayLine[1].equals("all")) {
	    		numerator += Math.pow(Float.parseFloat(arrayLine[arrayLine.length-1]) - this.mean, 2);
	    		count++;
	    		//get next line
	    		line = reader.readLine();
		    	line = line.replaceAll("\\s+", " ");
		    	arrayLine = line.split(" ");
	    	}
	    	reader.close();
	    	//calculate the standard deviation
			return (float) (Math.sqrt(numerator/(count-1)) / Math.sqrt(count));
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
