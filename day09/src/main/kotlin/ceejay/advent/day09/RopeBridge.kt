package ceejay.advent.day09

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1(): Int = run(2)

fun part2(): Int = run(10)

private fun run(knotNum: Int): Int {
    return InputFile.withLines {
        val unitMovements = flatMap { MovementParser.parseLine(it) }

        with(MovementTracker(knotNum)) {
            trackMovements(unitMovements)
            getPositionsVisitedByTail()
        }.count()
    }
}