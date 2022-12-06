package ceejay.advent._01

import ceejay.advent.util.InputUtils

fun main() {
    Part2()
}

object Part2 {
    private const val inputFile = "01-Calories.txt"
    operator fun invoke() {
        val inputString = InputUtils.read(inputFile)
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