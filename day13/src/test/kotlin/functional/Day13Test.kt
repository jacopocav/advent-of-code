package functional

import ceejay.advent.day13.part1
import ceejay.advent.day13.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day13Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(5659, actual)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(22110, actual)
    }
}