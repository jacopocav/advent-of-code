package ceejay.advent.day17

import ceejay.advent.day17.Shape.*
import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
//    println("Part 2 Result: ${part2()}")
}

fun part1(): Int = InputFile.withLines {
    val moves = flatMap { line -> line.map { Move.byChar(it) } }
    val dropper = ShapeDropper(
        width = 7,
        shapes = listOf(HOR_LINE, CROSS, L, VER_LINE, SQUARE),
        debugEnabled = false
    )

    with(dropper) {
        drop(shapeCount = 2022, moves = moves.toList())
            .towerHeight()
    }
}

fun part2(): Int = InputFile.withLines {
    TODO()
}