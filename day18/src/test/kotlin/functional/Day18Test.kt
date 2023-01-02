package functional

import ceejay.advent.day18.part1
import ceejay.advent.day18.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day18Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(3500, actual)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(2048, actual)
    }
}