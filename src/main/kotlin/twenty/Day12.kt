package twenty

import util.Coordinates.Cartesian2D
import util.Resources
import kotlin.math.absoluteValue

object Day12 {
    fun travelWithGuessedInstructions(input: List<Pair<Char, Int>>): Int {
        var position = Cartesian2D(0, 0)
        var direction = Cartesian2D(1, 0)
        for ((d, m) in input) {
            when (d) {
                'N' -> position = Cartesian2D(position.x, position.y + m)
                'S' -> position = Cartesian2D(position.x, position.y - m)
                'E' -> position = Cartesian2D(position.x + m, position.y)
                'W' -> position = Cartesian2D(position.x - m, position.y)
                'L' -> repeat(m / 90) { direction = Cartesian2D(-direction.y, direction.x) }
                'R' -> repeat(m / 90) { direction = Cartesian2D(direction.y, -direction.x) }
                'F' -> position = Cartesian2D(position.x + direction.x * m, position.y + direction.y * m)
            }
        }
        return position.x.absoluteValue + position.y.absoluteValue
    }

    fun travelWithActualInstructions(input: List<Pair<Char, Int>>): Int {
        var position = Cartesian2D(0, 0)
        var waypoint = Cartesian2D(10, 1)
        for ((d, m) in input) {
            when (d) {
                'N' -> waypoint = Cartesian2D(waypoint.x, waypoint.y + m)
                'S' -> waypoint = Cartesian2D(waypoint.x, waypoint.y - m)
                'E' -> waypoint = Cartesian2D(waypoint.x + m, waypoint.y)
                'W' -> waypoint = Cartesian2D(waypoint.x - m, waypoint.y)
                'L' -> repeat(m / 90) { waypoint = Cartesian2D(-waypoint.y, waypoint.x) }
                'R' -> repeat(m / 90) { waypoint = Cartesian2D(waypoint.y, -waypoint.x) }
                'F' -> position = Cartesian2D(position.x + waypoint.x * m, position.y + waypoint.y * m)
            }
        }
        return position.x.absoluteValue + position.y.absoluteValue
    }
}

fun main() {
    val input = Resources.readFileAsList("input.txt").map { it[0] to it.substring(1).toInt() }

    // Part 1sc
    println(Day12.travelWithGuessedInstructions(input))
    // Part 2
    println(Day12.travelWithActualInstructions(input))
}