package org.apache.spark.examples

import groovy.transform.BaseScript
import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.api.java.function.Function
import org.apache.spark.api.java.function.Function2

@BaseScript SerializableScript baseScript


/**
 *
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */

def sparkConf = new SparkConf().setAppName("GroovySparkPi")
def jsc = new JavaSparkContext(sparkConf)

int slices = (args.length == 1) ? Integer.parseInt(args[0]) : 2
int n = 100000 * slices
List<Integer> l = new ArrayList<Integer>(n)
for (int i = 0; i < n; i++) {
    l.add(i)
}

def dataSet = jsc.parallelize(l, slices)

def mapper = {
    double x = Math.random() * 2 - 1
    double y = Math.random() * 2 - 1
    return (x * x + y * y < 1) ? 1 : 0
}

int count = dataSet
        .map(mapper as Function<Integer, Integer>)
        .reduce({int a, int b -> a+b} as Function2<Integer, Integer, Integer>)

println("Pi is roughly ${4.0 * count / n}")

jsc.stop()