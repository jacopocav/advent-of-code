package ceejay.advent.day15

import kotlin.math.abs

internal data class Coordinates(val column: Int, val row: Int) {
    fun manhattanDistanceFrom(other: Coordinates): Int =
        abs(row - other.row) + abs(column - other.column)

    override fun toString() = "($column, $row)"
}