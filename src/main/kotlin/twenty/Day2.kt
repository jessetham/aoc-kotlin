package twenty

import util.Resources

object Day2 {
    fun part1(input: List<List<String>>): Int {
        var validPasswords = 0
        for ((l, h, c, pass) in input) {
            val cCount = pass.count { it == c.single() }
            if (l.toInt() <= cCount && cCount <= h.toInt()) {
                validPasswords++
            }
        }
        return validPasswords
    }

    fun part2(input: List<List<String>>) : Int {
        var validPasswords = 0
        for ((l, h, c, pass) in input) {
            val first = pass.elementAtOrElse(l.toInt() - 1) {' '}
            val second = pass.elementAtOrElse(h.toInt() - 1) {' '}
            if ((first == c.single()) xor (second == c.single())) {
                validPasswords++
            }
        }
        return validPasswords
    }
}

fun main() {
    val regex = """(\d+)-(\d+)\s(\w):\s(\w+)"""
    val input = Resources.readFileAsListAndParse("twenty/day2.txt", regex.toRegex())
    println(Day2.part1(input))
    println(Day2.part2(input))
}