package functional

import ceejay.advent.day23.part1
import ceejay.advent.day23.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day23Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(3882, actual.result)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(1116, actual.result)
    }
}