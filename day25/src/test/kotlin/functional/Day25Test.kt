package functional

import ceejay.advent.day25.part1
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test

object Day25Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        then(actual.result).isEqualTo("2=001=-2=--0212-22-2")
    }
}