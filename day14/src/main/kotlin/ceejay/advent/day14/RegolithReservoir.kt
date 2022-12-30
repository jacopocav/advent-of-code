package ceejay.advent.day14

import ceejay.advent.util.InputFile
import ceejay.advent.util.Vector2D.Companion.vector
import ceejay.advent.util.timed

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1() = InputFile.withLines {
    timed {
        val sandSpawnPoint = vector(500, 0)
        CaveParser.parse(this, sandSpawnPoint)
            .countSandUnitsUntilAbyssReached()
    }
}

fun part2() = InputFile.withLines {
    timed {
        val sandSpawnPoint = vector(500, 0)

        CaveParser.parse(this, sandSpawnPoint, floorDistance = 2)
            .countCellsReachableBySand()
    }
}