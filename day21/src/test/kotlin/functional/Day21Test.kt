package functional

import ceejay.advent.day21.part1
import ceejay.advent.day21.part2
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test

object Day21Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        then(actual.result).isEqualTo(170_237_589_447_588)
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        then(actual.result).isEqualTo(3_712_643_961_892)
    }
}