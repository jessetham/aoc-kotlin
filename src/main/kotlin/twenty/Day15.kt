package twenty

import util.Resources
import kotlin.system.measureTimeMillis

object Day15 {
    fun game(input: List<Int>, turns: Int): Int {
        val seen = input.withIndex().associateBy({ it.value }, { it.index + 1 }).toMutableMap()
        var lastSpoken = 0
        var turn = seen.size + 1
        while (turn < turns) {
            turn++
            val tmp = lastSpoken
            lastSpoken = if (seen.contains(lastSpoken)) {
                val lastLast = seen.getValue(lastSpoken)
                turn - 1 - lastLast
            } else {
                0
            }
            seen[tmp] = turn - 1
        }
        return lastSpoken
    }
}

fun main() {
    val input = Resources.readFileAsString("input.txt").split(",").map { it.toInt() }
    // Part 1
    println(Day15.game(input, 2020))
    // Part 2
    println(Day15.game(input, 30000000))
    // 4831ms
    // println((1..10).map { measureTimeMillis { Day15.game(input, 30000000) } }.average())
}