package twenty

import util.Resources

object Day9 {
    // From Day 1
    fun twoSum(nums: MutableList<Long>, target: Long): Boolean {
        nums.sort()
        var l = 0
        var r = nums.size - 1
        while (l < r) {
            val sum = nums[l] + nums[r]
            when {
                sum == target -> return true
                sum < target -> l++
                else -> r--
            }
        }
        return false
    }

    fun firstNumberWithoutProperty(input: List<Long>): Long {
        val q = ArrayDeque<Long>()
        input.take(25).forEach { q.add(it) }
        for (num in input.subList(25, input.size)) {
            if (!twoSum(q.toMutableList(), num)) {
                return num
            }
            q.removeFirst()
            q.add(num)
        }
        return -1
    }

    // Sliding window
    fun findEncryptionWeakness(input: List<Long>, target: Long): Long {
        var l = 0
        var sum = 0L
        for (r in input.indices) {
            while (sum > target) {
                sum -= input[l]
                l++
            }
            if (sum == target) {
                val view = input.subList(l, r)
                return view.maxOrNull()!! + view.minOrNull()!!
            }
            sum += input[r]
        }
        return -1
    }
}

fun main() {
    val input = Resources.readFileAsList("twenty/day9.txt").map { it.toLong() }
    val part1 = Day9.firstNumberWithoutProperty(input)
    println(part1)
    println(Day9.findEncryptionWeakness(input, part1))
}