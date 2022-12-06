package ceejay.advent.day01

import ceejay.advent.util.InputFile

fun main() {
    Part2()
}

object Part2 {
    operator fun invoke() {
        val inputString = InputFile("01-Calories.txt")
        val top3ElvesSum = inputString.split("\n\n")
            .map { groups ->
                groups.split("\n")
                    .filter { it.isNotBlank() }
                    .sumOf { it.toInt() }
            }
            .sortedDescending()
            .take(3)
            .sum()
        println(top3ElvesSum)
    }
}