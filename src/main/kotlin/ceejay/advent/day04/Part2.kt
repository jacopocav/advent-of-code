package ceejay.advent.day04

import ceejay.advent.util.InputFile


fun main() {
    println(Part2())
}

object Part2 {
    operator fun invoke(): Int {
        val input = InputFile("04-Assignments.txt")

        return input.split("\n")
            .dropLast(1)
            .map { Common.parsePair(it) }
            .count { (first, second) -> first overlaps second }
    }
}