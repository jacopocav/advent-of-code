package ceejay.advent.day16

import ceejay.advent.util.mutableDequeOf

data class Path(
    val currentValve: String,
    val totalRelievedPressure: Int,
    val elapsedMinutes: Int,
    val openedValves: Set<String>,
    val pathString: String,
)

private val worstPath = Path("<DUMMY>", Int.MIN_VALUE, Int.MAX_VALUE, setOf(), "<DUMMY>")

fun Set<Valve>.findBestPath(startValve: String, maxMinutes: Int): Path {
    val valveMap = associateBy { it.name }
    val startFlow = valveMap[startValve]!!.flow

    val paths = mutableDequeOf(
        Path(
            currentValve = startValve,
            totalRelievedPressure = 0,
            elapsedMinutes = if (startFlow > 0) 1 else 0,
            openedValves = if (startFlow > 0) setOf(startValve) else setOf(),
            pathString = startValve
        )
    )

    var best = worstPath
    val bestRelievedByMinute = mutableMapOf<Int, Int>().withDefault { 0 }

    while (paths.isNotEmpty()) {
        val path = paths.removeFirst()

        if (path.totalRelievedPressure > best.totalRelievedPressure) {
            best = path
        }

        if (bestRelievedByMinute.getValue(path.elapsedMinutes) < path.totalRelievedPressure) {
            bestRelievedByMinute[path.elapsedMinutes] = path.totalRelievedPressure
        }

        val currentValve = valveMap[path.currentValve]!!

        for ((valve, length) in currentValve.connections) {
            val elapsedAfterOpening = path.elapsedMinutes + length + 1

            if (elapsedAfterOpening < maxMinutes && valve !in path.openedValves) {
                val newPressure =
                    path.totalRelievedPressure + (maxMinutes - elapsedAfterOpening) * valveMap[valve]!!.flow

                if (newPressure > bestRelievedByMinute.getValue(elapsedAfterOpening)) {
                    paths += Path(
                        currentValve = valve,
                        totalRelievedPressure = newPressure,
                        elapsedMinutes = elapsedAfterOpening,
                        openedValves = path.openedValves + valve,
                        pathString = path.pathString + "-$valve"
                    )
                }
            }
        }
    }

    return best
}