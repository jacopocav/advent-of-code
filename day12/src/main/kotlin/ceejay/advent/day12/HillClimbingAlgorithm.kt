package ceejay.advent.day12

import ceejay.advent.util.InputFile
import java.util.*

internal typealias Graph = List<Node>

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1() = InputFile.withLines {
    val graph = GraphParser(startChar = 'S', endChars = "E").parse(this)

    graph.findSingleEndNode().dist
}

fun part2() = InputFile.withLines {
    // searches backwards from the single end node to all start nodes
    val graph = GraphParser(
        startChar = 'E',
        endChars = "aS",
        heightMapper = { -GraphParser.defaultHeight(it) }
    ).parse(this)

    graph.findAllEndNodes().minOf { it.dist }
}

private fun Graph.findAllEndNodes() = dijkstraSearch(stopEarly = false)

private fun Graph.findSingleEndNode(): Node = dijkstraSearch(stopEarly = true).first()

private fun Graph.dijkstraSearch(stopEarly: Boolean): List<Node> {
    val queue = toCollection(PriorityQueue(compareBy { it.dist }))

    val pathLengths = mutableListOf<Node>()
    while (queue.isNotEmpty()) {
        val lowestDistNode = queue.remove()

        if (lowestDistNode.isEnd) {
            pathLengths += lowestDistNode
            if (stopEarly) break
        } else {
            lowestDistNode.edges
                .filter { it in queue }
                .forEach {
                    queue.remove(it)
                    it.dist = lowestDistNode.dist + 1
                    it.prev = lowestDistNode
                    queue.add(it)
                }
        }
    }

    return pathLengths
}