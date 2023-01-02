package functional

import ceejay.advent.day24.part1
import ceejay.advent.day24.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day24Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(257, actual.result)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(828, actual.result)
    }
}