package twenty

import util.Resources
import util.Resources.splitByEmptyLine

object Day6 {
    fun combineForms(group: List<String>): Map<Char, Int> {
        val count = mutableMapOf<Char, Int>()
        for (form in group) {
            form.forEach { c -> count[c] = count.getOrDefault(c, 0) + 1 }
        }
        return count
    }
}

fun main() {
    val groups = Resources.readFileAsList("twenty/day6.txt").splitByEmptyLine()
    val groupAnswers = groups.map { group -> Day6.combineForms(group) }

    // Part 1
    println(groupAnswers.sumBy { it.size })

    // Part 2
    val groupAllAnsweredYes = (groups zip groupAnswers).map { (group, answers) -> answers.values.filter { numAnsweredYes -> numAnsweredYes == group.size } }
    println(groupAllAnsweredYes.sumBy { it.size })
}