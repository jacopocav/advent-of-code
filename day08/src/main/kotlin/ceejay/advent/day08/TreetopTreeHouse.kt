package ceejay.advent.day08

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1(): Int {
    val grid = InputFile()
        .let { TreeGrid.parse(it) }

    return grid.allCoordinates()
        .count { grid.isVisible(it) }
}

fun part2(): Int {
    val grid = InputFile()
        .let { TreeGrid.parse(it) }

    return grid.allCoordinates()
        .maxOf { grid.scenicScoreOf(it) }
}