package functional

import ceejay.advent.day04.part1
import ceejay.advent.day04.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day04Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(475, actual)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(825, actual)
    }
}