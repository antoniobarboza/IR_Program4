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
        simName = simName.toLowerCase();
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
	
	//Comments will be added and these names will be changed to be more accurate
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
