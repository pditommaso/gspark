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

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.api.java.function.PairFunction
import scala.Tuple2
/**
 * Transitive closure on a graph, implemented in Java.
 * Usage: JavaTC [slices]
 */
public final class GroovyTC {

  private static final int numEdges = 200
  private static final int numVertices = 100
  private static final Random rand = new Random(42)

  static List<Tuple2<Integer, Integer>> generateGraph() {
    def edges = new HashSet<Tuple2<Integer, Integer>>(numEdges)
    while (edges.size() < numEdges) {
      int from = rand.nextInt(numVertices)
      int to = rand.nextInt(numVertices)
      def e = new Tuple2<Integer, Integer>(from, to)
      if (from != to) {
        edges.add(e)
      }
    }
    return new ArrayList<Tuple2<Integer, Integer>>(edges)
  }

  public static void main(String[] args) {

    def sparkConf = new SparkConf().setAppName("GroovyHdfsLR")
    def sc = new JavaSparkContext(sparkConf)
    int slices = (args.length > 0) ? Integer.parseInt(args[0]): 2
    def tc = sc.parallelizePairs(generateGraph(), slices).cache()

    // Linear transitive closure: each round grows paths by one edge,
    // by joining the graph's edges with the already-discovered paths.
    // e.g. join the path (y, z) from the TC with the edge (x, y) from
    // the graph to obtain the path (x, z).

    // Because join() joins on keys, the edges are stored in reversed order.
    def edges = tc.mapToPair({ e-> new Tuple2<Integer, Integer>(e._2(), e._1()) } as PairFunction<Tuple2<Integer, Integer>, Integer, Integer>)

    final PAIR_FUNCTION = { Tuple2<Integer, Tuple2<Integer, Integer>> triple -> new Tuple2<Integer, Integer>(triple._2()._2(), triple._2()._1())} as PairFunction<Tuple2<Integer, Tuple2<Integer, Integer>>, Integer, Integer>
    long oldCount=-1
    long nextCount = tc.count()
    while (nextCount != oldCount) {
      oldCount = nextCount
      // Perform the join, obtaining an RDD of (y, (z, x)) pairs,
      // then project the result to obtain the new (x, z) paths.
      tc = tc.union(tc.join(edges).mapToPair(PAIR_FUNCTION)).distinct().cache()
      nextCount = tc.count()
    }

    println("TC has " + tc.count() + " edges.")
    sc.stop()
  }
}
