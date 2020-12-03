package twenty

import util.Resources

object Day3 {
    fun part1and2(input: List<String>, down: Int, right: Int): Long {
        var c = 0
        var r = 0
        var numTrees = 0L
        while (r < input.size) {
            if (input[r][c] == '#') {
                numTrees++
            }
            r += down
            c = (c + right) % input.first().length
        }
        return numTrees
    }
}

fun main() {
    val input = Resources.readFileAsList("twenty/day3.txt")
    println(Day3.part1and2(input, 1, 3))
    println(Day3.part1and2(input, 1, 1) * Day3.part1and2(input, 1, 3) * Day3.part1and2(input, 1, 5) * Day3.part1and2(input, 1, 7) * Day3.part1and2(input, 2, 1))
}