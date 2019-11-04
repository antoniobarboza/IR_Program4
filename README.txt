Program 4 installation: Tony and Bobby 

1. git clone https://github.com/antoniobarboza/IR_Program4.git

2. Add the data to the test200 found in src/main/java/
	http://trec-car.cs.unh.edu/datareleases/
	unzip 
3. From the project root folder:
	mvn clean install
	mvn clean compile assembly:single
	
*How to run the indexer: 
java -Xmx50g -cp target/IR_Program2-0.1-jar-with-dependencies.jar lucene.Indexer

*How to run the Searcher 
java -Xmx50g -cp target/IR_Program2-0.1-jar-with-dependencies.jar lucene.SearchFiles
	
All the ranking can be found in the src/main/java/output
