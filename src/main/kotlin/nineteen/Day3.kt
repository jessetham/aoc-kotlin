package nineteen

import util.Coordinates.Cartesian2D
import util.Resources
import java.lang.Exception
import kotlin.math.absoluteValue

object Day3 {
    fun part1and2(path1: List<String>, path2: List<String>) {
        val visited1 = traverse(path1)
        val visited2 = traverse(path2)

        val minManhattan =
            (visited1.keys intersect visited2.keys).map { it.x.absoluteValue + it.y.absoluteValue }.minOrNull()
        println("Part 1: $minManhattan")

        val minSteps =
            (visited1.keys intersect visited2.keys).map { visited1.getValue(it) + visited2.getValue(it) }.minOrNull()
        println("Part 2: $minSteps")
    }

    private fun traverse(instructions: List<String>): Map<Cartesian2D, Int> {
        var position = Cartesian2D(0, 0)
        val visited = mutableMapOf<Cartesian2D, Int>()
        var steps = 0
        for (path in instructions) {
            val distance = path.substring(1).toInt()
            val delta = when (path[0]) {
                'U' -> Cartesian2D(0, 1)
                'D' -> Cartesian2D(0, -1)
                'L' -> Cartesian2D(-1, 0)
                'R' -> Cartesian2D(1, 0)
                else -> throw Exception("unknown direction")
            }
            repeat(distance) {
                position = position + delta
                steps++
                visited.putIfAbsent(position.copy(), steps)
            }
        }
        return visited
    }
}

fun main() {
    val input = Resources.readFileAsList("nineteen/day3.txt")

    Day3.part1and2(input[0].split(","), input[1].split(","))
}