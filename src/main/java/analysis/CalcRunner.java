package analysis;

/**
 * This class will find the mean and standard deviations of the 3 required analysis for 
 * part 6 of program 2
 * @author Bobby
 *
 */
public class CalcRunner {
	public static void main(String [] args) {
		String[] defaultFilePaths = {
				"./src/main/java/analysis/defaultMap.txt",
				"./src/main/java/analysis/defaultNdcg.txt",
				"./src/main/java/analysis/defaultRprec.txt"
		};
		
		String[] ulFilePaths = {
				"./src/main/java/analysis/ULcustomMap.txt",
				"./src/main/java/analysis/ULcustomNdcg.txt",
				"./src/main/java/analysis/ULcustomRprec.txt"
		};
		
		String[] udsFilePaths = {
				"./src/main/java/analysis/UDScustomMap.txt",
				"./src/main/java/analysis/UDScustomNdcg.txt",
				"./src/main/java/analysis/UDScustomRprec.txt"
		};
		
		String[] ujmFilePaths = {
				"./src/main/java/analysis/UJMcustomMap.txt",
				"./src/main/java/analysis/UJMcustomNdcg.txt",
				"./src/main/java/analysis/UJMcustomRprec.txt"
		};
		
		
		System.out.println("Defaults file results: ");
		runAnalysis(defaultFilePaths);
		System.out.println("\n\nU-L file results: ");
		runAnalysis(ulFilePaths);
		System.out.println("\n\nU-JM file results: ");
		runAnalysis(ujmFilePaths);
		System.out.println("\n\nU-DS file results: ");
		runAnalysis(udsFilePaths);
		
	}
	
	private static void runAnalysis(String[] filePaths) {
		for(String file: filePaths) {
			EvalCalc calc = new EvalCalc(file);
			Float mean = calc.calculateMean();
			Float standDev = calc.calculateStandardDev();
			Float standError = calc.calculateStandardError();
			System.out.println("Results for file: " + file);
			System.out.println("Mean: " + mean + "\nStandard Deviation: " + standDev + "\nStandard Error: " + standError + "\n");
		}
	}
}
