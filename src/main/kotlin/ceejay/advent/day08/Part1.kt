package ceejay.advent.day08

import ceejay.advent.util.InputFile

fun main() {
    println(Part1())
}

object Part1 {
    operator fun invoke(): Int {
        val input = InputFile("08-Trees.txt")
        val grid = TreeGrid.parse(input)

        return grid.allCoordinates()
            .count { grid.isVisible(it) }
    }
}