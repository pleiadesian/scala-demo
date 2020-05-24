import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.{Graph, GraphLoader, VertexId, _}
import org.apache.spark.graphx.util.GraphGenerators
import org.apache.spark.sql.SparkSession

object PageRank {
  def main(args: Array[String]): Unit = {
    // Creates a SparkSession.
    val sc =  new SparkContext(new SparkConf().setMaster("local").setAppName("pagerank"))

    // Generate a graph with edge attributes containing distances
    // val graph: Graph[Long, Double] =
    //   GraphGenerators.logNormalGraph(sc, numVertices = 5).mapEdges(e => e.attr.toDouble)
    // graph.edges.foreach(println)
    // Pre-treat data, load the edges and partition the graph for triangle count
    val graph = GraphLoader.edgeListFile(sc, "/home/hadoop/data/web-Google.txt")
      .partitionBy(PartitionStrategy.RandomVertexCut)

    // Run PageRank
    val ranks = graph.pageRank(0.0001).vertices

    // Print the result
    println(ranks.collect().mkString("\n"))
  }
}
