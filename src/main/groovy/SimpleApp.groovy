/* SimpleApp.groovy */
import org.apache.spark.api.java.*
import org.apache.spark.SparkConf
import org.apache.spark.api.java.function.Function

public class SimpleApp {

    public static void main(String[] args) {

        def logFile = "${System.getenv('SPARK_HOME')}/README.md" // Should be some file on your system
        def conf = new SparkConf().setAppName("Simple Application")
        def sc = new JavaSparkContext(conf)
        def logData = sc.textFile(logFile).cache()

        long numAs = logData.filter( { it.contains("a") } as  Function<String, Boolean>).count()

        long numBs = logData.filter( { it.contains("b") } as Function<String, Boolean> ).count()

        println("Lines with a: " + numAs + ", lines with b: " + numBs)
  }

}
