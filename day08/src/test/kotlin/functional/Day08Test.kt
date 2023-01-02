package functional

import ceejay.advent.day08.part1
import ceejay.advent.day08.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day08Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(1_854, actual)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(527_340, actual)
    }
}