package org.apache.spark.examples
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


import java.util.regex.Pattern

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.api.java.function.FlatMapFunction
import org.apache.spark.api.java.function.Function2
import org.apache.spark.api.java.function.PairFunction
import scala.Tuple2

public final class GroovyWordCount {

  private static final Pattern SPACE = Pattern.compile(" ")

  public static void main(String[] args) throws Exception {

    if (args.length < 1) {
      println("Usage: GroovyWordCount <file>")
      System.exit(1)
    }

    def sparkConf = new SparkConf().setAppName("GroovyWordCount")
    def ctx = new JavaSparkContext(sparkConf)
    def lines = ctx.textFile(args[0], 1)

    def words = lines.flatMap( { Arrays.asList(SPACE.split(it)) } as FlatMapFunction<String, String> )

    def ones = words.mapToPair({ new Tuple2<String, Integer>(it, 1) } as PairFunction<String, String, Integer>)

    def counts = ones.reduceByKey({ Integer i1, Integer i2 -> i1 + i2 } as Function2<Integer, Integer, Integer>)

    counts.collect().each { tuple ->
        println("${tuple._1()}: ${tuple._2()}")
    }
    ctx.stop();
  }
}
