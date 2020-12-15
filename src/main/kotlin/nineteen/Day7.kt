package nineteen

import util.MyMath.permutations
import nineteen.intcode.Context
import nineteen.intcode.Intcode
import util.Resources
import kotlin.math.max

object Day7 {
    fun part1(program: Map<Long, Long>): Long {
        val phases = listOf<Long>(0, 1, 2, 3, 4)
        var maxOutput = -1L
        permutations(phases).forEach { permutation ->
            var prevOutput = 0L
            permutation.forEach { phase ->
                val ctx = Context(program.toMutableMap(), input = ArrayDeque(listOf<Long>(phase, prevOutput)))
                prevOutput = Intcode.stepUntilNewOutput(ctx)
            }
            maxOutput = max(maxOutput, prevOutput)
        }
        return maxOutput
    }

    fun part2(program: Map<Long, Long>): Long {
        val phases = listOf<Long>(5, 6, 7, 8, 9)
        var maxOutput = -1L
        permutations(phases).forEach { permutation ->
            val ctxs =
                List(permutation.size) { i ->
                    Context(
                        program.toMutableMap(),
                        input = ArrayDeque(listOf(permutation[i]))
                    )
                }
            var prevOutput = 0L
            while (!ctxs.all { it.halted }) {
                for (ctx in ctxs) {
                    ctx.input.add(prevOutput)
                    prevOutput = Intcode.stepUntilNewOutput(ctx)
                }
            }
            maxOutput = max(maxOutput, prevOutput)
        }
        return maxOutput
    }
}

fun main() {
    val program = Resources.readFileAsIntcodeInstructions("nineteen/day7.txt")

    println(Day7.part1(program))
    println(Day7.part2(program))
}