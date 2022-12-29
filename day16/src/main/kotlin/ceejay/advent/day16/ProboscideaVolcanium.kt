package ceejay.advent.day16

import ceejay.advent.util.InputFile
import ceejay.advent.util.timed

fun main() {
    part1().also {
        println("Part 1 Result: $it")
    }
    part2().also {
        println("Part 2 Result: $it")
    }
}

fun part1() = InputFile.withLines {
    timed {
        val startValve = "AA"

        ValveGraphSimplifier(parse())
            .simplify(startValve = startValve)
            .findBestPath(startValve, 30)
            .totalRelievedPressure
    }
}

fun part2() = InputFile.withLines {
    timed {
        val startValve = "AA"
        val maxMinutes = 26
        val valves = parse()

        // path 1: find the best path like in part 1
        val bestPath1 = ValveGraphSimplifier(valves)
            .simplify(startValve = startValve)
            .findBestPath(startValve, maxMinutes)

        // path 2: change the flow of all opened valves in path 1 to 0, then find the best path again
        val bestPath2 = valves
            .map { if (it.name in bestPath1.openedValves) it.copy(flow = 0) else it }
            .let { ValveGraphSimplifier(it).simplify(startValve = startValve) }
            .findBestPath(startValve, maxMinutes)

        bestPath1.totalRelievedPressure + bestPath2.totalRelievedPressure
    }
}