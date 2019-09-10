/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import edu.unh.cs.treccar_v2.Data.Page;
import edu.unh.cs.treccar_v2.read_data.DeserializeData;

/** Simple command-line based search demo. */
public class SearchFiles {
	
  private SearchFiles() {}

  /**
   * Runs the search using an input file that is converted to pages and used as queries
   * 
   * @param args args[0] is path to index args[1] path to input file
   * @throws Exception if file opening fails or deserializeData fails
   */
  public static void main(String[] args) throws Exception {
    //This is a directory to the index
    String indexPath = "./src/main/java/index";
    
    //Taken from args[0], if empty defaults to 
    String inputFilePath = "./src/main/java/data/test200-train/train.pages.cbor-outlines.cbor";
    
    //Convert the input file into an iteratable of pages to query
    File pageQueries = new File(inputFilePath);
    FileInputStream fileStream = new FileInputStream(pageQueries);
    Iterable<Page> pagesForDefaultRanks = DeserializeData.iterableAnnotations(fileStream);
    ArrayList<Page> pagesForCustomRanks = new ArrayList<Page>();
    
    
    try {
    	//indicate that the output is being written to a file
    	System.out.println("Searching pages with default ranking function...");
    	//runs the searches with the default rankings
    	for(Page page: pagesForDefaultRanks) {
    		pagesForCustomRanks.add(page);
    		runSearchWithDefaultRank(page, indexPath);
    	}
    	//All default ranked searches are done
    	System.out.println("Default Ranking done! Written to file: $filename");
    	
    	//Search using custom ranking function
    	System.out.println("Searching pages with custom searching function...");
    	for(Page page: pagesForCustomRanks) {
    		runSearch(page, indexPath, CustomSimilarity.getSimilarity());
    	}
    	System.out.println("Custom ranking done! Written to file $filename");
    } catch(Exception e) {
    	e.printStackTrace();
    }
    
  }
  
  /**
   * Runs the search with the default similarity/ranking
   * @param page page to be searched
   * @param indexPath path to index folder
   * @throws Exception Thrown if parsing input file or opening index fails
   */
  private static void runSearchWithDefaultRank(Page page, String indexPath) throws Exception {
	  runSearch(page, indexPath, new BM25Similarity());
  }
  
  /**
   * This method runs a search using the default ranking
   * @param page
   * @param indexPath
   * @throws Exception
   */
  private static void runSearch(Page page, String indexPath, Similarity similarity) throws Exception {
	    //convert page to search terms
	  	String queryId = page.getPageId();
	  	String queryString = page.getPageName().toString();
	  	
	    Directory dir = FSDirectory.open(Paths.get(indexPath));
	    IndexReader reader = DirectoryReader.open(dir);
	    IndexSearcher searcher = new IndexSearcher(reader);
	    searcher.setSimilarity(similarity);
	    
	    //This sets up the query
	    Analyzer analyzer = new StandardAnalyzer();
	    QueryParser queryParser = new QueryParser("text", analyzer);
	    //Query query = queryParser.parse(QueryParser.escape(queryString));
	    Query query = queryParser.parse(queryString);
	    
	    //This initiates the search and returns top 10
	    //System.out.println("STARTING RETREVAl: " + query.toString());
	    TopDocs searchResult = searcher.search(query,100);
	    ScoreDoc[] hits = searchResult.scoreDocs;
	    
	    //System.out.println("Results found: " + searchResult.totalHits);
	    
	    //If there are no results
	    if (hits.length == 0) {
	        System.out.println("No result found for: " + queryString);   	
	    }
	    else {
	    	System.out.println("Results for query: " + queryString);
	    }
	    System.out.println("Results for query: " + queryString);
	    for (int j=0; j < hits.length; j++ ) {
	    	Document document = searcher.doc(hits[j].doc);
	    	float score = hits[j].score;
	    	String paraId = document.get("id");
	    	String paraBody = document.get("text").toString();
	    	System.out.println("\t$" + queryId + " $" + paraId + " $" + j + " $" + hits[j].score + "$Team9-$" + similarity.toString());
	    }
  }
}

  