package nineteen

import nineteen.intcode.Context
import nineteen.intcode.Intcode
import util.Coordinates.Cartesian2D
import util.Resources
import kotlin.math.absoluteValue

object Day11 {
    fun part1(program: Map<Long, Long>): Int {
        val panels = paint(program)
        return panels.size
    }

    fun part2(program: Map<Long, Long>): String {
        val panels = paint(program, 1)
        val minX = panels.minByOrNull { it.key.x }!!.key.x
        val minY = panels.minByOrNull { it.key.y }!!.key.y
        val maxX = panels.maxByOrNull { it.key.x }!!.key.x
        val maxY = panels.maxByOrNull { it.key.y }!!.key.y
        val width = (maxX - minX).absoluteValue
        val height = (maxY - minY).absoluteValue
        val grid = MutableList(height + 1) { MutableList(width + 1) { " " } }
        for ((key, value) in panels) {
            if (value == 0L)
                continue
            val x = key.x - minX
            val y = key.y - minY
            grid[y][x] = "@"
        }
        return grid.map { it.joinToString("") }.reversed().joinToString("\n")
    }

    fun paint(program: Map<Long, Long>, startingSquareColor: Long = 0): Map<Cartesian2D, Long> {
        val ctx = Context(program.toMutableMap(), input = mutableListOf(startingSquareColor))
        // Using hashCode() as the key gave us the wrong answer. Wonder if there were duplicate hash codes for different
        // coordinates? Anyways, there goes 2 hours of my life.
        val panels = mutableMapOf<Cartesian2D, Long>()
        var position = Cartesian2D(0, 0)
        var dx = 0
        var dy = 1
        while (!ctx.halted) {
            val newColor = Intcode.stepUntilHaltedOrNewOutput(ctx) ?: break
            val turn = Intcode.stepUntilNewOutput(ctx)

            panels[position] = newColor
            when (turn) {
                0L -> dx = -dy.also { dy = dx }
                1L -> dx = dy.also { dy = -dx }
                else -> throw Exception("invalid turn code")
            }
            position = Cartesian2D(position.x + dx, position.y + dy)

            val panelColor = panels.getOrDefault(position.copy(), 0)
            ctx.input.add(panelColor)
        }
        return panels
    }
}

fun main() {
    val program = Resources.readFileAsIntcodeInstructions("nineteen/day11.txt")

    //println(Day11.part1(program))
    println(Day11.part2(program))
}
