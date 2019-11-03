package lucene;

import org.apache.lucene.search.similarities.BasicStats;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.similarities.SimilarityBase;

/**
 *
 * Creates a custom similarity/scoring function and returns it
 */
public class CustomSimilarity {
	private static double vocabLength;
	private static double totalTerms;
	/**
	 * Returns a custom scoring function
	 * 
	 * @param simName name of which similarity that we will be using, the initials
	 * @return a custom similarity based on selection, UL is default
	 */
	public static Similarity getSimilarity(String simName, double termsInVocab, double totalTermCount) {
		totalTerms = totalTermCount;
		vocabLength = termsInVocab;
		if(simName != null) simName = simName.toLowerCase();
		switch(simName) {
			case "ul":
				return getUL();
			case "ujm":
				return getUJM();
			case "uds":
				return getUDS();
			default:
				return getUL();
		
		}
    }
	
	/**
	 * This creates a similarity that is Unigram language model with Laplace smoothing  with alpha = 1
	 * @return unigram LM 
	 */
	private static Similarity getUL() {
		SimilarityBase ul = new SimilarityBase() {
        	/**
        	 * This is called for each term
        	 */
			@Override
            protected double score(BasicStats basicStats, double frequency, double docLength) {
        		double numerator = (double) frequency + 1;
        		double denominator = (double) docLength + vocabLength;
                return numerator/denominator;
            }

            @Override
            public String toString() {
                return "Unigram language model with Laplace smoothing";
            }
        };
        return ul;
	}
	
	/**
	 * Creates  similarity that is Unigram language model with Jelinek-Mercer smoothing with lambda = .9
	 * @return
	 */
	private static Similarity getUJM() {
		SimilarityBase ujm = new SimilarityBase() {
        	@Override
            protected double score(BasicStats basicStats, double frequency, double docLength) {
        		double numerator = (double) frequency;
        		double denominator = (double) docLength;
        		double pofT = basicStats.getTotalTermFreq();
        		double smooth = (1 - 0.9) * pofT;
                return 0.9*(numerator/denominator) + smooth;
            }

            @Override
            public String toString() {
                return "Unigram language model with Jelinek-Mercer smoothing";
            }
        };
        return ujm;
	}

	/**
	 * Creates a similarity that is Unigram language model with Dirichlet smoothing with mu = 1000
	 * @return
	 */
	private static Similarity getUDS() {
		SimilarityBase uds = new SimilarityBase() {
        	@Override
            protected double score(BasicStats basicStats, double frequency, double docLength) {
        		double pofT = basicStats.getTotalTermFreq();
        		double numerator = (double) frequency + (1000*pofT);
        		double denominator = (double) docLength + 1000;
        		
                return numerator/denominator;
            }

            @Override
            public String toString() {
                return "Unigram language model with Dirichlet smoothing";
            }
        };
        return uds;
	}

	/**
	 * Returns the name of the ranking function
	 */
	public static String getSimilarityName() {
		return "CustomSimilarity";
		
	}
}
