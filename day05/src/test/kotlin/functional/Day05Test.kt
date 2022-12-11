package functional

import ceejay.advent.day05.part1
import ceejay.advent.day05.part2
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test

object Day05Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        then(actual).isEqualTo("FZCMJCRHZ")
    }

    @Test
    fun part2Test() {
        // when
        val actual = part2()

        // then
        then(actual).isEqualTo("JSDHQMZGF")
    }
}