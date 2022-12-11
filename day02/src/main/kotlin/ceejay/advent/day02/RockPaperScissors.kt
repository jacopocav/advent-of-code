package ceejay.advent.day02

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1(): Int = InputFile.withLines {
    map { it.split(" ") }
        .sumOf { (opponentCode, myCode) ->
            val opponent = Hand.findByOpponentCode(opponentCode)
            val mine = Hand.findByMyCode(myCode)

            mine.scoreAgainst(opponent)
        }
}

fun part2(): Int = InputFile.withLines {
    map { it.split(" ") }
        .sumOf { (opponentCode, outcomeCode) ->
            val outcome = Outcome.findByCode(outcomeCode)

            val opponent = Hand.findByOpponentCode(opponentCode)
            val mine = outcome.getAnswerTo(opponent)

            mine.scoreAgainst(opponent)
        }
}

