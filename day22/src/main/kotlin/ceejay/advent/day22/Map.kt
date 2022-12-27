package ceejay.advent.day22

import ceejay.advent.day22.Cell.*
import ceejay.advent.day22.Direction.*

data class Position(val x: Int, val y: Int, val direction: Direction) {
    fun advance(steps: Int = 1): Position = when (direction) {
        UP -> copy(y = y - steps)
        DOWN -> copy(y = y + steps)
        LEFT -> copy(x = x - steps)
        RIGHT -> copy(x = x + steps)
    }
}

class Map(
    private val cells: List<List<Cell>>,
    private val initialPosition: Position,
) {
    init {
        require(cells[initialPosition.y][initialPosition.x] == FREE) {
            "cannot start on wall or void cell"
        }
        require(cells.isNotEmpty()) {
            "map cannot be empty"
        }
        require(cells.first().size.let { size -> cells.all { it.size == size } }) {
            "all rows must have the same size"
        }
    }

    private val rowCount = cells.size
    private val colCount = cells.first().size

    private val rowOffsets =
        cells.map { row -> row.indexOfFirst { it != VOID } to row.indexOfLast { it != VOID } }

    private val columnOffsets = buildList {
        for (col in cells.first().indices) {
            val column = cells.map { it[col] }
            add(column.indexOfFirst { it != VOID } to column.indexOfLast { it != VOID })
        }
    }

    fun run(actions: List<Action>): Position {
        var position = initialPosition

        for (action in actions) {
            position = when (action) {
                is Turn -> turn(position, action.direction)
                is Move -> move(position, action.steps)
            }
        }

        return position
    }

    private fun turn(
        position: Position,
        direction: Direction,
    ): Position {
        val newDirection =
            if (direction == LEFT) position.direction.turnLeft()
            else position.direction.turnRight()

        return position.copy(direction = newDirection)
    }

    private fun move(initialPosition: Position, steps: Int): Position {
        var current = initialPosition
        var next = initialPosition
        for (step in 0..steps) {
            current = next
            next = current.advance()
            if (next.isOutOfBounds()) {
                next = next.wrapAround()
            }
            when (cells[next]) {
                WALL -> break
                FREE -> continue
                VOID -> {
                    next = next.wrapAround()
                    assert(cells[next] != VOID)
                    if (cells[next] == WALL) break
                }
            }
        }
        return current
    }

    private fun Position.isOutOfBounds() = x !in 0 until colCount
        || y !in 0 until rowCount

    private fun Position.wrapAround(): Position = when (direction) {
        UP -> copy(y = columnOffsets[x].second)
        DOWN -> copy(y = columnOffsets[x].first)
        LEFT -> copy(x = rowOffsets[y].second)
        RIGHT -> copy(x = rowOffsets[y].first)
    }

    private operator fun List<List<Cell>>.get(position: Position): Cell =
        this[position.y][position.x]
}