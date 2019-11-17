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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import edu.unh.cs.treccar_v2.Data.Page;
import edu.unh.cs.treccar_v2.read_data.DeserializeData;


public class NewSearcher {
	
  NewSearcher() {}

  /**
   * Runs the search using an input file that is converted to pages and used as queries
   * 
   * @param args args[0] is path to index args[1] path to input file, if no args passed both set to default
   * @throws Exception if file opening fails or deserializeData fails
   */
  public static void main(String[] args) throws Exception {
	ArrayList<Similarity> ranks = new ArrayList<Similarity>();
	String query = "This is a temporary query";
    //This is a directory to the index, args[0] or default
    String indexPath;
    if(args.length > 0) indexPath = args[0];
    else  indexPath = "./src/main/java/index";
    
    //Taken from args[1], if empty defaults to 
    String inputFilePath;
    if(args.length > 1) inputFilePath = args[1];
    else inputFilePath = "./src/main/java/test200/test200-train/train.pages.cbor-outlines.cbor";
    
    //Paths to the 4 output files
    
    String defaultRankOutputPath = "./src/main/java/output/DefaultRankingOutput.txt";
    
    
    //Convert the input file into an iteratable of pages to query
    File pageQueries = new File(inputFilePath);
    FileInputStream fileStream = new FileInputStream(pageQueries);
    Iterable<Page> pagesForDefaultRanks = DeserializeData.iterableAnnotations(fileStream);
    
    //create indexReader
    Directory dir = FSDirectory.open(Paths.get(indexPath));
    IndexReader reader = DirectoryReader.open(dir);
    
    try {
    	
    	//indicate that the output is being written to a file
    	System.out.println("Searching pages using different ranking functions...");
    	
    	//create the arraylist to hold all doc scores
    	ArrayList<HashMap<String, Float>> allScores = new ArrayList<HashMap<String, Float>>();
    	//runs the searches with the default rankings
    	for(Similarity rank: ranks) {
    		//need to create a file and a writer for each ranking function
    		//Delete the output files if they exist already
    		String filePath = "./src/main/java/output/" + rank.getClass().getSimpleName().toString() + ".txt";
        	Files.deleteIfExists(Paths.get(filePath));
        	
        	//Create the files to be written to
        	File defaultRankOutputFile = new File(filePath);
        	defaultRankOutputFile.createNewFile();
        	//Create the file writers
        	
        	BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        	
        	//make an array list of the hashmaps returned to construct the rank files
    		allScores.add(runSearch(query, reader, writer, rank));
    		
    		//close writer
    		writer.close();
    	}
    	//create the ranklib files
    	
    	
    	//All default ranked searches are done
    	System.out.println("All ranking done! Output files are found in folder: src/main/java/output");
    	
    } catch(Exception e) {
    	e.printStackTrace();
    }
    
  }
  
  /**
   * This method runs a search using the default ranking
   * @param page the page being used to search
   * @param indexPath the path to the index directory
   * @param similarityName the name of the similarity function being used
   * @throws Exception
   */
  private static HashMap<String, Float> runSearch(String queryString, IndexReader reader, BufferedWriter writer, Similarity similarity) throws Exception {
	    //HashMap is used to have the doc id's with scores
	    HashMap<String, Float> docsWithScores = new HashMap<String, Float>();
	    IndexSearcher searcher = new IndexSearcher(reader);
	    searcher.setSimilarity(similarity);
	    
	    //This sets up the query
	    Analyzer analyzer = new StandardAnalyzer();
	    QueryParser queryParser = new QueryParser("text", analyzer);
	    //Query query = queryParser.parse(QueryParser.escape(queryString));
	    Query query = queryParser.parse(queryString);
	    
	    //This initiates the search and returns top 100
	    TopDocs searchResult = searcher.search(query,100);
	    ScoreDoc[] hits = searchResult.scoreDocs;
	    
	    //System.out.println("Results found: " + searchResult.totalHits);
	    
	    //If there are no results
	    if (hits.length == 0) {
	        return docsWithScores;
	    }
	    
	    for (int j=0; j < hits.length; j++ ) {
	    	Document document = searcher.doc(hits[j].doc);
	    	float score = hits[j].score;
	    	String paraId = document.get("id");
	    	writer.write(queryString + " Q0 " + paraId + " " + j + " " + score + " Team11-" + similarity.getClass().getSimpleName().toString() + "\n");
	    	//creates rank lib file formats
	    	docsWithScores.put(paraId, score);
	    }
	    return docsWithScores;
	   //writer.write("\n\n");
  }
  
  /**
   * All of the ranking methods with scores are taken in and a ranklib file is created
   * 
   * @param allScores array list of all of the docs returned by a ranking function and their scores
   * @param relevantDocs docID and a 1 if they are relevant
 * @throws IOException 
   */
  private static void createRankLibFile(String query, BufferedWriter writer, ArrayList<HashMap<String, Float>> allScores, HashMap<String, Integer> relevantDocs) throws IOException {
	  //This hashmap is used for the R vectors, docId -> list of scores in order for each method
	  HashMap<String, ArrayList<Float>> docsWithScores = new HashMap<String, ArrayList<Float>>();
	  int numRankings = allScores.size();
	  //This loops through all ranking functions
	  for(int i = 0; i < numRankings; i++) {
		  //get the docID's and scores for this ranking function
		  HashMap<String, Float> scores = allScores.get(i);
		  //this loops through the docID's of each ranking function
		  while(!scores.isEmpty()) {
			  //This is the docId that will be searched in all of the maps, then removed and repeat until this map is empty
			  String thisDoc = (String) scores.keySet().toArray()[0];
			  //Write relevancy and qid to ranklib file
			  writer.write(relevantDocs.get(thisDoc) + " qid:" + query + " ");
			  ArrayList<Float> scoresForThisDoc = new ArrayList<Float>();
			  for(int j = 0; j < numRankings; j++) {
				  Float scoreForDoc = allScores.get(j).get(thisDoc);
				  if(scoreForDoc == null) scoreForDoc = (float) 0;
				  scoresForThisDoc.add(scoreForDoc);
				  writer.write((j+1) + ":" + scoreForDoc + " ");
				  if(scoreForDoc != 0) allScores.get(j).remove(thisDoc, scoreForDoc);
			  }
			  docsWithScores.put(thisDoc, scoresForThisDoc);
			  //write the docID
			  writer.write("# " + thisDoc + "\n");
		  }
	  }
  }
}
