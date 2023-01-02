package functional

import ceejay.advent.day01.part1
import ceejay.advent.day01.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(71_502, actual)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(208_191, actual)
    }
}