package util

import kotlin.math.max

object Graphs {
    fun floydWarshall(edges: List<List<Int>>, n: Int): List<List<Int>> {
        val distances = List(n) { MutableList(n) { Int.MAX_VALUE } }
        for ((a, b, w) in edges) {
            distances[a][b] = w
        }
        for (i in 0 until n) {
            distances[i][i] = 0
        }
        for (k in 0 until n) {
            for (i in 0 until n) {
                for (j in 0 until n) {
                    if (distances[i][k] == Int.MAX_VALUE || distances[k][j] == Int.MAX_VALUE)
                        continue
                    distances[i][j] = max(distances[i][j], distances[i][k] + distances[k][j])
                }
            }
        }
        return distances
    }

    fun djikstra(graph: List<List<Pair<Int, Int>>>, source: Int): Pair<List<Int>, List<Int>> {
        val vertices = graph.indices.toMutableSet()
        val distances = MutableList(graph.size) { Int.MAX_VALUE }
        val previous = MutableList(graph.size) { -1 }
        distances[source] = 0

        while (vertices.isNotEmpty()) {
            val node = distances.withIndex().minByOrNull { it.value }!!.index
            vertices.remove(node)
            for ((nei, weight) in graph[node]) {
                val new = distances[node] + weight
                if (new < distances[nei]) {
                    distances[nei] = new
                    previous[nei] = node
                }
            }
        }
        return distances to previous
    }
}