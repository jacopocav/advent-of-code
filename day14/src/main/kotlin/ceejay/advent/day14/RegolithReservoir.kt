package ceejay.advent.day14

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
//    println("Part 2 Result: ${part2()}")
}

fun part1(): Int = InputFile.withLines {
    CaveParser.parse(this)
        .apply { spawnSandUntilAbyssReached() }
        .spawnedSandUnits - 1
}

fun part2(): Int = InputFile.withLines {
    TODO()
}