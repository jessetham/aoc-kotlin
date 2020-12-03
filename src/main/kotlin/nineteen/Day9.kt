package nineteen

import nineteen.intcode.Context
import nineteen.intcode.Intcode
import util.Resources

object Day9 {
    fun part(program: Map<Long, Long>, part: Long): Long {
        val ctx = Context(program.toMutableMap(), input = ArrayDeque(listOf(part)))
        val out = Intcode.stepUntilNewOutput(ctx)
        if (!ctx.halted)
            throw Error("malfunctioning opcode: $out instruction pointer: ${ctx.ip}")
        return out
    }
}

fun main() {
    val program = Resources.readFileAsIntcodeInstructions("nineteen/day9.txt")

    println(Day9.part(program, 1L))
    println(Day9.part(program, 2L))
}