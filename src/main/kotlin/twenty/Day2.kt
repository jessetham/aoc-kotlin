package twenty

import util.Resources

object Day2 {
    data class Input(val l: Int, val h: Int, val c: Char, val pass: String)

    fun part1(input: List<Input>): Int {
        var validPasswords = 0
        for ((l, h, c, pass) in input) {
            val cCount = pass.count { it == c }
            if (cCount in l..h) {
                validPasswords++
            }
        }
        return validPasswords
    }

    fun part2(input: List<Input>): Int {
        var validPasswords = 0
        for ((l, h, c, pass) in input) {
            val first = pass.elementAtOrElse(l - 1) { ' ' }
            val second = pass.elementAtOrElse(h - 1) { ' ' }
            if ((first == c) xor (second == c)) {
                validPasswords++
            }
        }
        return validPasswords
    }
}

fun main() {
    val regex = """(\d+)-(\d+)\s(\w):\s(\w+)"""
    val input = Resources.readFileAsListAndParse("twenty/day2.txt", regex.toRegex()).map { (l, h, c, pass) -> Day2.Input(l.toInt(), h.toInt(), c.single(), pass) }
    println(Day2.part1(input))
    println(Day2.part2(input))
}