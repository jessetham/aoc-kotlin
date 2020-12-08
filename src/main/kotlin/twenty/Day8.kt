package twenty

import util.Resources

fun run(instructions: List<Pair<String, Int>>): Pair<Int, Boolean> {
    var acc = 0
    val seen = mutableSetOf<Int>()
    var i = 0
    while (i < instructions.size && !seen.contains(i)) {
        val (instruction, value) = instructions[i]
        seen.add(i)
        when (instruction) {
            "acc" -> {
                acc += value
                i++
            }
            "jmp" -> {
                i += value
            }
            "nop" -> {
                i++
            }
        }
    }
    return acc to (i == instructions.size)
}

fun main() {
    val input = Resources.readFileAsList("twenty/day8.txt").map { it.substringBefore(" ") to it.substringAfter(" ").toInt() }

    // Part 1
    val (acc1, _) = run(input)
    println(acc1)

    // Part 2
    for (i in input.indices) {
        if (input[i].first == "acc")
            continue
        val newInput = input.toMutableList()
        newInput[i] = if (newInput[i].first == "nop") "jmp" to newInput[i].second else "nop" to newInput[i].second
        val (acc2, res) = run(newInput)
        if (res) {
            println(acc2)
            break
        }
    }
}