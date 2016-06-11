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

/* SimpleApp.groovy */
import org.apache.spark.api.java.*
import org.apache.spark.SparkConf
import org.apache.spark.api.java.function.Function

public class SimpleApp {

    public static void main(String[] args) {

        def logFile = "${System.getenv('SPARK_HOME')}/README.md" // Should be some file on your system
        def conf = new SparkConf().setAppName("Groovy Simple Application")
        def sc = new JavaSparkContext(conf)
        def logData = sc.textFile(logFile).cache()

        long numAs = logData.filter( { it.contains("a") } as  Function<String, Boolean>).count()

        long numBs = logData.filter( { it.contains("b") } as Function<String, Boolean> ).count()

        println("Lines with a: " + numAs + ", lines with b: " + numBs)
  }

}
