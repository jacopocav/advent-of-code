package ceejay.advent.day02

import ceejay.advent.util.InputFile

fun main() {
    Part1()
}

object Part1 {
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
        val mine = Hand.findByMyCode(symbols[1])
        return mine.scoreAgainst(opponent)
    }
}