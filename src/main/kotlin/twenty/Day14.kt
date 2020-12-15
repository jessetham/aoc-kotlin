package twenty

import util.MyMath
import util.Resources

sealed class Day14Expr {
    data class Mask(val mask: String) : Day14Expr()
    data class Memory(val address: String, val value: String) : Day14Expr()
}

private fun String.getBitRepresentation(): String {
    return this.toLong().toString(2).padStart(36, '0')
}

object Day14 {
    fun version1(input: List<Day14Expr>): Long {
        var currentMask = ""
        val memory = mutableMapOf<Long, Long>()
        for (expr in input) {
            when (expr) {
                is Day14Expr.Mask -> currentMask = expr.mask
                is Day14Expr.Memory -> memory[expr.address.toLong(2)] = currentMask.zip(expr.value)
                        .map { (cm, v) -> if (cm == 'X') v else cm }
                        .joinToString("")
                        .toLong(2)
            }
        }
        return memory.values.sum()
    }

    fun version2(input: List<Day14Expr>): Long {
        var currentMask = ""
        var numFloating = 0
        val memory = mutableMapOf<Long, Long>()
        for (expr in input) {
            when (expr) {
                is Day14Expr.Mask -> {
                    currentMask = expr.mask
                    numFloating = currentMask.count { it == 'X' }
                }
                is Day14Expr.Memory -> {
                    for (combination in MyMath.product("01", count = numFloating)) {
                        var i = 0
                        val floatingAddress = currentMask.zip(expr.address)
                                .map { (cm, a) ->
                                    when (cm) {
                                        '1' -> '1'
                                        'X' -> combination[i++]
                                        else -> a
                                    }
                                }
                                .joinToString("")
                                .toLong(2)
                        memory[floatingAddress] = expr.value.toLong(2)
                    }
                }
            }
        }
        return memory.values.sum()
    }
}

fun main() {
    val regex = """(\w+)\[?(\d+)?]? = (\w+)""".toRegex()
    val input = Resources.readFileAsList("input.txt").map { regex.find(it)!!.destructured }.map { (a, b, c) ->
        when (a) {
            "mask" -> Day14Expr.Mask(c)
            "mem" -> Day14Expr.Memory(b.getBitRepresentation(), c.getBitRepresentation())
            else -> throw Exception("unknown field $a")
        }
    }

    // Part 1
    println(Day14.version1(input))

    // Part 2
    println(Day14.version2(input))
}