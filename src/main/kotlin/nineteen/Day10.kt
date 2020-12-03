package nineteen

import util.Coordinates.Cartesian2D
import util.Coordinates.Polar
import util.Resources
import kotlin.math.PI

object Day10 {
    fun part1(asteroids: List<Cartesian2D>): Int {
        val bestSpot = asteroids.maxByOrNull { getObservableAsteroids(it, asteroids).size }!!
        return getObservableAsteroids(bestSpot, asteroids).size
    }

    fun part2(asteroids: List<Cartesian2D>): Int {
        val bestSpot = asteroids.maxByOrNull { getObservableAsteroids(it, asteroids).size }!!
        val seen = getObservableAsteroids(bestSpot, asteroids)
            .toList()
            .map { it.second }
            .sortedBy { it.first().theta }
            .onEach { polars -> polars.sortBy { p -> p.r } }
        var i = seen.binarySearchBy(PI / 2) { it.first().theta }
        if (i < 0)
            i = -(i + 1)
        repeat(199) {
            seen[i].removeFirst()
            i = (i + 1) % seen.size
            while (seen[i].isEmpty()) {
                i = (i + 1) % seen.size
            }
        }
        val next = bestSpot - seen[i].removeFirst().toCartesian()
        return next.x * 100 + next.y
    }


    private fun getObservableAsteroids(
        pms: Cartesian2D,
        asteroids: List<Cartesian2D>
    ): MutableMap<Double, MutableList<Polar>> {
        val seen = mutableMapOf<Double, MutableList<Polar>>()
        for (other in asteroids) {
            if (pms == other)
                continue
            val polar = (pms - other).toPolar()
            if (!seen.contains(polar.theta))
                seen[polar.theta] = mutableListOf()
            seen[polar.theta]?.add(polar)
        }
        return seen
    }
}

fun main() {
    val input = Resources.readFileAsList("nineteen/day10.txt").map { it.toCharArray() }
    val asteroids = mutableListOf<Cartesian2D>()
    for (y in input.indices) {
        for (x in input[y].indices) {
            if (input[y][x] == '#') asteroids.add(Cartesian2D(x, y))
        }
    }

    println(Day10.part1(asteroids))
    println(Day10.part2(asteroids))
}