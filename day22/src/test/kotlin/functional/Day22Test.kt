package functional

import ceejay.advent.day22.part1
import ceejay.advent.day22.part2
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test

object Day22Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        then(actual.result).isEqualTo(66292)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        then(actual.result).isEqualTo(127012)
    }
}