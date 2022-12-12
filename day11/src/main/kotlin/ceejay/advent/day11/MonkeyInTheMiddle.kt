package ceejay.advent.day11

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1(): Int = InputFile.withLines {
    val monkeys = MonkeyParser.parse(this)

    val inspectionCounts = with(MonkeyEngine(monkeys)) {
        run(20)
        getInspectionCounts()
    }

    return inspectionCounts.sortedDescending()
        .take(2)
        .reduce(Int::times)
}

fun part2(): Int = 0  // TODO implement

