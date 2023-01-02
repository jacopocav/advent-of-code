package functional

import ceejay.advent.day06.part1
import ceejay.advent.day06.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day06Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(1262, actual)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(3444, actual)
    }
}