package ceejay.advent.day16

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1(): Int = InputFile.withLines {
    val startValve = "AA"

    parse()
        .prune(startValve = startValve)
        .findBestPath(startValve, 30)
        .totalRelievedPressure
}

fun part2(): Int = InputFile.withLines {
    val startValve = "AA"
    val maxMinutes = 26
    val valves = parse()

    // path 1: find the best path like in part 1
    val bestPath1 = valves
        .prune(startValve = startValve)
        .findBestPath(startValve, maxMinutes)

    // path 2: change the flow of all opened valves in path 1 to 0, then find the best path again
    val bestPath2 = valves
        .map { if (it.name in bestPath1.openedValves) it.copy(flow = 0) else it }
        .prune(startValve = startValve)
        .findBestPath(startValve, maxMinutes)

    bestPath1.totalRelievedPressure + bestPath2.totalRelievedPressure
}