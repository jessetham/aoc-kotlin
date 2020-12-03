package util

import kotlin.math.absoluteValue

object Math {
    // Heap's algorithm, taken from Wikipedia
    fun <T> permutations(candidates: List<T>): Sequence<List<T>> = sequence {
        val n = candidates.size
        val c = MutableList(n) { 0 }
        yield(candidates)

        val mutableCandidates = candidates.toMutableList()
        var i = 0
        while (i < n) {
            if (c[i] < i) {
                if (i % 2 == 0) {
                    mutableCandidates[0] = mutableCandidates[i].also { mutableCandidates[i] = mutableCandidates[0] }
                } else {
                    mutableCandidates[c[i]] =
                        mutableCandidates[i].also { mutableCandidates[i] = mutableCandidates[c[i]] }
                }
                yield(mutableCandidates)
                c[i]++
                i = 0
            } else {
                c[i] = 0
                i++
            }
        }
    }

    // Euclidean algorithm
    tailrec fun gcd(a: Int, b: Int): Int {
        if (b == 0) {
            return a
        }
        return gcd(b, a % b)
    }

    fun lcm(vararg nums: Int) = nums.reduce { acc, num -> (acc * num).absoluteValue / gcd(acc, num) }

    tailrec fun gcd(a: Long, b: Long): Long {
        if (b == 0L) {
            return a
        }
        return gcd(b, a % b)
    }

    fun lcm(vararg nums: Long) = nums.reduce { acc, num -> (acc * num).absoluteValue / gcd(acc, num) }
}