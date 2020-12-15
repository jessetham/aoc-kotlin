package twenty

import util.Resources

object Day15 {
    fun game(input: List<Int>, turns: Int): Int {
        var turn = 1
        var lastNum = -1
        val seen = mutableMapOf<Int, Pair<Int, Int>>()
        seen[0] = -1 to -1
        while (turn <= input.size) {
            lastNum = input[turn - 1]
            seen[lastNum] = -1 to turn
            turn++
        }
        // For part 1, replace with 2020
        while (turn <= turns) {
            lastNum = if (seen.getValue(lastNum).first == -1) {
                seen[0] = seen.getValue(0).second to turn
                0
            } else {
                val (a, b) = seen.getValue(lastNum)
                seen[b - a] = if (seen.contains(b - a)) seen.getValue(b - a).second to turn else -1 to turn
                b - a
            }
            turn++
        }
        return lastNum
    }
}

fun main() {
    val input = Resources.readFileAsString("input.txt").split(",").map { it.toInt() }

    // Part 1
    println(Day15.game(input, 2020))

    // Part 2 takes 6.637s when measure by measureTimeMillis
    println(Day15.game(input, 30000000))
}