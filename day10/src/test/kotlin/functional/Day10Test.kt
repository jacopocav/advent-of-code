package functional

import ceejay.advent.day10.part1
import ceejay.advent.day10.part2
import kotlin.test.assertEquals
import kotlin.test.Test

class Day10Test {

    @Test
    fun part1Test() {
        // when
        val actual = part1()

        // then
        assertEquals(14_320, actual)
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
        assertEquals(expected, actual)
    }
}