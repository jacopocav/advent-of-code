package functional

import ceejay.advent.day14.part1
import ceejay.advent.day14.part2
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test

object Day14Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        then(actual).isEqualTo(698)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        then(actual).isEqualTo(28594)
    }
}