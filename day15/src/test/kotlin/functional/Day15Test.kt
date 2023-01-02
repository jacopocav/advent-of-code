package functional

import ceejay.advent.day15.part1
import ceejay.advent.day15.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day15Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(5_142_231, actual.result)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(10_884_459_367_718L, actual.result)
    }
}