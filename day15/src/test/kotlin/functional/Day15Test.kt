package functional

import ceejay.advent.day15.part1
import ceejay.advent.day15.part2
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test

object Day15Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        then(actual).isEqualTo(5_142_231)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        then(actual).isEqualTo(10_884_459_367_718L)
    }
}