package functional

import ceejay.advent.day17.part1
import ceejay.advent.day17.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day17Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(3059, actual)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(1_500_874_635_587, actual)
    }
}