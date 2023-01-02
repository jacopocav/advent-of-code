package functional

import ceejay.advent.day21.part1
import ceejay.advent.day21.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day21Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(170_237_589_447_588, actual.result)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        assertEquals(3_712_643_961_892, actual.result)
    }
}