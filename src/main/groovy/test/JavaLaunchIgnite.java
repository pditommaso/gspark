package test;

import org.apache.ignite.Ignite;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteOutClosure;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.spark.JavaIgniteContext;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
/**
 *
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */
public class JavaLaunchIgnite {

    static public void main(String... args) {

        // -- spark context
        SparkConf sparkConf = new SparkConf().setAppName("Spark-Ignite");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);

        // -- ignite configuration
        IgniteOutClosure cfg = new IgniteOutClosure() {
            @Override
            public Object apply() {
                IgniteConfiguration cfg = new IgniteConfiguration();
                return cfg;
            }
        };

        // -- ignite context
        JavaIgniteContext<Integer,Integer> ic = new JavaIgniteContext<Integer,Integer>(sc, cfg, false);
        final Ignite ignite = ic.ignite();
        ignite
                .compute()
                .broadcast(new IgniteRunnable() {
                    @Override
                    public void run() {
                        System.out.println(">>> Hello Node: " + ignite.cluster().localNode().id());
            }
        });

        ic.close(true);
        System.out.println(">>> DONE");
    }

}
