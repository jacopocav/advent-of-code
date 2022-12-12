package ceejay.advent.day11

import ceejay.advent.day11.Operation.Operator.DIVIDE
import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1() = InputFile.withLines {
    run(rounds = 20, boredOperation = Operation(DIVIDE, 3L))
}

fun part2() = InputFile.withLines {
    run(rounds = 10_000, boredOperation = Operation.noOp)
}

private fun Sequence<String>.run(rounds: Int, boredOperation: Operation): Long {
    val monkeys = parse(boredOperation)

    val inspectionCounts = with(MonkeyEngine(monkeys)) {
        run(rounds)
        inspectionCounts
    }

    return inspectionCounts.sortedDescending()
        .take(2)
        .reduce { acc, current -> acc * current }
}

