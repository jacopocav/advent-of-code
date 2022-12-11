package functional

import ceejay.advent.day08.part1
import ceejay.advent.day08.part2
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test

object Day08Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        then(actual).isEqualTo(1_854)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        then(actual).isEqualTo(527_340)
    }
}