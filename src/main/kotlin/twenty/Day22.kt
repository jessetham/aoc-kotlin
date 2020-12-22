package twenty

import util.Resources
import util.Resources.splitByEmptyLine

fun calculateWinnersScore(winningDeck: List<Int>) = winningDeck.reversed().withIndex().fold(0) { acc, pair -> acc + (pair.value * (pair.index + 1)) }

fun play(d1: MutableList<Int>, d2: MutableList<Int>): Pair<Int, Int> {
    // Not required for part 1
    val seen = mutableSetOf<Pair<List<Int>, List<Int>>>()
    while (d1.isNotEmpty() && d2.isNotEmpty()) {
        if (seen.contains(d1 to d2)) {
            return calculateWinnersScore(d1) to 0
        } else {
            seen.add(d1.toList() to d2.toList())
        }
        val a = d1.removeFirst()
        val b = d2.removeFirst()
        val winner = if (a <= d1.size && b <= d2.size) {
            // Also not required for part 1
            play(d1.subList(0, a).toMutableList(), d2.subList(0, b).toMutableList()).second
        } else if (a > b) {
            0
        } else 1
        if (winner == 0) {
            d1.add(a)
            d1.add(b)
        } else {
            d2.add(b)
            d2.add(a)
        }
    }
    return calculateWinnersScore(if (d1.isNotEmpty()) d1 else d2) to if (d1.isNotEmpty()) 0 else 1
}

fun main() {
    val (one, two) = Resources.readFileAsList("input.txt").splitByEmptyLine().map { it.subList(1, it.size).map { card -> card.toInt() } }

    // Part 2
    println(play(one.toMutableList(), two.toMutableList()).first)
}