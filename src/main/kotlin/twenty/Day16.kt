package twenty

import util.Resources

object Day16 {
    // Easier just to hardcode these parts of the input
    // Shift+Alt click is pretty useful in IntelliJ
    val rules = mapOf(
            "departure location" to Pair(35..796, 811..953),
            "departure station" to Pair(25..224, 248..952),
            "departure platform" to Pair(47..867, 885..959),
            "departure track" to Pair(44..121, 127..949),
            "departure date" to Pair(49..154, 180..960),
            "departure time" to Pair(35..532, 546..971),
            "arrival location" to Pair(41..700, 706..953),
            "arrival station" to Pair(25..562, 568..968),
            "arrival platform" to Pair(31..672, 680..969),
            "arrival track" to Pair(43..836, 852..961),
            "class" to Pair(38..291, 304..968),
            "duration" to Pair(31..746, 755..956),
            "price" to Pair(46..711, 719..971),
            "route" to Pair(35..584, 608..955),
            "row" to Pair(39..618, 640..950),
            "seat" to Pair(25..308, 334..954),
            "train" to Pair(26..901, 913..957),
            "type" to Pair(33..130, 142..965),
            "wagon" to Pair(34..395, 405..962),
            "zone" to Pair(46..358, 377..969),
    )
    val myTicket = listOf(97, 103, 89, 191, 73, 79, 83, 101, 151, 71, 149, 53, 181, 59, 61, 67, 113, 109, 107, 127)

    fun check(tickets: List<List<Int>>): Pair<List<List<Int>>, Int> {
        val valid = mutableListOf<List<Int>>()
        var invalidSum = 0
        start@ for (ticket in tickets) {
            for (t in ticket) {
                if (rules.all { t !in it.value.first && t !in it.value.second }) {
                    invalidSum += t
                    continue@start
                }
            }
            valid.add(ticket)
        }
        return valid to invalidSum
    }

    fun findFields(validTickets: List<List<Int>>): Map<String, Int> {
        val correctMapping = mutableMapOf<String, Int>()
        val remainingRules = rules.toMutableMap()
        val remainingIndices = (0 until rules.size).toMutableSet()
        while (remainingRules.isNotEmpty()) {
            for ((field, ranges) in remainingRules) {
                val fits = mutableListOf<Int>()
                for (i in remainingIndices) {
                    if (validTickets.all { it[i] in ranges.first || it[i] in ranges.second }) {
                        fits.add(i)
                    }
                }
                // We only want to set the mapping if there is one exact match. Otherwise, it's too ambiguous
                // and we'll check again for this field after the number of unknown fields has decreased.
                if (fits.size == 1) {
                    correctMapping[field] = fits.first()
                    remainingIndices.remove(fits.first())
                }
            }
            correctMapping.forEach { remainingRules.remove(it.key) }
        }
        return correctMapping
    }
}

fun main() {
    // The input file only contains the other tickets
    val input = Resources.readFileAsList("input.txt").map { it.split(",").map { num -> num.toInt() } }
    val (validTickets, invalidSum) = Day16.check(input)
    // Part 1
    println(invalidSum)

    val fields = Day16.findFields(validTickets)
    val startsWithDeparture = fields.filter { it.key.startsWith("departure") }.map { Day16.myTicket[it.value].toLong() }
    // Part 2
    println(startsWithDeparture.fold(1L) { acc, i -> acc * i })
}