package ceejay.advent.day14

import ceejay.advent.day14.Cave.Companion.Cell.ROCK
import ceejay.advent.util.Vector2D
import ceejay.advent.util.Vector2D.Companion.vector
import kotlin.math.max
import kotlin.math.min

object CaveParser {
    fun parse(
        lines: Sequence<String>,
        sandSpawnPoint: Vector2D,
        floorDistance: Int? = null,
    ): Cave {
        val grid = Grid()

        lines.forEach { it.parseRockPath().into(grid) }

        floorDistance?.let { grid.addFloor(it) }

        return Cave(grid, sandSpawnPoint)
    }

    private fun String.parseRockPath(): Sequence<Vector2D> {
        val path = splitToSequence("->")
            .map { it.trim() }
            .map { it.split(",") }
            .map { (col, row) -> vector(col.toInt(), row.toInt()) }

        return path.zipWithNext()
            .flatMap { (curr, next) -> curr.stepsTo(next) }
    }

    private fun Sequence<Vector2D>.into(grid: Grid) {
        forEach { (column, row) -> grid[column, row] = ROCK }
    }

    private fun Vector2D.stepsTo(other: Vector2D): Sequence<Vector2D> =
        when {
            x == other.x ->
                (min(y, other.y)..max(y, other.y))
                    .asSequence()
                    .map { vector(x, it) }

            y == other.y ->
                (min(x, other.x)..max(x, other.x))
                    .asSequence()
                    .map { vector(it, y) }

            else -> throw IllegalArgumentException("cannot step diagonally")
        }
}