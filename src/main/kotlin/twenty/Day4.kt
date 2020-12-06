package twenty

import util.Resources

object Day4 {
    val requiredFields = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
    val validEyeColors = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

    private fun verify(field: String, value: String): Boolean {
        return when (field) {
            "byr" -> value.length == 4 && value.toInt() in 1920..2002
            "iyr" -> value.length == 4 && value.toInt() in 2010..2020
            "eyr" -> value.length == 4 && value.toInt() in 2020..2030
            "hgt" -> {
                if (value.length >= 3) {
                    val num = value.substring(0, value.length - 2).toInt()
                    when (value.substring(value.length - 2)) {
                        "cm" -> num in 150..193
                        "in" -> num in 59..76
                        else -> false
                    }
                } else false
            }
            "hcl" -> value[0] == '#' && value.substring(1).length == 6 && value.substring(1).map { it in '0'..'9' || it in 'a'..'f' }.all { it }
            "ecl" -> validEyeColors.contains(value)
            "pid" -> value.length == 9 && value.toIntOrNull() != null
            else -> throw Exception("$field : $value")
        }
    }

    fun part1and2(input: MutableList<String>): Int {
        var numValid = 0
        val fields = mutableSetOf<String>()
        // Makes sure that we check that our last passport is valid
        input.add("")
        for (line in input) {
            if (line.isEmpty()) {
                if (fields == requiredFields)
                    numValid++
                fields.clear()
            } else {
                line.split(" ").forEach {
                    val (field, value) = it.split(":")
                    // The verify bit is only for part 2
                    if (field != "cid" && verify(field, value)) {
                        fields.add(field)
                    }
                }
            }
        }
        return numValid
    }
}

fun main() {
    val input = Resources.readFileAsList("twenty/day4.txt")
    println(Day4.part1and2(input.toMutableList()))
}