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
		
		String[] customFilePaths = {
				"./src/main/java/analysis/customMap.txt",
				"./src/main/java/analysis/customNdcg.txt",
				"./src/main/java/analysis/customRprec.txt"
		};
		
		System.out.println("Defaults file results: ");
		runAnalysis(defaultFilePaths);
		System.out.println("\n\nCustom file results: ");
		runAnalysis(customFilePaths);
		
	}
	
	private static void runAnalysis(String[] filePaths) {
		for(String file: filePaths) {
			EvalCalc calc = new EvalCalc(file);
			Float mean = calc.calculateMean();
			Float standDev = calc.calculateStandardDev();
			System.out.println("Results for file: " + file);
			System.out.println("Mean: " + mean + "\tStandard Deviation: " + standDev + "\n");
		}
	}
}
