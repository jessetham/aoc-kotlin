package nineteen

import util.Resources

object Day14 {
    fun part1(requires: Map<String, List<Pair<String, Long>>>, creates: Map<String, Long>): Long {
        val stack = mutableListOf("FUEL" to 1L)
        var oreTotal = 0L
        while (stack.isNotEmpty()) {
            val (l, v) = stack.removeLast()
            println("$l $v")
            if (l == "ORE") {
                oreTotal += v
                continue
            }
            val create = creates.getValue(l)
            val multiplier = if (v % create == 0L) v / create else v / create + 1
            for ((ol, ov) in requires.getValue(l)) {
                print("$ol ${ov * multiplier}, ")
                stack.add(ol to ov * multiplier)
            }
            println()
        }
        return oreTotal
    }
}

fun main() {
    val input = Resources.readFileAsList("nineteen/day14.txt").map { it.split(" => ") }
    val requires = mutableMapOf<String, MutableList<Pair<String, Long>>>()
    val creates = mutableMapOf<String, Long>()
    for ((r, c) in input) {
        val list = c.split(" ").let { (n, l) ->
            creates[l] = n.toLong()
            requires.getOrPut(l, { mutableListOf() })
        }
        r.split(", ").forEach {
            it.split(" ").let { (n, l) ->
                list.add(l to n.toLong())
            }
        }
    }

    Day14.part1(requires, creates)
}