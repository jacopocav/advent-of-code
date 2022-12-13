package functional

import ceejay.advent.day13.part1
import ceejay.advent.day13.part2
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

object Day13Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        then(actual).isEqualTo(5659)
    }

    @Test
    @Disabled
    fun part2Test() {
        // when
        val actual = part2()

        // then
        then(actual).isEqualTo(0) // TODO replace with correct value
    }
}