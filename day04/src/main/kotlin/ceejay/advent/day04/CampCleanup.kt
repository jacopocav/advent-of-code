package ceejay.advent.day04

import ceejay.advent.util.InputFile


fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1(): Int = InputFile.withLines {
    filter { it.isNotBlank() }
        .map { parsePair(it) }
        .count { (first, second) -> first in second || second in first }
}

fun part2(): Int = InputFile.withLines {
    filter { it.isNotBlank() }
        .map { parsePair(it) }
        .count { (first, second) -> first overlaps second }
}

private fun parsePair(line: String): Pair<Range, Range> {
    val ranges = line.split(",")
        .map { parseRange(it) }

    assert(ranges.size == 2)

    return ranges[0] to ranges[1]
}

private fun parseRange(pair: String): Range {
    val bounds = pair.split("-")
        .map { it.toInt() }

    assert(bounds.size == 2)

    return Range(bounds[0], bounds[1])
}