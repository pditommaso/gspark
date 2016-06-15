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

    $SPARK_HOME/bin/spark-submit \
      --class "org.apache.spark.examples.GroovyWordCount" \
      build/libs/spark-groovy-1.0.jar README.md
    
   
## Deploy jobs on a local cluster 

Launch the Spark master: 

    $SPARK_HOME/sbin/start-master.sh
    
Launch a Spark worker: 

    $SPARK_HOME/sbin/start-slave.sh spark://$HOSTNAME:7077 -c 1 -m 1G
    
Run the Groovy PI example: 

    $SPARK_HOME/bin/spark-submit \
      --class org.apache.spark.examples.GroovySparkPi \
      --master spark://$HOSTNAME:7077 \
      build/libs/spark-groovy-1.0.jar       
   
Check the Spark daskbord opening the browser at the address [http://localhost:8080/](http://localhost:8080/)     
      
Stop master and slave instances: 

	$SPARK_HOME/sbin/stop-slave.sh && $SPARK_HOME/sbin/stop-master.sh
     
               
    