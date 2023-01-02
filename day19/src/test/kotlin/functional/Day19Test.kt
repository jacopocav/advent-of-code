package functional

import ceejay.advent.day19.part1
import ceejay.advent.day19.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day19Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(1294, actual.result)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(13_640, actual.result)
    }
}