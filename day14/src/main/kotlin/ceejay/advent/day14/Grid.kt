package ceejay.advent.day14

import ceejay.advent.day14.Cave.Companion.Cell
import ceejay.advent.day14.Cave.Companion.Cell.*

internal class Grid {
    private val nonTraversableCells = mutableMapOf<Int, MutableMap<Int, Cell>>()
        .withDefault { mutableMapOf() }

    operator fun get(column: Int, row: Int): Cell =
        nonTraversableCells[column]?.get(row) ?: AIR

    operator fun set(column: Int, row: Int, value: Cell) {
        nonTraversableCells.computeIfAbsent(column) { mutableMapOf() }

        when (value) {
            AIR -> nonTraversableCells[column]!!.remove(row)
            ROCK, SAND -> nonTraversableCells[column]!![row] = value
        }
    }

    fun nextNonTraversableRow(column: Int, row: Int): Int = nonTraversableCells[column]
        ?.keys
        ?.filter { it > row }
        ?.min()
        ?: throw NoSuchElementException()

    fun downLeft(column: Int, row: Int): Cell = this[column - 1, row + 1]
    fun downRight(column: Int, row: Int): Cell = this[column + 1, row + 1]

    fun areAllTraversableBelow(column: Int, row: Int): Boolean = nonTraversableCells[column]
        ?.none { (r, _) -> r > row }
        ?: true

    override fun toString(): String {
        val minColumn = nonTraversableCells.keys.min()
        val minRow = 0

        val maxColumn = nonTraversableCells.keys.max()
        val maxRow = nonTraversableCells.values.maxOf { it.keys.max() }

        return (minRow..maxRow)
            .map { row -> (minColumn..maxColumn).map { this[it, row].char } }
            .joinToString("\n") { it.joinToString("") }
            .let { "from ($minColumn, $minRow) to ($maxColumn, $maxRow)\n$it" }
    }
}