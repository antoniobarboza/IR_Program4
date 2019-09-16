Installation Notes
Part 1 run instructions
Download the TREC Complex Answer Retrieval “test200“ dataset from http://trec-car.cs.unh.edu/ and unpack and place it into src/main/java/data

*To run Maven build*
mvn clean install

*Compile
mvn clean compile assembly:single

NOTE:  You can specify an index as a command line argument but if you don’t it will be written                          to the default directory. Default index: src/main/java/index
*How to run the indexer: 
java -Xmx50g -cp target/IR_program2-0.1-jar-with-dependencies.jar lucene.Indexer

NOTE: First command line argument is path to index, second is path to input file, if no arguments it will use default index: src/main/java/index and the file: train.pages.cbor-outlines.cbor. The output files can be found in src/main/java/output
*How to run the searcher:
java -Xmx50g -cp target/IR_program2-0.1-jar-with-dependencies.jar lucene.SearchFiles



Part 2 run instructions 
Put the Trec_eval 9.0 version tool in the base directory of the project. 
Cd trec_eval.90 
Compile the program using make 
Run the command: 
./trec_eval -m Rprec -m map -m ndcg_cut ../src/main/java/data/test200/test200-train/train.pages.cbor-article.qrels ../src/main/java/output/DefaultRankingOutput.txt 
And: 
./trec_eval -m Rprec -m map -m ndcg_cut ../src/main/java/data/test200/test200-train/train.pages.cbor-article.qrels ../src/main/java/output/CustomRankingOutput.txt

*Note ndcg_20 would not work so this prints out all ndcg values!


Part 3
*To run the Prescision at R file run:
java -Xmx50g -cp target/IR_program2-0.1-jar-with-dependencies.jar lucene.PrecisionatR

Output: 
Custom Rank Output Precision at R: 0.5260762
Default Rank Output Precision at R: 0.5963763

*Our result is .0003 off from the trec_eval score. After hours of evaluating our precision at R we came to the conclusion it must be a rounding difference. 
