package util

import kotlin.math.absoluteValue

object MyMath {
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

    // A hastily written copy of itertools.product from Python
    fun product(vararg strings: String, count: Int = 1): List<String> {
        val pools = mutableListOf<String>()
        repeat(count) {
            pools.addAll(strings)
        }
        var result = mutableListOf("")
        for (pool in pools) {
            val newResult =  mutableListOf<String>()
            for (x in result) {
                for (y in pool) {
                    newResult.add(x + y)
                }
            }
            result = newResult
        }
        return result
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