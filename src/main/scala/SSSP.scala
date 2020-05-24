import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.{Graph, GraphLoader, PartitionStrategy, VertexId}
import org.apache.spark.graphx.util.GraphGenerators

object SSSP {
  def main(args: Array[String]): Unit = {
    val sc =  new SparkContext(new SparkConf().setMaster("local").setAppName("sssp"))

    // Generate a graph with edge attributes containing distances
    // val graph: Graph[Long, Double] =
    //   GraphGenerators.logNormalGraph(sc, numVertices = 10).mapEdges(e => e.attr.toDouble)
    // graph.edges.foreach(println)
    // Pre-treat data
    val graph = GraphLoader.edgeListFile(sc, "/home/hadoop/data/web-Google.txt")
      .partitionBy(PartitionStrategy.RandomVertexCut)

    val sourceId: VertexId = 30 // The ultimate source
    // Initialize the graph such that all vertices except the root have distance infinity.
    val initialGraph = graph.mapVertices((id, _) =>
      if (id == sourceId) 0.0 else Double.PositiveInfinity)
    val sssp = initialGraph.pregel(Double.PositiveInfinity)(
      (id, dist, newDist) => math.min(dist, newDist), // Vertex Program
      triplet => {  // Send Message
        if (triplet.srcAttr + triplet.attr < triplet.dstAttr) {
          Iterator((triplet.dstId, triplet.srcAttr + triplet.attr))
        } else {
          Iterator.empty
        }
      },
      (a, b) => math.min(a, b) // Merge Message
    )
    println(sssp.vertices.collect.mkString("\n"))
  }
}