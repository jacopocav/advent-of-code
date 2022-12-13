package ceejay.advent.day12

import java.util.*

internal sealed interface Algorithm {
    fun getShortestPath(graph: Graph): List<Node>
}

internal object DijkstraAlgorithm : Algorithm {
    override fun getShortestPath(graph: Graph): List<Node> {
        val queue = graph.nodes
            .toCollection(PriorityQueue(compareBy { it.dist }))

        while (queue.isNotEmpty()) {
            val lowestDistNode = queue.remove()

            if (lowestDistNode.isEnd) {
                return createPath(lowestDistNode)
            }

            lowestDistNode.edges
                .filter { it in queue }
                .forEach {
                    queue.remove(it)
                    it.dist = lowestDistNode.dist + 1
                    it.prev = lowestDistNode
                    queue.add(it)
                }
        }

        throw IllegalArgumentException("end node is unreachable")
    }

    private fun createPath(lowestDistNode: Node): List<Node> =
        generateSequence(lowestDistNode) { it.prev }
            .toList()
            .asReversed()
}