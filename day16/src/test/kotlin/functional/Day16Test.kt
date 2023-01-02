package functional

import ceejay.advent.day16.part1
import ceejay.advent.day16.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day16Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(1716, actual.result)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(2504, actual.result)
    }
}