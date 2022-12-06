package ceejay.advent.day01

import ceejay.advent.util.InputFile

fun main() {
    Part1()
}

object Part1 {
    operator fun invoke() {
        val inputString = InputFile("01-Calories.txt")
        val maxCalories = inputString.split("\n\n")
            .maxOf { groups ->
                groups.split("\n")
                    .filter { it.isNotBlank() }
                    .sumOf { it.toInt() }
            }
        println(maxCalories)
    }
}