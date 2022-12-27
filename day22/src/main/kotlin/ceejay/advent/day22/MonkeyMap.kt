package ceejay.advent.day22

import ceejay.advent.day22.Cell.FREE
import ceejay.advent.day22.Direction.*
import ceejay.advent.util.InputFile
import ceejay.advent.util.TimedResult
import ceejay.advent.util.removeWhile
import ceejay.advent.util.timed

fun main() {
    part1().also {
        println("Part 1 Result: $it")
    }
//    part2().also {
//        println("Part 2 Result: $it")
//    }
}

fun part1(): TimedResult<Int> = InputFile.withLines {
    timed {
        val lines = toList()
        val mapLines = lines.dropLast(1)
        val actions = lines.takeLast(1).first().parseActions()

        val cells = mapLines.parseCells()
        val map = Map(cells, cells.topLeftFreeCell())

        val (col, row, direction) = map.run(actions)

        val rowResult = 1000 * (row + 1)
        val columnResult = 4 * (col + 1)
        val directionResult = when (direction) {
            RIGHT -> 0
            DOWN -> 1
            LEFT -> 2
            UP -> 3
        }

        rowResult + columnResult + directionResult
    }
}

fun part2(): TimedResult<Long> = InputFile.withLines {
    timed {
        TODO()
    }
}

private fun List<String>.parseCells(): List<List<Cell>> {
    val maxRowLength = maxOf { it.length }
    return map { it.padEnd(maxRowLength) }
        .map { line -> line.map { Cell.byChar(it) } }
}

private fun List<List<Cell>>.topLeftFreeCell(): Position =
    map { row -> row.indexOfFirst { it == FREE } }.let { firstFreeCols ->
        val (y, x) = firstFreeCols.withIndex().first { (_, col) -> col > -1 }
        require(y > -1)
        Position(x = x, y = y, direction = RIGHT)
    }

private fun String.parseActions(): List<Action> = buildList {
    val chars = this@parseActions.toMutableList()
    while (chars.isNotEmpty()) {
        when (val current = chars.removeFirst()) {
            'L' -> add(Turn(LEFT))
            'R' -> add(Turn(RIGHT))
            else -> {
                assert(current.isDigit())
                val digits = listOf(current) + chars.removeWhile { it.isDigit() }
                val steps = digits.joinToString("").toInt()
                add(Move(steps))
            }
        }
    }
}