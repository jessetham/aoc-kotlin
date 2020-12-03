package nineteen

import util.Resources

object Day1 {
    fun part1(modules: List<Int>): Int {
        return modules.map { it / 3 - 2 }.sum()
    }

    fun part2(modules: List<Int>): Int {
        var sum = 0
        var m = modules
        while (m.isNotEmpty()) {
            m = m.map { it / 3 - 2 }.filter { it > 0 }
            sum += m.sum()
        }
        return sum
    }
}

fun main() {
    val input = Resources.readFileAsList("nineteen/day1.txt").map { it.toInt() }

    println(Day1.part1(input))
    println(Day1.part2(input))
}