package ceejay.advent.day08

import ceejay.advent.util.InputFile

fun main() {
    println(Part2())
}

object Part2 {
    operator fun invoke(): Int {
        val input = InputFile("08-Trees.txt")

        val grid = TreeGrid.parse(input)

        return grid.allCoordinates()
            .maxOf { grid.scenicScoreOf(it) }
    }
}