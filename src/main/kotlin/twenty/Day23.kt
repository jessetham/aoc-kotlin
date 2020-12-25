import util.Resources
import kotlin.system.measureTimeMillis

object Day23 {
    fun getDestination(removed: Set<Int>, current: Int, maxCupLabel: Int): Int {
        var target = current
        while (removed.contains(target)) {
            target = if (target - 1 < 1) maxCupLabel else target - 1
        }
        return target
    }

    fun arrangeCups(cups: List<Int>) = cups.withIndex().associate { it.value to cups[(it.index + 1) % cups.size] }

    fun play(input: List<Int>, head: Int, numRounds: Int): Map<Int, Int> {
        val cups = arrangeCups(input).toMutableMap()
        var current = head
        val maxCupLabel = cups.keys.maxOrNull()!!
        repeat(numRounds) { _ ->
            val next3Head = cups[current]!!
            val next3Tail = cups[cups[next3Head]!!]!!
            cups[current] = cups[next3Tail]!!

            val destination = getDestination(
                    setOf(current, next3Head, cups[next3Head]!!, next3Tail),
                    current,
                    maxCupLabel
            )
            cups[next3Tail] = cups[destination]!!
            cups[destination] = next3Head
            current = cups[current]!!
        }
        return cups
    }
}

fun main() {
    val input = Resources.readFileAsString("input.txt").map { Character.getNumericValue(it) }

    // Part 1
    val cups1 = Day23.play(input, input.first(), 100)
    println(buildString {
        var node = cups1[1]!!
        while (node != 1) {
            append(node)
            node = cups1[node]!!
        }
    })
    // Part 2
    val cups2 = Day23.play(
            input.plus(10..1_000_000),
            input.first(),
            10_000_000)
    println(cups2[1]!!.toLong() * cups2[cups2[1]!!]!!.toLong())

    // 7198.7 ms
    /*println((1..10).map {
        measureTimeMillis {
            Day23.play(
                    input.plus(10..1_000_000),
                    input.first(),
                    10_000_000)
        }
    }.average())*/
}