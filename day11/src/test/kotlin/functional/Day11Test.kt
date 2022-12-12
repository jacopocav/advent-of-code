package functional

import ceejay.advent.day11.part1
import ceejay.advent.day11.part2
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test

object Day11Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        then(actual).isEqualTo(88_208)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        then(actual).isEqualTo(0) // TODO replace with real result
    }
}