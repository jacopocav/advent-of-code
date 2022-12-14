package ceejay.advent.day14

import ceejay.advent.day14.Cave.Companion.Cell.AIR
import ceejay.advent.day14.Cave.Companion.Cell.SAND
import ceejay.advent.day14.Cave.Companion.Status.IN_ABYSS
import ceejay.advent.day14.Cave.Companion.Status.RESTING

internal class Cave(private val grid: Grid, val sandSpawnPoint: Pair<Int, Int>) {
    var spawnedSandUnits = 0
        private set

    inline fun spawnSandUntil(predicate: (SandResult) -> Boolean) {
        var result = SandUnit().fall()
        while (!predicate(result)) {
            result = SandUnit().fall()
        }
    }

    inner class SandUnit {
        init {
            spawnedSandUnits++
        }

        private var column = sandSpawnPoint.first
        private var row = sandSpawnPoint.second

        fun fall(): SandResult {
            if (grid.areAllAirBelow(column, row)) {
                return SandResult(IN_ABYSS, column, Int.MAX_VALUE)
            }

            val nextNonAirRow = grid.nextNonAirCellRow(column, row)
            assert(nextNonAirRow > row)

            row = nextNonAirRow - 1

            return when {
                grid.downLeftFrom(column, row) == AIR -> {
                    column--
                    row++
                    fall()
                }

                grid.downRightFrom(column, row) == AIR -> {
                    column++
                    row++
                    fall()
                }

                else -> {
                    grid[column, row] = SAND
                    SandResult(RESTING, column, row)
                }
            }
        }

    }

    companion object {
        data class SandResult(val status: Status, val column: Int, val row: Int)

        enum class Status {
            RESTING, IN_ABYSS
        }

        enum class Cell(val char: Char) {
            AIR('.'), ROCK('#'), SAND('o')
        }
    }
}
