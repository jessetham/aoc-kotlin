package twenty

import util.Resources
import util.Math

object Day13 {
    fun getEarliestBus(estimate: Int, buses: List<Long>): Long {
        val waiting = buses.map { it to it - (estimate % it) }
        return waiting.minByOrNull { it.second }!!.let { it.first * it.second }
    }

    fun getGoldCoin(buses: List<Pair<Int, Long>>): Long {
        var timestamp = 0L
        var step = buses.first().second
        for (i in 1 until buses.size) {
            while ((timestamp + buses[i].first) % buses[i].second != 0L) {
                timestamp += step
            }
            step = Math.lcm(step, buses[i].second)
        }
        return timestamp
    }
}

fun main() {
    val input = Resources.readFileAsList("input.txt")
    val estimate = input[0].toInt()
    val buses = input[1].split(",").withIndex().filter { it.value != "x" }.map { it.index to it.value.toLong() }

    // Part 1
    println(Day13.getEarliestBus(estimate, buses.map { it.second }))

    // Part 2
    println(Day13.getGoldCoin(buses))
}