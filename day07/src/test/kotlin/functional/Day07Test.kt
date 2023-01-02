package functional

import ceejay.advent.day07.Part1
import ceejay.advent.day07.Part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day07Test {

    @Test
    fun part1Test() {
        // when
        val actual = Part1()

        // then
        assertEquals(1_348_005, actual)
    }

    @Test
    fun part2Test() {
        // when
        val actual = Part2()

        // then
        assertEquals(12_785_886, actual)
    }
}