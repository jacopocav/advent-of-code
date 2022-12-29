package ceejay.advent.day16

import java.util.*

class ValveGraphSimplifier(valves: Iterable<Valve>) {
    private val allValves: Map<String, Valve> = valves.associateBy { it.name }
    private val nonZeroValves: Map<String, Valve> = allValves.filterValues { it.flow > 0 }

    fun simplify(startValve: String): Set<Valve> {
        return getSimplifiedConnections(startValve)
            .mapTo(mutableSetOf()) { (name, connections) ->
                Valve(
                    name = name,
                    flow = allValves[name]!!.flow,
                    connections = connections
                )
            }
    }

    /**
     * For every other valve V with non-zero flow, creates a direct connection from this to V
     * with length as the shortest length this->V in the original graph
     */
    private fun getSimplifiedConnections(
        startValve: String,
    ): Map<String, Set<Connection>> {
        return (nonZeroValves.keys + startValve).associateWith {
            it.getShortestPathsToNonZeroFlowValves()
                .mapTo(mutableSetOf()) { (name, dist) -> Connection(name, dist) }
        }
    }

    private fun String.getShortestPathsToNonZeroFlowValves(): Map<String, Int> {
        // Yet Another Dijkstra Implementation
        val firstValve = allValves[this]!!

        val queue = allValves.values.mapTo(PriorityQueue(compareBy { it.dist })) {
            Path(
                valve = it,
                isEnd = it != firstValve && it.flow > 0,
                dist = if (it == firstValve) 0 else Int.MAX_VALUE
            )
        }

        val paths = mutableMapOf<String, Int>()

        while (queue.isNotEmpty()) {
            val path = queue.remove()

            if (path.isEnd) {
                paths[path.valve.name] = path.dist
            }

            path.valve.connections
                .map {
                    val v = allValves[it.toValve]!!
                    Path(valve = v, isEnd = v != firstValve && v.flow > 0, dist = it.length)
                }
                .filter { it in queue }
                .forEach {
                    queue -= it
                    it.dist += path.dist
                    it.prev = path
                    queue += it
                }
        }
        return paths
    }

    private data class Path(
        val valve: Valve,
        val isEnd: Boolean,
        var dist: Int,
        var prev: Path? = null,
    ) {
        override fun equals(other: Any?): Boolean = other is Path && valve == other.valve
        override fun hashCode(): Int = valve.hashCode()
    }
}