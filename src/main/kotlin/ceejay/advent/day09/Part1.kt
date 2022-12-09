package ceejay.advent.day09

import ceejay.advent.util.InputFile

fun main() {
    println(Part1())
}

object Part1 {
    operator fun invoke(): Int {
        val input = InputFile("09-Ropes.txt")

        val unitMovements = input.lineSequence()
            .filter { it.isNotBlank() }
            .flatMap { MovementParser.parseLine(it) }

        return with(MovementTracker(2)) {
            trackMovements(unitMovements)
            getPositionsVisitedByTail()
        }.count()
    }
}