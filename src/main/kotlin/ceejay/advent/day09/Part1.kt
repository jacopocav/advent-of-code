package ceejay.advent.day09

import ceejay.advent.util.InputFile

fun main() {
    println(Part1())
}

object Part1 {
    operator fun invoke(): Int {
        val input = InputFile("09-Ropes.txt")

        val unitMovements = input.lines()
            .filter { it.isNotBlank() }
            .map { Movement.parse(it) }
            .flatMap { it.toUnitMovements() }

        return with(MovementTracker()) {
            trackMovements(unitMovements)
            getPositionsVisitedByTail()
        }.count()
    }
}