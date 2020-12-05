package util

class Trie(words: List<String>) {
    private class Node {
        val children = mutableMapOf<Char, Node>()
        var word: String? = null
    }

    private val root = Node()

    init {
        words.forEach { word -> insert(word) }
    }

    fun insert(word: String) {
        var node = root
        for (c in word) {
            node = node.children.getOrPut(c, { Node() })
        }
        node.word = word
    }

    fun contains(word: String): Boolean {
        return getNode(word)?.word != null
    }

    fun containsPrefix(prefix: String): Boolean {
        return getNode(prefix) != null
    }

    private fun getNode(word: String): Node? {
        var node = root
        for (c in word) {
            if (!node.children.contains(c)) {
                return null
            }
            node = node.children.getValue(c)
        }
        return node
    }
}