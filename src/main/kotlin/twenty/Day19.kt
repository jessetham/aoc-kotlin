package twenty

import util.Resources
import util.Resources.splitByEmptyLine

sealed class Day19Rule {
    lateinit var pattern: String
    abstract fun getPattern(allRules: Map<String, Day19Rule>): String

    data class Base(val result: String) : Day19Rule() {
        override fun getPattern(allRules: Map<String, Day19Rule>) = result
    }

    data class Serial(val ids: List<String>) : Day19Rule() {
        override fun getPattern(allRules: Map<String, Day19Rule>): String {
            if (!this::pattern.isInitialized) {
                pattern = buildString {
                    for (id in ids) {
                        append(allRules.getValue(id).getPattern(allRules))
                    }
                }
            }
            return pattern
        }
    }

    data class OrSerial(val s1: Serial, val s2: Serial) : Day19Rule() {
        override fun getPattern(allRules: Map<String, Day19Rule>): String {
            if (!this::pattern.isInitialized) {
                pattern = buildString {
                    append("(")
                    append(s1.getPattern(allRules))
                    append("|")
                    append(s2.getPattern(allRules))
                    append(")")
                }
            }
            return pattern
        }
    }
}

object Day19 {
    fun parseRules(rules: List<String>): Map<String, Day19Rule> {
        val parsed = mutableMapOf<String, Day19Rule>()
        for (rule in rules) {
            val (id, rest) = rule.split(": ")
            val rule = if (rest.contains(" | ")) {
                rest.split(" | ").let { (a, b) ->
                    Day19Rule.OrSerial(
                            Day19Rule.Serial(a.split(" ")),
                            Day19Rule.Serial(b.split(" "))
                    )
                }
            } else if (rest.contains("\"")) {
                Day19Rule.Base(rest[1].toString())
            } else {
                Day19Rule.Serial(rest.split(" "))
            }
            parsed[id] = rule
        }
        return parsed
    }
}

fun main() {
    val (rules, messages) = Resources.readFileAsList("input.txt").splitByEmptyLine()

    // Part 1
    val parsed = Day19.parseRules(rules)
    val rule0Regex = parsed["0"]!!.getPattern(parsed).toRegex()
    println(messages.count { rule0Regex.matches(it) })

    // Part 2
    val modified = Day19.parseRules(rules)
    val rule42Pattern = modified["42"]!!.getPattern(modified)
    val rule31Pattern = modified["31"]!!.getPattern(modified)
    modified["8"]!!.pattern = "($rule42Pattern)+"
    // Recursive regex is also supposed to work?
    modified["11"]!!.pattern = """
        ($rule42Pattern$rule31Pattern|
        $rule42Pattern$rule42Pattern$rule31Pattern$rule31Pattern|
        $rule42Pattern$rule42Pattern$rule42Pattern$rule31Pattern$rule31Pattern$rule31Pattern|
        $rule42Pattern$rule42Pattern$rule42Pattern$rule42Pattern$rule31Pattern$rule31Pattern$rule31Pattern$rule31Pattern|
        $rule42Pattern$rule42Pattern$rule42Pattern$rule42Pattern$rule42Pattern$rule31Pattern$rule31Pattern$rule31Pattern$rule31Pattern$rule31Pattern|
        $rule42Pattern$rule42Pattern$rule42Pattern$rule42Pattern$rule42Pattern$rule42Pattern$rule31Pattern$rule31Pattern$rule31Pattern$rule31Pattern$rule31Pattern$rule31Pattern|
        $rule42Pattern$rule42Pattern$rule42Pattern$rule42Pattern$rule42Pattern$rule42Pattern$rule42Pattern$rule31Pattern$rule31Pattern$rule31Pattern$rule31Pattern$rule31Pattern$rule31Pattern$rule31Pattern)
    """.trimIndent().replace("\n", "")
    val modifiedRule0Regex = modified["0"]!!.getPattern(modified).toRegex()
    println(messages.count { modifiedRule0Regex.matches(it) })
}