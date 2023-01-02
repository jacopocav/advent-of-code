package functional

import ceejay.advent.day11.part1
import ceejay.advent.day11.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day11Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(88_208L, actual)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(21_115_867_968L, actual)
    }
}