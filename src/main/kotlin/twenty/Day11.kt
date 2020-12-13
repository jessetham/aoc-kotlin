package twenty

import util.Resources

object Day11 {
    val deltas = listOf(-1 to 0, -1 to 1, 0 to 1, 1 to 1, 1 to 0, 1 to -1, 0 to -1, -1 to -1)

    fun isOccupied(grid: List<CharArray>, r: Int, dr: Int, c: Int, dc: Int): Boolean {
        var mr = r
        var mc = c
        while (mr + dr in grid.indices && mc + dc in grid[r].indices) {
            if (grid[mr + dr][mc + dc] == '#') {
                return true
            } else if (grid[mr + dr][mc + dc] == 'L') {
                break
            }
            mr += dr
            mc += dc
        }
        return false
    }

    fun gameOfSeats(input: List<CharArray>): Int {
        val height = input.size
        val width = input.first().size
        var prev = input
        do {
            val current = MutableList(height) { CharArray(width) { 'x' } }
            for (r in current.indices) {
                for (c in current[r].indices) {
                    // To do part 1, just check adjacent seats. Also, change the numOccupied condition in the else-if case to 4
                    val numOccupied = deltas.count { (dr, dc) -> isOccupied(prev, r, dr, c, dc) }
                    current[r][c] = if (prev[r][c] == 'L' && numOccupied == 0) {
                        '#'
                    } else if (prev[r][c] == '#' && numOccupied >= 5) {
                        'L'
                    } else {
                        prev[r][c]
                    }
                }
            }
            val tmp = prev
            prev = current
        } while ((current zip tmp).any { (c, t) -> !c.contentEquals(t) })
        return prev.map { row -> row.count { seat -> seat == '#' } }.sum()
    }
}

fun main() {
    val input = Resources.readFileAsList("input.txt").map { it.toCharArray() }

    // Part 2
    println(Day11.gameOfSeats(input))
}