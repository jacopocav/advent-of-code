package functional

import ceejay.advent.day18.part1
import ceejay.advent.day18.part2
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

object Day18Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        then(actual).isEqualTo(3500)
    }

    @Test
    @Disabled
    fun part2Test() {
        // when
        val actual = part2()

        // then
        then(actual).isEqualTo(TODO())
    }
}