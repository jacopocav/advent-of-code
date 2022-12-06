package ceejay.advent.day02

import ceejay.advent.util.InputFile

fun main() {
    Part2()
}

object Part2 {
    operator fun invoke() {
        val inputString = InputFile("02-RockPaperScissors.txt")
        val totalScore = inputString.split("\n")
            .filter { it.isNotBlank() }
            .sumOf { scoreRow(it) }
        println(totalScore)
    }

    private fun scoreRow(row: String): Int {
        val symbols = row.split(" ")
        val opponent = Hand.findByOpponentCode(symbols[0])
        val outcome = Outcome.findByCode(symbols[1])
        val mine = outcome.getAnswerTo(opponent)
        return mine.scoreAgainst(opponent)
    }
}