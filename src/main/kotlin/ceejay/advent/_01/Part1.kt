package ceejay.advent._01

import ceejay.advent.util.InputUtils

fun main() {
    Part1()
}

object Part1 {
    private const val inputFile = "01-Calories.txt"
    operator fun invoke() {
        val inputString = InputUtils.read(inputFile)
        val maxCalories = inputString.split("\n\n")
            .maxOf { groups ->
                groups.split("\n")
                    .filter { it.isNotBlank() }
                    .sumOf { it.toInt() }
            }
        println(maxCalories)
    }
}