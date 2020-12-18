package twenty

import util.Resources

object Day18 {
    val numberRegex = """([0-9]+)""".toRegex()

    fun modifiedShuntingYard(equation: String): List<String> {
        val operands = mutableListOf<String>()
        val result = mutableListOf<String>()
        var i = 0
        while (i < equation.length) {
            when (val e = equation[i].toString()) {
                // For part 1, you have to do something like this for both operators since they both
                // have the same precedence
                "*" -> {
                    while (operands.isNotEmpty() && operands.last() == "+")
                        result.add(operands.removeLast())
                    operands.add(e)
                }
                "+", "(" -> operands.add(e)
                ")" -> {
                    while (operands.isNotEmpty() && operands.last() != "(")
                        result.add(operands.removeLast())
                    // Drop '(' character
                    operands.removeLast()
                }
                else -> {
                    val num = numberRegex.find(equation, i)?.value ?: throw Error("could not parse out a number")
                    i += num.length - 1
                    result.add(num)
                }
            }
            i++
        }
        result.addAll(operands.reversed())
        return result
    }

    fun evaluate(rpn: List<String>): Long {
        val stack = mutableListOf<Long>()
        for (e in rpn) {
            stack.add(
                when (e) {
                    "+" -> stack.removeLast() + stack.removeLast()
                    "*" -> stack.removeLast() * stack.removeLast()
                    else -> e.toLong()
                }
            )
        }
        return stack.last()
    }
}

fun main() {
    val input = Resources.readFileAsList("input.txt").map { it.replace(" ", "") }

    // Part 2, see function for notes on how to modify for part 1
    val rpns = input.map { Day18.modifiedShuntingYard(it) }
    println(rpns.fold(0L) { acc, rpn -> acc + Day18.evaluate(rpn) })
}