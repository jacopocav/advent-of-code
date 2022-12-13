package ceejay.advent.day13

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
//    println("Part 2 Result: ${part2()}")
}

fun part1(): Int = InputFile.withLines {
    PacketParser.parse(this)
        .windowed(2, 2)
        .map { (first, second) -> first compareTo second }
        .mapIndexed { i, result -> Pair(i, result) }
        .filter { (_, result) -> result < 0 }
        .sumOf { it.first + 1 }
}

fun part2(): Int {
    TODO()
}