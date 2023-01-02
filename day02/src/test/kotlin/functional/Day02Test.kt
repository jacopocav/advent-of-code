package functional

import ceejay.advent.day02.part1
import ceejay.advent.day02.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day02Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(14_375, actual)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(10_274, actual)
    }
}