package twenty

import util.Resources

object Day25 {
    fun handshake(value: Long, subjectNumber: Long) = (value * subjectNumber) % 20201227

    fun calculateLoopSize(publicKey: Long): Int {
        var transformed = 1L
        var loopSize = 0
        while (transformed != publicKey) {
            transformed = handshake(transformed, 7)
            loopSize++
        }
        return loopSize
    }

    fun crack(cardPublicKey: Long, doorPublicKey: Long): Long {
        var privateKey = 1L
        repeat(calculateLoopSize(cardPublicKey)) {
            privateKey = handshake(privateKey, doorPublicKey)
        }
        return privateKey
    }
}

fun main() {
    val (cpk, dpk) = Resources.readFileAsList("input.txt").map { it.toLong() }
    println(Day25.crack(cpk, dpk))
}