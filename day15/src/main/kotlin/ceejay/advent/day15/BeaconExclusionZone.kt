package ceejay.advent.day15

import ceejay.advent.util.InputFile
import ceejay.advent.util.Vector2D.Companion.vector
import ceejay.advent.util.timed

fun main() {
    part1().also {
        println("Part 1 Result: $it")
    }
    part2().also {
        println("Part 2 Result: $it")
    }
}

fun part1() = InputFile.useLines { lines ->
    timed {
        val sensorBeacons = SensorParser.parse(lines)
        val grid = Grid(sensorBeacons)

        grid.countExcludedInRow(2_000_000)
    }
}

fun part2() = InputFile.useLines { lines ->
    timed {
        val min = 0
        val max = 4_000_000

        val sensorBeacons = SensorParser.parse(lines)
        val grid = Grid(sensorBeacons)

        val hiddenBeacon = grid.findFirstEmptyCellInRectangle(
            minCoordinates = vector(min, min),
            maxCoordinates = vector(max, max)
        )

        hiddenBeacon.x * 4_000_000L + hiddenBeacon.y
    }
}