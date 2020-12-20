package twenty

import util.MyMath
import util.Resources
import util.Resources.splitByEmptyLine

object Day20 {
    fun flipTile(tile: List<String>, flipId: Int) = when (flipId) {
        0 -> tile.toList()
        // Flip across horizontal axis
        1 -> tile.reversed()
        // Flip across vertical axis
        2 -> tile.map { it.reversed() }
        else -> throw Error("invalid flipId")
    }

    fun rotateTile(tile: List<String>, rotateId: Int): List<String> {
        if (rotateId == 0) {
            return tile.toList()
        }
        var newTile = tile.map { it.toCharArray() }
        repeat(rotateId) {
            val rotated = List(tile.size) { CharArray(tile[0].length) }
            for (i in newTile.indices) {
                for (j in newTile[0].indices) {
                    rotated[j][i] = newTile[i][j]
                }
            }
            newTile = rotated.map { it.reversed().toCharArray() }
        }
        return newTile.map { it.joinToString("") }
    }

    fun removeEdges(tile: List<String>) = (1 until tile.size - 1).map {
        tile[it].substring(1, tile[it].length - 1)
    }

    fun getEdges(tile: List<String>) = listOf(
            (tile[0].indices).map { tile[0][it] }.joinToString(""), // top
            (tile[0].indices).map { tile[tile.size - 1][it] }.joinToString(""), // bottom
            (tile.indices).map { tile[it][0] }.joinToString(""), // left
            (tile.indices.map { tile[it][tile[0].length - 1] }.joinToString("")) // right
    )

    fun getNeighbors(edges: Map<Long, List<String>>): Map<Long, Set<Long>> {
        val numNeighbors = mutableMapOf<Long, MutableSet<Long>>()
        for ((id, e) in edges) {
            start@ for ((oid, o) in edges) {
                if (id == oid)
                    continue
                for (i in o.indices) {
                    if (e.contains(o[i]) || e.contains(o[i].reversed())) {
                        val myMatches = numNeighbors.getOrPut(id, { mutableSetOf() })
                        myMatches.add(oid)
                        continue@start
                    }
                }
            }
        }
        return numNeighbors
    }

    fun putTogetherPhoto(unfitTiles: Map<Long, List<String>>, neighbors: Map<Long, Set<Long>>): List<String> {
        // First, figure out how all the tile fit together. Basically, you just need to pick an arbitrary tile and
        // "lock" its orientation then put everything else together by always testing against "locked" tiles.
        val initial = neighbors.filter { it.value.size == 2 }.entries.toList().first()
        val fitTiles = mutableMapOf(initial.key to unfitTiles.getValue(initial.key))
        val graph = mutableMapOf<Long, MutableList<Long>>()
        val q = ArrayDeque<Long>()
        q.add(initial.key)
        while (q.isNotEmpty()) {
            val me = q.removeFirst()
            start@ for (nei in neighbors.getValue(me)) {
                if (!fitTiles.contains(nei))
                    q.add(nei)
                val myEdges = getEdges(fitTiles[me]!!)
                for ((flip, rotate) in MyMath.product(listOf(0, 1, 2), listOf(0, 1, 2, 3))) {
                    val neiTile = flipTile(rotateTile(unfitTiles[nei]!!, rotate), flip)
                    val neiEdges = getEdges(neiTile)
                    for ((m, n) in listOf(0 to 1, 1 to 0, 2 to 3, 3 to 2)) {
                        if (myEdges[m] == neiEdges[n]) {
                            fitTiles[nei] = neiTile
                            graph.getOrPut(me, { mutableListOf(-1, -1, -1, -1) })[m] = nei
                            graph.getOrPut(nei, { mutableListOf(-1, -1, -1, -1) })[n] = me
                            continue@start
                        }
                    }
                }
            }
        }
        // Finally, put the photo together. Start from the top left tile, append everything to its right
        // and then repeat but with the tile below. Keep going until you hit the bottom of the photo.
        var head = graph.filter { it.value[0] == -1L && it.value[2] == -1L }.keys.first()
        val photo = mutableListOf<MutableList<String>>()
        while (head != -1L) {
            photo.add(removeEdges(fitTiles[head]!!).toMutableList())
            var node = graph[head]!![3]
            while (node != -1L) {
                val nodeTile = removeEdges(fitTiles[node]!!)
                for (i in nodeTile.indices) {
                    photo.last()[i] += nodeTile[i]
                }
                node = graph[node]!![3]
            }
            head = graph[head]!![1]
        }
        return photo.flatten()
    }

    fun calculateWaterRoughness(photo: List<String>): Int {
        val seaMonsterRegex = listOf(
                """..................#.""".toRegex(),
                """#....##....##....###""".toRegex(),
                """.#..#..#..#..#..#...""".toRegex()
        )
        val seaMonsterSize = 15
        val totalRoughness = photo.map { it.count { tile -> tile == '#' } }.sum()
        for ((flip, rotate) in MyMath.product(listOf(0, 1, 2), listOf(0, 1, 2, 3))) {
            val testPhoto = flipTile(rotateTile(photo, rotate), flip)
            var currentRoughness = totalRoughness
            for (i in 0 until testPhoto.size - 3) {
                for (j in 0 until testPhoto[0].length - 20) {
                    val slice = testPhoto.subList(i, i + 3).map { it.slice(j until j + 20) }
                    if (seaMonsterRegex.zip(slice).all { (reg, sl) -> reg.matches(sl) })
                        currentRoughness -= seaMonsterSize
                }
            }
            if (totalRoughness > currentRoughness)
                return currentRoughness
        }
        throw Error("couldn't find any monsters")
    }
}

fun main() {
    val tiles = Resources.readFileAsList("input.txt").splitByEmptyLine().associateBy({ it[0].substring(5, it[0].length - 1).toLong() }, { it.subList(1, it.size) })

    // Part 1
    val edges = tiles.mapValues { Day20.getEdges(it.value) }
    val neighbors = Day20.getNeighbors(edges)
    println(neighbors.filter { it.value.size == 2 }.keys.reduce { acc, l -> acc * l })
    // Part 2
    val photo = Day20.putTogetherPhoto(tiles, neighbors)
    println(Day20.calculateWaterRoughness(photo))
}