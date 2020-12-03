package nineteen

import nineteen.intcode.Context
import nineteen.intcode.Intcode
import util.Resources

object Day5 {
    fun part1(program: Map<Long, Long>): Long {
        val ctx = Context(program.toMutableMap(), input = ArrayDeque(listOf(1)), output = 0)
        return Intcode.stepThroughDiagnostics(ctx)
    }

    fun part2(program: Map<Long, Long>): Long {
        val ctx = Context(program.toMutableMap(), input = ArrayDeque(listOf(5)), output = 0)
        return Intcode.stepThroughDiagnostics(ctx)
    }
}

fun main() {
    val program = Resources.readFileAsIntcodeInstructions("nineteen/day5.txt")

    println(Day5.part1(program))
    println(Day5.part2(program))
}