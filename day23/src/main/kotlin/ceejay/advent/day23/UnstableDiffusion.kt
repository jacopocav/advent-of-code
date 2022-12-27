package ceejay.advent.day23

import ceejay.advent.day23.ElfPositioning.moveElves
import ceejay.advent.util.InputFile
import ceejay.advent.util.TimedResult
import ceejay.advent.util.Vector2D
import ceejay.advent.util.Vector2D.Companion.vector
import ceejay.advent.util.timed

fun main() {
//    ElfPositioning.debugEnabled = true
    part1().also {
        println("Part 1 Result: $it")
    }
    part2().also {
        println("Part 2 Result: $it")
    }
}

fun part1(): TimedResult<Int> = InputFile.withLines {
    timed {
        val (_, elves) = moveElves(elves = parse(), rounds = 10)
        elves.score()
    }
}

fun part2(): TimedResult<Int> = InputFile.withLines {
    timed {
        val (lastRound, _) = moveElves(elves = parse())
        lastRound
    }
}

private fun Sequence<String>.parse(): Collection<Vector2D> = flatMapIndexed { y, row ->
    row.mapIndexedNotNull { x, cell ->
        if (cell == '#') vector(x, y) else null
    }
}.toSet()

private fun Collection<Vector2D>.score(): Int {
    val xMin = minOf { it.x }
    val xMax = maxOf { it.x }

    val yMin = minOf { it.y }
    val yMax = maxOf { it.y }

    return (xMax - xMin + 1) * (yMax - yMin + 1) - size
}

fun Collection<Vector2D>.toGrid(): String {
    val xMin = minOf { it.x }
    val xMax = maxOf { it.x }

    val yMin = minOf { it.y }
    val yMax = maxOf { it.y }

    return (yMin..yMax).joinToString("\n") { y ->
        (xMin..xMax).joinToString("") { x ->
            val vector = Vector2D(x, y)
            if (vector in this) "#" else "."
        }
    }
}