# Spark Groovy

Sandbox testing Groovy based Spark applications


## Quickstart 

Download and unzip [Spark](http://spark.apache.org/downloads.html) in a directory of your choice, 
then set the `SPARK_HOME` directory (tested with Spark 1.6.3).

Clone this repo: 

    git clone https://github.com/pditommaso/spark-groovy.git
    cd spark-groovy
    
    
Compile the examples:

    ./gradlew shadowJar
    
    
Launch the GroovyWordCount: 

    $SPARK_HOME/bin/spark-submit \
      --class "org.apache.spark.examples.GroovyWordCount" \
      build/libs/gspark-1.0-all.jar \
      README.md
    
   
## Deploy jobs on a local cluster 

Launch the Spark master: 

    $SPARK_HOME/sbin/start-master.sh
    
Launch a Spark worker: 

    $SPARK_HOME/sbin/start-slave.sh spark://$HOSTNAME:7077 -c 1 -m 1G
    
Run the Groovy PI example: 

    $SPARK_HOME/bin/spark-submit \
      --class org.apache.spark.examples.GroovySparkPi \
      --master spark://$HOSTNAME:7077 \
      build/libs/gspark-1.0-all.jar       
   
Check the Spark daskbord opening the browser at the address [http://localhost:8080/](http://localhost:8080/)     
      
Stop master and slave instances: 

	$SPARK_HOME/sbin/stop-slave.sh && $SPARK_HOME/sbin/stop-master.sh
     
               
    
## Deploy Ignite application over a Spark cluster

     
Launch the Ignite example: 
     
    $SPARK_HOME/bin/spark-submit \
        --class org.apache.ignite.examples.GroovyIgniteSimpleApp \
        --master spark://$HOSTNAME:7077 \
        build/libs/gspark-1.0-all.jar 