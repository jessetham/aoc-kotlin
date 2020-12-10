package twenty

import util.Resources
import kotlin.math.pow

// The idea was to just count and multiply the number of subsets for groups of adapters that we could remove. Adapters that have a
// jolt difference of 3 with either of their neighbors can't be removed no matter what.
//
// I have no idea if this works with anyone else's input. I noticed that my input never had a group of removable adapters
// that was larger than 3.
fun countWays(joltDifferences: List<Int>): Long {
    val consecutiveOnes = mutableListOf<Int>()
    var count = 0
    for (num in joltDifferences) {
        if (num == 3) {
            if (count > 1)
                consecutiveOnes.add(count - 1)
            count = 0
        } else {
            count++
        }
    }
    return consecutiveOnes.map { if (it == 3) 2.0.pow(it).toLong() - 1L else 2.0.pow(it).toLong() }.fold(1) { a, b -> a * b }
}

fun countWaysDp(current: Int, target: Int, adapters: Set<Int>, memo: MutableMap<Int, Long>): Long {
    if (current == target) {
        return 1
    } else if (memo.contains(current)) {
        return memo.getValue(current)
    }
    var product = 0L
    for (i in 1..3) {
        if (adapters.contains(current + i)) {
            product += countWaysDp(current + i, target, adapters, memo)
        }
    }
    memo[current] = product
    return product
}

fun main() {
    // No repeating numbers
    // No jolt difference of two
    // Pockets of 1 are always 4 or lower
    val adapters = Resources.readFileAsList("input.txt").map { it.toInt() }.toMutableList()
    val deviceAdapter = adapters.maxOrNull()!! + 3
    adapters.add(0)
    adapters.add(deviceAdapter)
    val joltDifferences = adapters.sorted().windowed(2).map { (a, b) -> b - a }

    // Part 1
    println(joltDifferences.count { it == 3 } * joltDifferences.count { it == 1 })

    // Part 2
    println(countWays(joltDifferences))
    // Alternatively...
    println(countWaysDp(0, deviceAdapter, adapters.toSet(), mutableMapOf()))
}