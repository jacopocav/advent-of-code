package ceejay.advent.day04

import ceejay.advent.util.InputFile


fun main() {
    println(Part1())
}

object Part1 {
    operator fun invoke(): Int {
        val input = InputFile("04-Assignments.txt")

        return input.split("\n")
            .dropLast(1)
            .map { Common.parsePair(it) }
            .count { (first, second) -> first in second || second in first }
    }
}