package ceejay.advent.day22

import ceejay.advent.util.InputFile
import ceejay.advent.util.TimedResult
import ceejay.advent.util.Vector2D.Companion.vector
import ceejay.advent.util.removeWhile
import ceejay.advent.util.timed

fun main() {
    part1().also {
        println("Part 1 Result: $it")
    }
    part2().also {
        println("Part 2 Result: $it")
    }
}

fun part1(): TimedResult<Int> = InputFile.withLines {
    timed { run(CubeFace.fakeFacesOfSize(50)) }
}

fun part2(): TimedResult<Int> = InputFile.withLines {
    timed { run(CubeFace.realFacesOfSize(50)) }
}

private fun Sequence<String>.run(cubeFaces: Map<Int, CubeFace>): Int {
    val (cells, actions) = parse()

    return with(CubeMap(cells, cubeFaces)) {
        val result =
            run(actions, cubeFaces.topLeftFace().id, DirectedVector(vector(0, 0), Right))

        result.score()
    }
}

private fun DirectedVector.score(): Int =
    1000 * (vector.y + 1) + 4 * (vector.x + 1) + direction.score

private fun Map<Int, CubeFace>.topLeftFace(): CubeFace {
    var min = values.first()
    for (face in values.drop(1)) {
        if (face.topLeft.x < min.topLeft.x && face.topLeft.y < min.topLeft.y) {
            min = face
        }
    }
    return min
}

private fun Sequence<String>.parse(): Pair<List<List<Cell>>, List<Action>> {
    val lines = toList()
    val cells = lines.dropLast(1).parseCells()
    val actions = lines.takeLast(1).first().parseActions()
    return cells to actions
}

private fun List<String>.parseCells(): List<List<Cell>> {
    val maxRowLength = maxOf { it.length }
    return map { it.padEnd(maxRowLength) }
        .map { line -> line.map { Cell.byChar(it) } }
}

private fun String.parseActions(): List<Action> = buildList {
    val chars = this@parseActions.toMutableList()
    while (chars.isNotEmpty()) {
        when (val current = chars.removeFirst()) {
            'L' -> add(TurnLeft)
            'R' -> add(TurnRight)
            else -> {
                assert(current.isDigit())
                val digits = listOf(current) + chars.removeWhile { it.isDigit() }
                val steps = digits.joinToString("").toInt()
                add(Move(steps))
            }
        }
    }
}