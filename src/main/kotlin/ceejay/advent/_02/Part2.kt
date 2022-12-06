package ceejay.advent._02

import ceejay.advent.util.InputUtils

fun main() {
    Part2()
}

object Part2 {
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
        val outcome = parseOutcome(symbols[1])
        val mine = outcome.answerTo(opponent)
        return scoreOf(opponent, mine)
    }
}