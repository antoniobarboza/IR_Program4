Program 5 installation: Tony and Bobby 

Rank Lib installation: 
Download the file from the My courses link: https://mycourses.unh.edu/courses/60222/assignments/410828
put it in the root of the project directory. 
command used to produce the ranking for part 2: 
java -jar RankLib.jar -train src/main/java/ranking/TestRankLibFeatureVectorFile.txt -ranker 4 -qrel src/main/java/test200/test200-train/train.pages.cbor-article.qrels -save model.txt

1. git clone https://github.com/antoniobarboza/IR_Program4.git

2. Add the data to the test200 found in src/main/java/
	http://trec-car.cs.unh.edu/datareleases/
	unzip 
3. From the project root folder:
	mvn clean install
	mvn clean compile assembly:single
	
*How to run the indexer: 
java -Xmx50g -cp target/IR_Program2-0.1-jar-with-dependencies.jar lucene.Indexer

*How to run the Searcher - part 2
java -Xmx50g -cp target/IR_Program2-0.1-jar-with-dependencies.jar lucene.Part2RankingRunner
	
Part3: 
java -Xmx50g -cp target/IR_Program2-0.1-jar-with-dependencies.jar lucene.SearcherRankLib

The ranking directory contains the files feature ranking files 

I made a bash script called produceInput.sh
-> This produces all the txt files needed to use the EvalCalculator needed to calculate standard error and deviation. 
	all these files then are used by CalcRunner.java which produces the standard dev, standard error. 
	All this is contained in the analysis folder down src/main/java
	
