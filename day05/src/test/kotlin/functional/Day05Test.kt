package functional

import ceejay.advent.day05.part1
import ceejay.advent.day05.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day05Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals("FZCMJCRHZ", actual)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals("JSDHQMZGF", actual)
    }
}