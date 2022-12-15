package ceejay.advent.day15

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1(): Int = InputFile.withLines {
    val sensorBeacons = SensorParser.parse(this)
    Grid(sensorBeacons).countExcludedInRow(2_000_000)
}

fun part2(): Int = InputFile.withLines {
    TODO()
}