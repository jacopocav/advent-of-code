package functional

import ceejay.advent.day24.part1
import ceejay.advent.day24.part2
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test

object Day24Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        then(actual.result).isEqualTo(257)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        then(actual.result).isEqualTo(828)
    }
}