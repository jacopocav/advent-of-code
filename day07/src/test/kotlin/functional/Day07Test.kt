package functional

import ceejay.advent.day07.Part1
import ceejay.advent.day07.Part2
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test

object Day07Test {

    @Test
    fun part1Test() {
        // when
        val actual = Part1()

        // then
        then(actual).isEqualTo(1_348_005)
    }

    @Test
    fun part2Test() {
        // when
        val actual = Part2()

        // then
        then(actual).isEqualTo(12_785_886)
    }
}