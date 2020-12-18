package twenty

import util.MyMath.product
import util.Resources

object Day17 {
    fun conwayCubes(activated: Set<List<Int>>, dimensions: Int): Int {
        val deltas = product(listOf(-1, 0, 1), count = dimensions).toMutableList()
        deltas.remove(List(dimensions) { 0 })
        var prev = activated
        repeat(6) { _ ->
            val numNeighbors = mutableMapOf<List<Int>, Int>()
            for (coord in prev) {
                for (delta in deltas) {
                    val neighbor = (coord zip delta).map { (a, b) -> a + b }
                    numNeighbors[neighbor] = numNeighbors.getOrDefault(neighbor, 0) + 1
                }
            }
            val new = mutableSetOf<List<Int>>()
            for ((coord, count) in numNeighbors) {
                if ((prev.contains(coord) && count in 2..3) || (!prev.contains(coord) && count == 3))
                    new.add(coord)
            }
            prev = new
        }
        return prev.size
    }
}

fun main() {
    val input = Resources.readFileAsList("input.txt")
    val activated = mutableSetOf<List<Int>>()
    val dimensions = 4
    for (y in input.indices) {
        for (x in input[y].indices) {
            if (input[y][x] == '#')
                activated.add(listOf(0, 0, x, y)) // z, w, x, y
        }
    }
    // For part 1, just set dimensions = 3 and only add z
    // Part 2
    println(Day17.conwayCubes(activated, dimensions))
}