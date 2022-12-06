package ceejay.advent._02

import ceejay.advent.util.InputUtils

fun main() {
    Part1()
}

object Part1 {
    private const val inputFile = "02-RockPaperScissors.txt"
    operator fun invoke() {
        val inputString = InputUtils.read(inputFile)
        val totalScore = inputString.split("\n")
            .filter { it.isNotBlank() }
            .sumOf { scoreRow(it) }
        println(totalScore)
    }

    private fun scoreRow(row: String): Int {
        val symbols = row.split(" ")
        val opponent = parseLeft(symbols[0])
        val mine = parseRight(symbols[1])
        return scoreOf(opponent, mine)
    }
}