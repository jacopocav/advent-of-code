package functional

import ceejay.advent.day23.part1
import ceejay.advent.day23.part2
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test

object Day23Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        then(actual.result).isEqualTo(3882)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        then(actual.result).isEqualTo(1116)
    }
}