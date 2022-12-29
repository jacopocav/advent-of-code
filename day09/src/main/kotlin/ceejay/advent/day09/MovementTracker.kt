package ceejay.advent.day09

import kotlin.math.sign

class MovementTracker(val knots: Int, val debug: Boolean = false) {
    private val knotPositions = MutableList(knots) { Position.START }
    private val visitedByTail = mutableSetOf(Position.START)
    private var cnt = 0

    fun trackMovements(unitMovements: Sequence<Direction>) {
        cnt = 0
        unitMovements.forEach {
            moveOne(it)
            cnt++
        }
    }

    fun getPositionsVisitedByTail(): Set<Position> {
        return visitedByTail.toSet()
    }

    private fun moveOne(direction: Direction) {
        knotPositions[0] += direction.diff

        for (i in 1 until knots) {
            if (current(i) isAdjacentTo previous(i)) {
                printDebugInfo()
                return
            }

            followPrevious(i)
            assert(current(i) isAdjacentTo previous(i))
        }

        visitedByTail += knotPositions.last()
        printDebugInfo()
    }

    private fun current(index: Int): Position {
        return knotPositions[index]
    }

    private fun previous(index: Int): Position {
        return knotPositions[index - 1]
    }

    private fun Position.toCode(): String {
        return when (this) {
            Position.START -> "s"
            in knotPositions -> knotPositions.indexOf(this)
                .let { if (it > 0) it.toString() else "H" }

            else -> "."
        }
    }

    private fun printDebugInfo() {
        if (debug) {
            val maxRow = knotPositions.maxOf { it.row }
            val minRow = knotPositions.minOf { it.row }
            val maxColumn = knotPositions.maxOf { it.column }
            val minColumn = knotPositions.minOf { it.column }

            (maxRow downTo minRow).map { row ->
                (minColumn..maxColumn).joinToString(separator = "") { Position(row, it).toCode() }
            }.forEach { println(it) }
            println()
        }
    }

    private fun followPrevious(index: Int) {
        val current = current(index)
        val previous = previous(index)

        val rowDiff = (previous.row - current.row).sign
        val colDiff = (previous.column - current.column).sign

        // Moves in the direction of the previous knot by 1 horizontal/vertical/diagonal step
        val step = Position(rowDiff, colDiff)

        knotPositions[index] += step
    }
}