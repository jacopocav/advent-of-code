package ceejay.advent.day09

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

internal fun part1(): Int {
    return InputFile.withLines {
        val unitMovements = filter { it.isNotBlank() }
            .flatMap { MovementParser.parseLine(it) }

        with(MovementTracker(2)) {
            trackMovements(unitMovements)
            getPositionsVisitedByTail()
        }.count()
    }
}

fun part2(): Int {
    return InputFile.withLines {
        val unitMovements = filter { it.isNotBlank() }
            .flatMap { MovementParser.parseLine(it) }

        with(MovementTracker(10)) {
            trackMovements(unitMovements)
            getPositionsVisitedByTail()
        }.count()
    }
}