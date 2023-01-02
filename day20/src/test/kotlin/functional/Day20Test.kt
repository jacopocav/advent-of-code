package functional

import ceejay.advent.day20.part1
import ceejay.advent.day20.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day20Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(872, actual.result)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(5_382_459_262_696, actual.result)
    }
}