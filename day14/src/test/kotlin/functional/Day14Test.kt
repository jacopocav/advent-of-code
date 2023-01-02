package functional

import ceejay.advent.day14.part1
import ceejay.advent.day14.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day14Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(698, actual.result)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(28594, actual.result)
    }
}