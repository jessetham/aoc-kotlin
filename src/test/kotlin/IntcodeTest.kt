import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.Resources
import nineteen.*

internal class IntcodeTest {
    @Test
    fun `verify all Intcode problems`() {
        val day2 = Resources.readFileAsIntcodeInstructions("nineteen/day2.txt")
        assertEquals(Day2.part1(day2), 3409710)
        assertEquals(Day2.part2(day2, 19690720), 7912)

        val day5 = Resources.readFileAsIntcodeInstructions("nineteen/day5.txt")
        assertEquals(Day5.part1(day5), 16574641)
        assertEquals(Day5.part2(day5), 15163975)

        val day7 = Resources.readFileAsIntcodeInstructions("nineteen/day7.txt")
        assertEquals(Day7.part1(day7), 38500)
        assertEquals(Day7.part2(day7), 33660560)

        val day9 = Resources.readFileAsIntcodeInstructions("nineteen/day9.txt")
        assertEquals(Day9.part(day9, 1L), 2955820355)
        assertEquals(Day9.part(day9, 2L), 46643)

        val day11 = Resources.readFileAsIntcodeInstructions("nineteen/day11.txt")
        assertEquals(Day11.part1(day11), 1883)

        val day13 = Resources.readFileAsIntcodeInstructions("nineteen/day13.txt")
        assertEquals(Day13.part1(day13), 230)
        assertEquals(Day13.part2(day13), 11140)
    }
}