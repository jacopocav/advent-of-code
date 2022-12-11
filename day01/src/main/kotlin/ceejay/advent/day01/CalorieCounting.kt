package ceejay.advent.day01

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1(): Int {
    return InputFile().split("\n\n")
        .maxOf { groups ->
            groups.split("\n")
                .filter { it.isNotBlank() }
                .sumOf { it.toInt() }
        }
}

fun part2(): Int {
    return InputFile().split("\n\n")
        .asSequence()
        .map { groups ->
            groups.split("\n")
                .filter { it.isNotBlank() }
                .sumOf { it.toInt() }
        }
        .sortedDescending()
        .take(3)
        .sum()
}