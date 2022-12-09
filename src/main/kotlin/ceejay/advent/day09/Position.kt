package ceejay.advent.day09

import kotlin.math.abs

data class Position(val row: Int, val column: Int) {
    operator fun plus(other: Position): Position {
        return Position(row + other.row, column + other.column)
    }

    infix fun isAdjacentTo(other: Position): Boolean {
        val rowDistance = abs(row - other.row)
        val colDistance = abs(column - other.column)

        // Overlapping
        return this == other
            // Vertically adjacent
            || (row == other.row && colDistance == 1)
            // Horizontally adjacent
            || (column == other.column && rowDistance == 1)
            // Diagonally adjacent
            || (rowDistance == 1 && colDistance == 1)
    }

    override fun toString(): String {
        return "($row, $column)"
    }

    companion object {
        val START = Position(0, 0)
    }
}
