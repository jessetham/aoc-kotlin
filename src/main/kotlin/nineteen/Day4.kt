package nineteen

fun String.isMonotonicallyIncreasing(): Boolean {
    return this.windowed(2).all { it[0] <= it[1] }
}

fun String.hasMatchingPair(): Boolean {
    return this.windowed(2).any { it[0] == it[1] }
}

fun String.hasStrictMatchingPair(): Boolean {
    var prev = ' '
    val groups = mutableMapOf(prev to 0)
    for (c in this) {
        if (c != prev) {
            if (groups.getValue(prev) == 2) {
                return true
            }
            prev = c
            groups[c] = 0
        }
        groups[c] = groups.getOrDefault(c, 0) + 1
    }
    return groups[prev] == 2
}

object Day4 {
    fun part1(start: Int, end: Int) {
        val meetsCriteria =
            (start..end).map { it.toString() }.filter { it.isMonotonicallyIncreasing() && it.hasMatchingPair() }
        println(meetsCriteria.size)
    }

    fun part2(start: Int, end: Int) {
        val meetsCriteria =
            (start..end).map { it.toString() }.filter { it.isMonotonicallyIncreasing() && it.hasStrictMatchingPair() }
        println(meetsCriteria.size)
    }
}

fun main() {
    val (start, end) = "136818-685979".split("-").map { it.toInt() }
    Day4.part1(start, end)
    Day4.part2(start, end)
}