package lucene;

import org.apache.lucene.search.similarities.BasicStats;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.similarities.SimilarityBase;

/**
 *
 * Creates a custom similarity/scoring function and returns it
 */
public class CustomSimilarity {
	/**
	 * Returns a custom scoring function
	 * 
	 * @param simName name of which similarity that we will be using, the initials
	 * @return a custom similarity based on selection, UL is default
	 */
	public static Similarity getSimilarity(String simName) {
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
        	@Override
            protected double score(BasicStats basicStats, double frequency, double docLength) {
        		
                return 1.0;
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
        		
                return 1.0;
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
        		
                return 1.0;
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
