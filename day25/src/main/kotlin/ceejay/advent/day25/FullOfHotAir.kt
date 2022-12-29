package ceejay.advent.day25

import ceejay.advent.day25.Snafu.Companion.toSnafu
import ceejay.advent.util.InputFile
import ceejay.advent.util.TimedResult
import ceejay.advent.util.timed

fun main() {
    part1().also {
        println("Part 1 Result: $it")
    }
}

fun part1(): TimedResult<String> = InputFile.withLines {
    timed {
        sumOf { it.toSnafu().toLong() }.toSnafu().toString()
    }
}