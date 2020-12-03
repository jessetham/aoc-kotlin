package nineteen

import nineteen.intcode.Context
import nineteen.intcode.Intcode
import util.Resources

object Day2 {
    fun part1(program: Map<Long, Long>): Long {
        val ctx = Context(program.toMutableMap())
        ctx.program[1] = 12
        ctx.program[2] = 2
        Intcode.stepUntilHalted(ctx)
        return ctx.program.getValue(0)
    }

    fun part2(program: Map<Long, Long>, target: Long): Long {
        for (noun in 0..99L) {
            for (verb in 0..99L) {
                val ctx = Context(program.toMutableMap())
                ctx.program[1] = noun
                ctx.program[2] = verb
                Intcode.stepUntilHalted(ctx)
                if (ctx.program[0] == target) {
                    return 100 * noun + verb
                }
            }
        }
        throw Exception("Could not find noun/verb pair")
    }
}

fun main() {
    val program = Resources.readFileAsIntcodeInstructions("nineteen/day2.txt")

    println(Day2.part1(program))
    println(Day2.part2(program, 19690720))
}