package util

import java.io.File
import java.io.FileNotFoundException
import java.lang.IllegalArgumentException
import java.net.URI

// Taken from https://github.com/tginsberg's AOC 2019 project
object Resources {
    private fun String.toURI(): URI =
            Resources.javaClass.classLoader.getResource(this)?.toURI()
                    ?: throw FileNotFoundException("file does not exist $this")

    private fun String.parseUsingRegex(regex: Regex): List<String> {
        return regex.matchEntire(this)?.destructured?.toList()
                ?: throw IllegalArgumentException("regex couldn't parse $this")
    }

    fun List<String>.splitByEmptyLine(): List<List<String>> {
        val res = mutableListOf(mutableListOf<String>())
        for (line in this) {
            if (line.isEmpty()) {
                res.add(mutableListOf())
            } else {
                res.last().add(line)
            }
        }
        return res
    }

    fun readFileAsList(filename: String): List<String> = File(filename.toURI()).readLines()

    fun readFileAsString(filename: String, delimiter: String = ""): String =
            readFileAsList(filename).joinToString(delimiter)

    fun readFileAsIntcodeInstructions(filename: String): Map<Long, Long> =
            readFileAsString(filename).split(",").withIndex().associate { it.index.toLong() to it.value.toLong() }

    fun readFileAsListAndParse(filename: String, regex: Regex): List<List<String>> = readFileAsList(filename).map { it.parseUsingRegex(regex) }
}