package ceejay.advent.day06

import ceejay.advent.util.InputFile

fun findFirstSubstringOfDistinctCharsOfLength(substringLength: Int): Int {
    val input = InputFile("06-Markers.txt")

    val firstMarkerIndex = input
        .windowedSequence(substringLength)
        .indexOfFirst { it.toSet().size == substringLength }

    return substringLength + firstMarkerIndex
}