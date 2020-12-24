package twenty

import util.Resources

object Day24 {
    val directions = listOf("e", "se", "sw", "w", "nw", "ne")
    val directionsRegex = """(${directions.joinToString("|")})""".toRegex()

    fun getNextTile(direction: String, c: Int, r: Int) = when (direction) {
        "e" -> c + 1 to r
        "w" -> c - 1 to r
        "se" -> if (r % 2 != 0) c + 1 to r + 1 else c to r + 1
        "sw" -> if (r % 2 == 0) c - 1 to r + 1 else c to r + 1
        "ne" -> if (r % 2 != 0) c + 1 to r - 1 else c to r - 1
        "nw" -> if (r % 2 == 0) c - 1 to r - 1 else c to r - 1
        else -> throw Error("invalid direction")
    }

    fun getInitialTiles(instructions: List<String>): Map<Pair<Int, Int>, Int> {
        val tiles = mutableMapOf<Pair<Int, Int>, Int>()
        for (instruction in instructions) {
            var r = 0
            var c = 0
            for (d in directionsRegex.findAll(instruction).map { it.value }) {
                val (nc, nr) = getNextTile(d, c, r)
                c = nc
                r = nr
            }
            tiles[c to r] = tiles.getOrDefault(c to r, 0) xor 1
        }
        return tiles
    }

    fun gameOfTiles(blackTiles: Set<Pair<Int, Int>>): Int {
        var prev = blackTiles
        repeat(100) { _ ->
            val numNeighbors = mutableMapOf<Pair<Int, Int>, Int>()
            for ((c, r) in prev) {
                for (dir in directions) {
                    val nextTile = getNextTile(dir, c, r)
                    numNeighbors[nextTile] = numNeighbors.getOrDefault(nextTile, 0) + 1
                }
            }
            prev = numNeighbors.filter { (coord, count) ->
                (prev.contains(coord) && count in 1..2) || (!prev.contains(coord) && count == 2)
            }.keys
        }
        return prev.size
    }
}

fun main() {
    val input = Resources.readFileAsList("input.txt")

    // Part 1
    val initialTiles = Day24.getInitialTiles(input)
    println(initialTiles.values.count { it == 1 })

    // Part 2
    val blackTiles = initialTiles.filter { it.value == 1 }.keys
    println(Day24.gameOfTiles(blackTiles))
}