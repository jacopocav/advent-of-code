package functional

import ceejay.advent.day02.part1
import ceejay.advent.day02.part2
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test

object Day02Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        then(actual).isEqualTo(14_375)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        then(actual).isEqualTo(10_274)
    }
}