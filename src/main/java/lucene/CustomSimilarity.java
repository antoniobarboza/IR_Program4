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
	 * @return a custom similarity
	 */
	public static Similarity getSimilarity() {
        SimilarityBase mySimilarity = new SimilarityBase() {
            @Override
            protected float score(BasicStats basicStats, float v, float v1) {
                float sum1 = 0.0f;
                sum1 += v;
                return sum1;
            }

            @Override
            public String toString() {
                return "Custom ranking function";
            }
        };
        return mySimilarity;
    }
}
