#!/bin/bash
# A Script that will produce the data needed for analysis
echo "Producing Files for Default Rank"
touch ./src/main/java/analysis/defaultRprec.txt
touch ./src/main/java/analysis/defaultMap.txt
touch ./src/main/java/analysis/defaultNdcg.txt
 
./trec_eval.9.0/trec_eval -m Rprec ./src/main/java/test200/test200-train/train.pages.cbor-article.qrels ./src/main/java/output/DefaultRankingOutput.txt -q > src/main/java/analysis/defaultRprec.txt

./trec_eval.9.0/trec_eval -m map ./src/main/java/test200/test200-train/train.pages.cbor-article.qrels src/main/java/output/DefaultRankingOutput.txt -q > src/main/java/analysis/defaultMap.txt

./trec_eval.9.0/trec_eval -m ndcg_cut.20 ./src/main/java/test200/test200-train/train.pages.cbor-article.qrels ./src/main/java/output/DefaultRankingOutput.txt -q > src/main/java/analysis/defaultNdcg.txt

echo "Producing files for Custom Rank UL"

touch ./src/main/java/analysis/ULcustomRprec.txt
touch ./src/main/java/analysis/ULcustomMap.txt
touch ./src/main/java/analysis/ULcustomNdcg.txt

./trec_eval.9.0/trec_eval -m Rprec ./src/main/java/test200/test200-train/train.pages.cbor-article.qrels ./src/main/java/output/ULCustomRankingOutput.txt  -q > ./src/main/java/analysis/ULcustomRprec.txt

./trec_eval.9.0/trec_eval -m map ./src/main/java/test200/test200-train/train.pages.cbor-article.qrels ./src/main/java/output/ULCustomRankingOutput.txt  -q > ./src/main/java/analysis/ULcustomMap.txt

./trec_eval.9.0/trec_eval -m ndcg_cut.20 ./src/main/java/test200/test200-train/train.pages.cbor-article.qrels ./src/main/java/output/ULCustomRankingOutput.txt  -q > ./src/main/java/analysis/ULcustomNdcg.txt

echo "Producing files for Custom Rank UJM"
touch ./src/main/java/analysis/UJMcustomRprec.txt
touch ./src/main/java/analysis/UJMcustomMap.txt
touch ./src/main/java/analysis/UJMcustomNdcg.txt

./trec_eval.9.0/trec_eval -m Rprec ./src/main/java/test200/test200-train/train.pages.cbor-article.qrels ./src/main/java/output/UJMCustomRankingOutput.txt  -q > ./src/main/java/analysis/UJMcustomRprec.txt

./trec_eval.9.0/trec_eval -m map ./src/main/java/test200/test200-train/train.pages.cbor-article.qrels ./src/main/java/output/UJMCustomRankingOutput.txt  -q > ./src/main/java/analysis/UJMcustomMap.txt

./trec_eval.9.0/trec_eval -m ndcg_cut.20 ./src/main/java/test200/test200-train/train.pages.cbor-article.qrels ./src/main/java/output/UJMCustomRankingOutput.txt  -q > ./src/main/java/analysis/UJMcustomNdcg.txt

echo "Producing files for Custom Rank UDS"
touch ./src/main/java/analysis/UDScustomRprec.txt
touch ./src/main/java/analysis/UDScustomMap.txt
touch ./src/main/java/analysis/UDScustomNdcg.txt

./trec_eval.9.0/trec_eval -m Rprec ./src/main/java/test200/test200-train/train.pages.cbor-article.qrels ./src/main/java/output/UDSCustomRankingOutput.txt  -q > ./src/main/java/analysis/UDScustomRprec.txt

./trec_eval.9.0/trec_eval -m map ./src/main/java/test200/test200-train/train.pages.cbor-article.qrels ./src/main/java/output/UDSCustomRankingOutput.txt  -q > ./src/main/java/analysis/UDScustomMap.txt

./trec_eval.9.0/trec_eval -m ndcg_cut.20 ./src/main/java/test200/test200-train/train.pages.cbor-article.qrels ./src/main/java/output/UDSCustomRankingOutput.txt  -q > ./src/main/java/analysis/UDScustomNdcg.txt


echo "DONE>>>"
