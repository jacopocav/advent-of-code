package ceejay.advent.day12

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
}

fun part1() = InputFile.withLines {
    val graph = parse()
    val shortestPath = DijkstraAlgorithm.getShortestPath(graph)
    shortestPath.size - 1
}

fun part2(): Int = TODO()

