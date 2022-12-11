package functional

import ceejay.advent.day10.part1
import ceejay.advent.day10.part2
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test

object Day10Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        then(actual).isEqualTo(14320)
    }

    @Test
    fun part2Test() {
        // given
        val expected = """
            ###...##..###..###..#..#..##..###....##.
            #..#.#..#.#..#.#..#.#.#..#..#.#..#....#.
            #..#.#....#..#.###..##...#..#.#..#....#.
            ###..#....###..#..#.#.#..####.###.....#.
            #....#..#.#....#..#.#.#..#..#.#....#..#.
            #.....##..#....###..#..#.#..#.#.....##..
        """.trimIndent()

        // when
        val actual = part2(pixelOnChar = '#', pixelOffChar = '.')

        // then
        then(actual).isEqualTo(expected)
    }
}