/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.examples

import groovy.transform.CompileStatic
import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.api.java.function.Function
import org.apache.spark.api.java.function.Function2
/**
 * Computes an approximation to pi
 * Usage: JavaSparkPi [slices]
 */
@CompileStatic
public final class GroovySparkPi {

  public static void main(String[] args) throws Exception {
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

    println("Pi is roughly " + 4.0 * count / n)

    jsc.stop()
  }
}
