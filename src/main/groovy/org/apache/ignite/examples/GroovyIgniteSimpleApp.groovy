package org.apache.ignite.examples

import groovy.transform.CompileStatic
import org.apache.ignite.Ignite
import org.apache.ignite.Ignition
import org.apache.ignite.configuration.IgniteConfiguration
import org.apache.ignite.lang.IgniteOutClosure
import org.apache.ignite.lang.IgniteRunnable
import org.apache.ignite.spark.JavaIgniteContext
import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext
/**
 *
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */
@CompileStatic
class GroovyIgniteSimpleApp {

    static void main(String... args) {

        // -- spark context
        def sparkConf = new SparkConf().setAppName("Spark-Ignite")
        def sc = new JavaSparkContext(sparkConf)

        // -- ignite configuration
        def cfg = new IgniteOutClosure() {
            @Override
            public Object apply() {
                IgniteConfiguration cfg = new IgniteConfiguration();
                return cfg;
            }
        }

        // -- ignite context
        def ic = new JavaIgniteContext<Integer,Integer>(sc, cfg, false);
        final Ignite ignite = ic.ignite();
        ignite .compute() .broadcast( { println">>> Hello Node: ${Ignition.ignite().cluster().localNode().id()}" } as IgniteRunnable )

        ic.close(true);
        println(">>> DONE");
    }

}
