package ceejay.advent.day06

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}


fun part1(): Int = findFirstSubstringOfDistinctCharsOfLength(4)
fun part2(): Int = findFirstSubstringOfDistinctCharsOfLength(14)

internal fun findFirstSubstringOfDistinctCharsOfLength(substringLength: Int): Int {
    val firstMarkerIndex = InputFile()
        .windowedSequence(substringLength)
        .indexOfFirst { it.toSet().size == substringLength }

    return substringLength + firstMarkerIndex
}