package ceejay.advent.day24

import ceejay.advent.util.InputFile
import ceejay.advent.util.TimedResult
import ceejay.advent.util.Vector2D.Companion.vector
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
    timed {
        parse().apply { debugEnabled = false }.findShortestPathTime()
    }
}

fun part2(): TimedResult<Int> = InputFile.withLines {
    timed {
        val valley = parse().apply { debugEnabled = false }
        val firstTripTime = valley.findShortestPathTime(startTime = 0)
        val secondTripTime = valley.flipped.findShortestPathTime(startTime = firstTripTime)
        val thirdTripTime = valley.findShortestPathTime(startTime = secondTripTime)
        thirdTripTime
    }
}

fun Sequence<String>.parse(): Valley {
    val lines = toList()

    assert(lines.distinctBy { it.length }.size == 1)
    assert(lines.first().count { it == '.' } == 1)
    assert(lines.last().count { it == '.' } == 1)
    assert(lines.map { it.first() }.all { it == '#' })
    assert(lines.map { it.last() }.all { it == '#' })

    val start = vector(x = lines.first().indexOfFirst { it == '.' }, y = 0)
    val end = vector(x = lines.last().indexOfFirst { it == '.' }, y = lines.size - 1)

    val xMin = 1
    val xMax = lines.first().length - 2

    val blizzards = lines.flatMapIndexed { y, line ->
        line.mapIndexedNotNull { x, char ->
            when (char) {
                '<', '>' -> HorizontalBlizzard(
                    startPosition = vector(x, y),
                    reversed = char == '<',
                    xMin = xMin,
                    xMax = xMax
                )

                '^', 'v' -> VerticalBlizzard(
                    startPosition = vector(x, y),
                    reversed = char == '^',
                    yMin = start.y + 1,
                    yMax = end.y - 1
                )

                else -> null
            }
        }
    }

    return Valley(
        start = start,
        end = end,
        xRange = 1..lines.first().length - 2,
        yRange = 1..lines.size - 2,
        blizzards = BlizzardCalculator(blizzards)
    )
}