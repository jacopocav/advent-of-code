package ceejay.advent.day15

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1(): Int = InputFile.useLines { lines ->
    val sensorBeacons = SensorParser.parse(lines)
    val grid = Grid(sensorBeacons)

    grid.countExcludedInRow(2_000_000)
}

fun part2(): Long = InputFile.useLines { lines ->
    val min = 0
    val max = 4_000_000

    val sensorBeacons = SensorParser.parse(lines)
    val grid = Grid(sensorBeacons)

    val hiddenBeacon = grid.findFirstEmptyCellInRectangle(
        minCoordinates = Coordinates(min, min),
        maxCoordinates = Coordinates(max, max)
    )

    hiddenBeacon.column * 4_000_000L + hiddenBeacon.row
}