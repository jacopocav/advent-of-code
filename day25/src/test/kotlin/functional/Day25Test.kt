package functional

import ceejay.advent.day25.part1
import kotlin.test.assertEquals
import kotlin.test.Test

class Day25Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals("2=001=-2=--0212-22-2", actual.result)
    }
}