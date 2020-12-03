package nineteen

import util.Coordinates.Cartesian3D
import util.Resources
import util.Math
import java.util.*
import kotlin.math.absoluteValue

object Day12 {
    fun part1(input: List<Cartesian3D>): Int {
        val positions = MutableList(input.size) { input[it].copy() }
        val velocities = MutableList(positions.size) { Cartesian3D(0, 0, 0) }

        repeat(1000) {
            step(positions, velocities)
        }

        return (positions zip velocities).map { (p, v) -> p.energy() * v.energy() }.sum()
    }

    fun part2(input: List<Cartesian3D>): Long {
        val positions = MutableList(input.size) { input[it].copy() }
        val velocities = MutableList(positions.size) { Cartesian3D(0, 0, 0) }
        val seens = MutableList(3) { mutableSetOf<String>() }
        val periods = MutableList(3) { 0L }
        var steps = 0L

        while (true) {
            val keys = MutableList(3) { "" }
            (positions zip velocities).forEach { (p, v) ->
                keys[0] += "|${p.x}|${v.x}"
                keys[1] += "|${p.y}|${v.y}"
                keys[2] += "|${p.z}|${v.z}"
            }
            keys.forEachIndexed { i, s ->
                if (seens[i].contains(s) && periods[i] == 0L) {
                    periods[i] = steps
                } else if (!seens[i].contains(s)) {
                    seens[i].add(s)
                }
            }

            // Check if we've found repeating states for all moons
            if (periods.all { it != 0L }) {
                break
            }

            // Step
            step(positions, velocities)
            steps++
        }

        return Math.lcm(*periods.toLongArray())
    }

    private fun step(
        positions: MutableList<Cartesian3D>,
        velocities: MutableList<Cartesian3D>
    ) {
        for (i in positions.indices) {
            for (j in positions.indices) {
                if (i == j)
                    continue
                velocities[i] = velocities[i] + positions[i].applyGravity(positions[j])
            }
        }
        for (i in velocities.indices) {
            positions[i] = positions[i] + velocities[i]
        }
    }

    private fun Cartesian3D.applyGravity(other: Cartesian3D): Cartesian3D {
        val m = listOf(x, y, z)
        val o = listOf(other.x, other.y, other.z)
        val r = mutableListOf<Int>()
        (m zip o).forEach { (mc, oc) ->
            r.add(
                when {
                    mc > oc -> -1
                    mc < oc -> 1
                    else -> 0
                }
            )
        }
        return Cartesian3D(r[0], r[1], r[2])
    }

    private fun Cartesian3D.energy(): Int {
        return x.absoluteValue + y.absoluteValue + z.absoluteValue
    }
}

fun parseInput(input: String): Cartesian3D {
    val regex = """<x=(?<x>-?\d+),\sy=(?<y>-?\d+),\sz=(?<z>-?\d+)>""".toRegex()
    return regex.matchEntire(input)
        ?.destructured
        ?.let { (x, y, z) ->
            Cartesian3D(x.toInt(), y.toInt(), z.toInt())
        } ?: throw InputMismatchException("input format not recognized")
}

fun main() {
    val input = Resources.readFileAsList("nineteen/day12.txt").map { parseInput(it) }

    println(Day12.part1(input))
    println(Day12.part2(input))
}