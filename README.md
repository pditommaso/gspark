# Spark Groovy

Sandbox testing Groovy based Spark applications


## Quickstart 

Download and unzip [Spark](http://spark.apache.org/downloads.html) in a directory of your choice, 
then set the `SPARK_HOME` directory.

Clone this repo: 

    git clone https://github.com/pditommaso/spark-groovy.git
    cd spark-groovy
    
    
Compile the examples:

    ./gradlew jar 
    
    
Launch the GroovyWordCount: 

    $SPARK_HOME/bin/spark-submit  --class "org.apache.spark.examples.GroovyWordCount" build/libs/spark-groovy-1.0.jar README.md