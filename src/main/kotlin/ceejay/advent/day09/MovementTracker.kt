package ceejay.advent.day09

import kotlin.math.sign

class MovementTracker(val debug: Boolean = false) {
    private var headPosition = Position.START
    private var tailPosition = Position.START
    private val visited = mutableSetOf(Position.START)

    fun trackMovements(unitMovements: List<Direction>) {
        visited.clear()
        visited += Position.START
        unitMovements.forEach { moveOne(it) }
    }

    fun getPositionsVisitedByTail(): Set<Position> {
        return visited.toSet()
    }

    private fun moveOne(direction: Direction) {
        headPosition += direction.diff
        if (tailPosition isAdjacentTo headPosition) {
            printDebugInfo(direction)
            return
        }

        followHead(direction)
        assert(tailPosition isAdjacentTo headPosition)

        printDebugInfo(direction)
    }

    private fun printDebugInfo(direction: Direction) {
        if (debug) {
            println("--- Movement: $direction")
            println("H: $headPosition")
            println("T: $tailPosition")
            println()
        }
    }

    private fun followHead(direction: Direction) {
        tailPosition += if (headPosition.row == tailPosition.row
            || headPosition.column == tailPosition.column
        ) {
            // horizontal/vertical movement
            direction.diff
        } else {
            // diagonal movement
            val rowDiff = (headPosition.row - tailPosition.row).sign
            val colDiff = (headPosition.column - tailPosition.column).sign

            Position(rowDiff, colDiff)
        }

        visited += tailPosition
    }
}