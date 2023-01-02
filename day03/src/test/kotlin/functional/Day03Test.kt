package functional

import ceejay.advent.day03.part1
import ceejay.advent.day03.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(7766, actual)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(2415, actual)
    }
}