import util.Resources

object Day8 {
    fun part1(sif: SIF) {
        val leastZerosLayer = sif.layers.minByOrNull { layer -> layer.count { it == 0 } }!!
        val numOnes = leastZerosLayer.count { it == 1 }
        val numTwos = leastZerosLayer.count { it == 2 }
        println("The product is ${numOnes * numTwos}")
    }
}

data class SIF(val rawData: String, val width: Int, val height: Int) {
    val layers = rawData.toCharArray().map { Character.getNumericValue(it) }.chunked(width * height)

    fun getPixel(layer: Int, x: Int, y: Int): Int {
        if (layer < 0 || x < 0 || y < 0 || layer >= layers.size || x >= width || y >= height) {
            throw IndexOutOfBoundsException()
        }
        return layers[layer][(y * width) + x]
    }

    fun mergeLayers(): List<List<Int>> {
        val merged = MutableList(height) { MutableList(width) { 2 } }
        for (y in merged.indices) {
            for (x in merged[y].indices) {
                for (l in layers.indices) {
                    val pixel = getPixel(l, x, y)
                    if (pixel != 2) {
                        merged[y][x] = pixel
                        break
                    }
                }
            }
        }
        return merged
    }

    fun showImage() {
        val mapping = mapOf(
            0 to " ", 1 to "#", 2 to "!"
        )
        val formatted = mergeLayers().map { layer -> layer.map { mapping[it] ?: error("unknown color $it") } }
        formatted.forEach {
            println(it.joinToString(""))
        }
    }
}

fun main() {
    val width = 25
    val height = 6
    val rawData = Resources.readFileAsString("nineteen/day8.txt")

    val sif = SIF(rawData, width, height)
    Day8.part1(sif)
    sif.showImage()
}