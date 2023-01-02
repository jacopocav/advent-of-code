package functional

import ceejay.advent.day12.part1
import ceejay.advent.day12.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day12Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(490, actual)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(488, actual)
    }
}