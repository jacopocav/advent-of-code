package ceejay.advent.day16

internal fun Iterable<Valve>.prune(startValve: String): Set<Valve> {
    val allValves = associateBy { it.name }
    val nonZeroValves = allValves.filterValues { it.flow > 0 }

    fun Valve.pruneConnections(newConnections: NewConnections, maxConnections: Int) {
        val found = mutableSetOf(name)
        val connections = newConnections.getOrPut(name) { mutableSetOf() }

        var queue = mutableListOf(this)
        var distance = 0
        while (connections.size < maxConnections) {
            val newQueue = mutableListOf<Valve>()
            distance++

            for (valve in queue) {
                for ((name, _) in valve.connections) {
                    val nextValve = allValves[name]!!
                    newQueue += nextValve

                    if (nextValve.flow > 0 && name !in found) {
                        connections += Connection(toValve = name, length = distance)
                        found += name
                    }
                }
            }
            queue = newQueue
        }
    }

    val start = allValves[startValve]
        ?: error { "no starting valve '$startValve' found" }
    val newConnections: NewConnections = mutableMapOf()

    nonZeroValves.forEach { (_, valve) ->
        valve.pruneConnections(
            newConnections,
            maxConnections = nonZeroValves.size - 1
        )
    }
    start.pruneConnections(newConnections, nonZeroValves.size)

    return newConnections
        .map { (name, connections) ->
            Valve(
                name = name,
                flow = allValves[name]!!.flow,
                connections = connections
            )
        }
        .toSet()
}

private typealias NewConnections = MutableMap<String, MutableSet<Connection>>