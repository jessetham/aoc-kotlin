package twenty

import util.Resources

object Day5 {
    fun search(line: String, upper: Int, lowerHalf: Char): Int {
        var l = 0
        var r = upper
        for (c in line) {
            val m = (r + l) / 2
            if (c == lowerHalf) {
                r = m
            } else {
                l = m + 1
            }
        }
        return l
    }
}

fun main() {
    val seatIds = Resources.readFileAsList("twenty/day5.txt").map { line ->
        Day5.search(line.substring(0, 7), 127, 'F') * 8 + Day5.search(line.substring(7), 7, 'L')
    }.sorted()

    // Part 1
    println(seatIds.last())

    // Part 2
    val allSeats = (seatIds.first()..seatIds.last()).reduce { a, b -> a xor b }
    println(seatIds.fold(allSeats) { a, b -> a xor b })
}