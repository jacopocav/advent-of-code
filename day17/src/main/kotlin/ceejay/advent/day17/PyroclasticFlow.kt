package ceejay.advent.day17

import ceejay.advent.day17.Shape.*
import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1(): Long = InputFile.withLines {
    run(2022)
}

fun part2(): Long = InputFile.withLines {
    run(1_000_000_000_000)
}

private fun Sequence<String>.run(shapeCount: Long): Long {
    val moves = flatMap { line -> line.map { Move.byChar(it) } }
    val dropper = ShapeDropper(
        width = 7,
        shapes = listOf(HOR_LINE, CROSS, L, VER_LINE, SQUARE),
        jetPattern = moves.toList(),
        debugEnabled = false
    )

    return with(dropper) {
        drop(shapeCount)
            .towerHeight()
    }
}