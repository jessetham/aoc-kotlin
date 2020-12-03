package nineteen

import util.Resources
import java.util.*

object Day6 {
    private fun calculateOrbits(orbit: String, orbits: Map<String, String>, numOrbits: MutableMap<String, Int>): Int {
        if (!numOrbits.containsKey(orbit)) {
            val orbiting = orbits.getValue(orbit)
            numOrbits[orbit] = calculateOrbits(orbiting, orbits, numOrbits) + 1
        }
        return numOrbits.getValue(orbit)
    }

    fun part1(orbits: Map<String, String>) {
        val numOrbits = mutableMapOf("COM" to 0)
        val totalOrbits = orbits.keys.map { calculateOrbits(it, orbits, numOrbits) }.sum()
        println("Total number of direct and indirect orbits is $totalOrbits")
    }

    fun part2(orbits: Map<String, String>) {
        val orbitalObjects = mutableMapOf<String, Int>()
        val queue: Queue<Pair<String, Int>> = LinkedList(listOf("YOU" to -1, "SAN" to -1))
        var orbitalTransfers = -1
        while (queue.size > 0) {
            val (oo, steps) = queue.remove()
            if (orbitalObjects.containsKey(oo)) {
                orbitalTransfers = orbitalObjects.getValue(oo) + steps
                break
            } else if (orbits.contains(oo)) {
                orbitalObjects[oo] = steps
                queue.add(orbits.getValue(oo) to steps + 1)
            }
        }
        println("The number of orbital transfers is $orbitalTransfers")
    }
}

fun main() {
    val orbits = Resources.readFileAsList("nineteen/day6.txt").associate { it.split(")").let { (a, b) -> b to a } }

    Day6.part1(orbits)
    Day6.part2(orbits)
}