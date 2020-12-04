package util

class DisjointSet(val size: Int) {
    val parents = MutableList(size) { it }
    val sizes = MutableList(size) { 1 }

    // No path compression
    tailrec fun find(x: Int): Int {
        if (parents[x] == x) {
            return x
        }
        return find(parents[x])
    }

    // Union by size
    fun union(x: Int, y: Int) {
        val (rootX, rootY) = (find(x) to find(y)).let { (a, b) ->
            if (sizes[b] <= sizes[a]) {
                a to b
            } else {
                b to a
            }
        }
        if (rootX != rootY) {
            parents[rootY] = rootX
            sizes[rootX] += sizes[rootY]
        }
    }
}