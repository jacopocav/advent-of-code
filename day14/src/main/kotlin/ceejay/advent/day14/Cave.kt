package ceejay.advent.day14

import ceejay.advent.day14.Cave.Companion.Cell.AIR
import ceejay.advent.day14.Cave.Companion.Cell.SAND
import ceejay.advent.day14.Cave.Companion.Status.IN_ABYSS
import ceejay.advent.day14.Cave.Companion.Status.RESTING
import ceejay.advent.util.Vector2D
import ceejay.advent.util.mutableDequeOf

class Cave(private val grid: Grid, val sandSpawnPoint: Vector2D) {
    var spawnedSandUnits = 0
        private set

    fun countSandUnitsUntilAbyssReached(): Int {
        var result = SandUnit().fall()
        while (result != IN_ABYSS) {
            result = SandUnit().fall()
        }
        return spawnedSandUnits - 1
    }

    fun countCellsReachableBySand(): Int {
        require(grid.hasFloor) { "cave has no floor, reachable cells are infinite" }

        if (grid[sandSpawnPoint.x, sandSpawnPoint.y] != AIR) {
            return 0
        }

        val queue = mutableDequeOf(sandSpawnPoint)

        var count = 0

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            count++

            current.neighbors()
                .filter { it !in queue }
                .let { queue += it }
        }

        return count
    }

    private fun Vector2D.neighbors() = listOf(
        copy(y = y + 1),
        copy(x = x - 1, y = y + 1),
        copy(x = x + 1, y = y + 1),
    ).filter { grid[it.x, it.y] == AIR }

    inner class SandUnit {
        init {
            spawnedSandUnits++
        }

        private var column = sandSpawnPoint.x
        private var row = sandSpawnPoint.y

        tailrec fun fall(): Status {
            if (grid.areAllAirBelow(column, row)) {
                return IN_ABYSS
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
                    RESTING
                }
            }
        }

    }

    companion object {

        enum class Status {
            RESTING, IN_ABYSS
        }

        enum class Cell(val char: Char) {
            AIR('.'), ROCK('#'), SAND('o')
        }
    }
}
