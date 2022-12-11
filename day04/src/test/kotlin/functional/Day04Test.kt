package functional

import ceejay.advent.day04.part1
import ceejay.advent.day04.part2
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test

object Day04Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        then(actual).isEqualTo(475)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        then(actual).isEqualTo(825)
    }
}