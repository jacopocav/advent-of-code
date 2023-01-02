package functional

import ceejay.advent.day09.part1
import ceejay.advent.day09.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day09Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(6266, actual)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(2369, actual)
    }
}