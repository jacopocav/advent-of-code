package functional

import ceejay.advent.day22.part1
import ceejay.advent.day22.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day22Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(66292, actual.result)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(127012, actual.result)
    }
}