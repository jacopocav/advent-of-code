package ceejay.advent.day14

import ceejay.advent.day14.Cave.Companion.Cell.ROCK
import kotlin.math.max
import kotlin.math.min

object CaveParser {
    fun parse(
        lines: Sequence<String>,
        sandSpawnPoint: Coordinates,
        floorDistance: Int? = null
    ): Cave {
        val grid = Grid()

        lines.forEach { it.parseRockPath().into(grid) }

        floorDistance?.let { grid.addFloor(it) }

        return Cave(grid, sandSpawnPoint.toPair())
    }

    private fun String.parseRockPath(): Sequence<Coordinates> {
        val path = splitToSequence("->")
            .map { it.trim() }
            .map { it.split(",") }
            .map { (col, row) -> Coordinates(col.toInt(), row.toInt()) }

        return path.zipWithNext()
            .flatMap { (curr, next) -> curr.stepsTo(next) }
    }

    private fun Sequence<Coordinates>.into(grid: Grid) {
        forEach { (column, row) -> grid[column, row] = ROCK }
    }

    data class Coordinates(val column: Int, val row: Int) {
        fun toPair() = Pair(column, row)
        fun stepsTo(other: Coordinates): Sequence<Coordinates> =
            when {
                column == other.column ->
                    (min(row, other.row)..max(row, other.row))
                        .asSequence()
                        .map { Coordinates(column, it) }

                row == other.row ->
                    (min(column, other.column)..max(column, other.column))
                        .asSequence()
                        .map { Coordinates(it, row) }

                else -> throw IllegalArgumentException("cannot step diagonally")
            }
    }
}