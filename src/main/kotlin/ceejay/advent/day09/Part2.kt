package ceejay.advent.day09

import ceejay.advent.util.InputFile

fun main() {
    println(Part2())
}

object Part2 {
    operator fun invoke(): Int {
        val input = InputFile("09-Ropes.txt")

        val unitMovements = input.lineSequence()
            .filter { it.isNotBlank() }
            .flatMap { MovementParser.parseLine(it) }

        return with(MovementTracker(10)) {
            trackMovements(unitMovements)
            getPositionsVisitedByTail()
        }.count()
    }
}