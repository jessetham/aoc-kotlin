package nineteen

import nineteen.intcode.Context
import nineteen.intcode.Intcode
import util.Resources
import kotlin.math.sign

// The screen is 40x24

object Day13 {
    fun part1(program: Map<Long, Long>): Int {
        val ctx = Context(program.toMutableMap())
        val screen = mutableMapOf<Pair<Long, Long>, Long>()
        while (!ctx.halted) {
            val x = Intcode.stepUntilHaltedOrNewOutput(ctx) ?: break
            val y = Intcode.stepUntilNewOutput(ctx)
            val id = Intcode.stepUntilNewOutput(ctx)
            screen[x to y] = id
        }
        return screen.values.filter { it == 2L }.size
    }

    fun part2(program: Map<Long, Long>): Int {
        var paddle = 0L
        var score = 0L
        val ctx = Context(program.toMutableMap())
        ctx.program[0] = 2L

        while (!ctx.halted) {
            val x = Intcode.stepUntilHaltedOrNewOutput(ctx) ?: break
            Intcode.stepUntilNewOutput(ctx)
            val i = Intcode.stepUntilNewOutput(ctx)
            when {
                x == -1L -> {
                    score = i
                }
                i == 4L -> {
                    ctx.input.add((x - paddle).sign.toLong())
                }
                i == 3L -> {
                    paddle = x
                }
            }
        }
        return score.toInt()
    }
}

fun main() {
    val program = Resources.readFileAsIntcodeInstructions("nineteen/day13.txt")

    //println(Day13.part1(program))
    println(Day13.part2(program))
}