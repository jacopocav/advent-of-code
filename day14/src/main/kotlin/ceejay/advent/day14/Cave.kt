package ceejay.advent.day14

import ceejay.advent.day14.Cave.Companion.Cell.AIR
import ceejay.advent.day14.Cave.Companion.Cell.SAND
import ceejay.advent.day14.Cave.Companion.Result.REACHED_ABYSS
import ceejay.advent.day14.Cave.Companion.Result.RESTING

internal class Cave(private val grid: Grid, val sandSpawnPoint: Pair<Int, Int>) {
    var spawnedSandUnits = 0
        private set

    fun spawnSandUntilAbyssReached() {
        while (true) {
            if (SandUnit().fall() == REACHED_ABYSS) {
                break
            }
        }
    }

    inner class SandUnit {
        init {
            spawnedSandUnits++
        }

        private var column = sandSpawnPoint.first
        private var row = sandSpawnPoint.second
        fun fall(): Result {
            if (grid.areAllTraversableBelow(column, row)) {
                return REACHED_ABYSS
            }

            val nextNonTraversableRow = grid.nextNonTraversableRow(column, row)
            assert(nextNonTraversableRow > row)

            row = nextNonTraversableRow - 1

            return when {
                grid.downLeft(column, row) == AIR -> {
                    column--
                    row++
                    fall()
                }

                grid.downRight(column, row) == AIR -> {
                    column++
                    row++
                    fall()
                }

                else -> {
                    grid[column, row] = SAND
                    RESTING
                }
            }
        }

    }

    companion object {

        enum class Result {
            RESTING, REACHED_ABYSS
        }

        enum class Cell(val char: Char) {
            AIR('.'), ROCK('#'), SAND('o')
        }
    }
}
