#!/bin/bash
# A Script that will produce the data needed for analysis
echo "Producing Files for Default Rank"
touch ./src/main/java/analysis/defaultRprec.txt
touch ./src/main/java/analysis/defaultMap.txt
touch ./src/main/java/analysis/defaultNdcg.txt
 
./trec_eval.9.0/trec_eval -m Rprec ./src/main/java/data/test200/test200-train/train.pages.cbor-article.qrels ./src/main/java/output/DefaultRankingOutput.txt -q > src/main/java/analysis/defaultRprec.txt

./trec_eval.9.0/trec_eval -m map ./src/main/java/data/test200/test200-train/train.pages.cbor-article.qrels src/main/java/output/DefaultRankingOutput.txt -q > src/main/java/analysis/defaultMap.txt

./trec_eval.9.0/trec_eval -m ndcg_cut.20 ./src/main/java/data/test200/test200-train/train.pages.cbor-article.qrels ./src/main/java/output/DefaultRankingOutput.txt -q > src/main/java/analysis/defaultNdcg.txt
/Program2/trec_eval.9.0
echo "Producing files for Custom Rank"
touch ./src/main/java/analysis/customRprec.txt
touch ./src/main/java/analysis/customMap.txt
touch ./src/main/java/analysis/customNdcg.txt

./trec_eval.9.0/trec_eval -m Rprec ./src/main/java/data/test200/test200-train/train.pages.cbor-article.qrels ./src/main/java/output/CustomRankingOutput.txt  -q > ./src/main/java/analysis/customRprec.txt

./trec_eval.9.0/trec_eval -m map ./src/main/java/data/test200/test200-train/train.pages.cbor-article.qrels ./src/main/java/output/CustomRankingOutput.txt  -q > ./src/main/java/analysis/customMap.txt

./trec_eval.9.0/trec_eval -m ndcg_cut.20 ./src/main/java/data/test200/test200-train/train.pages.cbor-article.qrels ./src/main/java/output/CustomRankingOutput.txt  -q > ./src/main/java/analysis/customNdcg.txt

echo "DONE>>>"
