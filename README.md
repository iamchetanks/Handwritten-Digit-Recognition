# Handwritten-Digit-Recognition

Google DataProc Spark Scala Job for MNIST Handwritten Digit Recogintion using Decision Trees.

## Requirements:

Spark 2.2.0
Scala 2.11.12
Google Cloud Environment

## Dependencies 

spark-core 2.11
spark-mllib 2.11
spark-sql 2.11

## Steps:

1. Create Scala Project.
2. Prepare Jar file.
3. Create Cluster in Google Cloud dataproc. (I used 3 nodes)
4. Upload train data, test data, jar file to the Google Cloud Storage Bucket.
5. Create spark job by providing path to the data files and jar files.
6. Submit the job.

