package twenty

import util.Resources

object Day1 {
    fun part1(nums: MutableList<Int>): Int {
        nums.sort()
        var l = 0
        var r = nums.size - 1
        while (l < r) {
            val sum = nums[l] + nums[r]
            if (sum == 2020) {
                return nums[l] * nums[r]
            } else if (sum < 2020) {
                l++
            } else {
                r--
            }
        }
        return -1
    }

    fun part2(nums: MutableList<Int>): Int {
        nums.sort()
        var l = 0
        var r = nums.size - 1
        start@ while (l < r) {
            for (m in l + 1 until r) {
                val sum = nums[l] + nums[m] + nums[r]
                if (sum == 2020) {
                    return nums[l] * nums[m] * nums[r]
                } else if (sum > 2020) {
                    r--
                    continue@start
                }
            }
            l++
        }
        return -1
    }
}

fun main() {
    val input = Resources.readFileAsList("twenty/day1.txt").map { it.toInt() }
    println(Day1.part1(input.toMutableList()))
    println(Day1.part2(input.toMutableList()))
}