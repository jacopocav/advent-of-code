package ceejay.advent.day14

import ceejay.advent.day14.Cave.Companion.SandResult
import ceejay.advent.day14.Cave.Companion.Status.IN_ABYSS
import ceejay.advent.day14.Cave.Companion.Status.RESTING
import ceejay.advent.day14.CaveParser.Coordinates
import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1(): Int = InputFile.withLines {
    val sandSpawnPoint = Coordinates(500, 0)
    CaveParser.parse(this, sandSpawnPoint)
        .apply { spawnSandUntil { it.status == IN_ABYSS } }
        .spawnedSandUnits - 1
}

fun part2(): Int = InputFile.withLines {
    val sandSpawnPoint = Coordinates(500, 0)
    val expectedResult = SandResult(RESTING, sandSpawnPoint.column, sandSpawnPoint.row)

    CaveParser.parse(this, sandSpawnPoint, floorDistance = 2)
        .apply { spawnSandUntil { it == expectedResult } }
        .spawnedSandUnits
}