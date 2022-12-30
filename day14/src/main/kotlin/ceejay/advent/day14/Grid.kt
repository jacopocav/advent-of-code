package ceejay.advent.day14

import ceejay.advent.day14.Cave.Companion.Cell
import ceejay.advent.day14.Cave.Companion.Cell.*

class Grid {
    private val nonTraversableColumns = mutableMapOf<Int, MutableMap<Int, Cell>>()
    private var floorRow: Int? = null
    val hasFloor: Boolean
        get() = floorRow != null

    fun addFloor(distanceFromLowestPoint: Int) {
        floorRow = nonTraversableColumns.values.maxOf { it.keys.max() } + distanceFromLowestPoint
    }

    operator fun get(column: Int, row: Int): Cell =
        if (row == floorRow) ROCK
        else nonTraversableColumns[column]?.get(row) ?: AIR

    operator fun set(column: Int, row: Int, value: Cell) {
        nonTraversableColumns.computeIfAbsent(column) { mutableMapOf() }

        when (value) {
            AIR -> nonTraversableColumns[column]!! -= row
            ROCK, SAND -> nonTraversableColumns[column]!![row] = value
        }
    }

    fun nextNonAirCellRow(column: Int, row: Int): Int = nonTraversableColumns[column]
        ?.keys
        ?.filter { it > row }
        ?.minOrNull()
        ?: floorRow
        ?: throw NoSuchElementException()

    fun downLeftFrom(column: Int, row: Int): Cell = this[column - 1, row + 1]
    fun downRightFrom(column: Int, row: Int): Cell = this[column + 1, row + 1]

    fun areAllAirBelow(column: Int, row: Int): Boolean = !hasFloor
        && (nonTraversableColumns[column]
        ?.none { (r, _) -> r > row }
        ?: true)

    override fun toString(): String {
        val minColumn = nonTraversableColumns.keys.min()
        val minRow = 0

        val maxColumn = nonTraversableColumns.keys.max()
        val maxRow = floorRow ?: nonTraversableColumns.values.maxOf { it.keys.max() }

        val maxRowDigits = maxRow.toString().length

        val firstLine = " ".repeat(maxRowDigits + 2) +
            "$minColumn...".padMiddle(maxColumn - minColumn + 1, "...$maxColumn")

        return (minRow..maxRow)
            .map { row -> (minColumn..maxColumn).map { this[it, row].char } }
            .mapIndexed { index, row ->
                index.toString().padStart(maxRowDigits + 1) + " " + row.joinToString("")
            }
            .joinToString("\n")
            .let { "$firstLine\n$it" }
    }

    companion object {
        private fun String.padMiddle(length: Int, end: String, padChar: Char = ' '): String =
            padEnd(length - end.length, padChar) + end
    }
}