package twenty

import util.Resources

object Day7 {
    fun buildGraph(input: List<List<MatchResult.Destructured>>): Map<String, List<Pair<String, Int>>> {
        val graph = mutableMapOf<String, MutableList<Pair<String, Int>>>()
        for (line in input) {
            val (_, contains) = line[0]
            val l = graph.getOrPut(contains, { mutableListOf() })
            line.subList(1, line.size).forEach { (times, color) -> if (color != "no other") l.add(color to times.toInt()) }
        }
        return graph
    }

    fun countBagsContainingShinyGold(graph: Map<String, List<Pair<String, Int>>>): Int {
        val noIncoming = graph.keys.toMutableSet()
        for (towards in graph.values) {
            towards.forEach { (s, _) -> noIncoming.remove(s) }
        }
        val canContain = mutableSetOf<String>()
        val q = ArrayDeque<Pair<String, MutableSet<String>>>()
        noIncoming.forEach { q.add(it to mutableSetOf(it)) }
        while (q.isNotEmpty()) {
            val (n, set) = q.removeFirst()
            if (n == "shiny gold") {
                canContain.addAll(set)
            } else {
                for ((contain, _) in graph.getValue(n)) {
                    val copy = set.toMutableSet()
                    copy.add(contain)
                    q.add(contain to copy)
                }
            }
        }
        return canContain.size - 1
    }

    fun countBagsInside(node: String, graph: Map<String, List<Pair<String, Int>>>): Long {
        if (graph.getValue(node).isEmpty()) {
            return 1
        }
        var total = 0L
        for ((c, count) in graph.getValue(node)) {
            total += countBagsInside(c, graph) * count
        }
        return total + 1
    }
}

fun main() {
    val regex = """(\d+)? ?(\w+ \w+) bags?""".toRegex()
    val input = Resources.readFileAsList("twenty/day7.txt").map { line ->
        regex.findAll(line).map { it.destructured }.toList()
    }
    val graph = Day7.buildGraph(input)

    // Part 1
    println(Day7.countBagsContainingShinyGold(graph))

    // Part 2
    // We don't count our shiny gold bag so subtract the result by one
    println(Day7.countBagsInside("shiny gold", graph) - 1)
}