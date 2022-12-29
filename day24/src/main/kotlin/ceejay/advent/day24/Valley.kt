package ceejay.advent.day24

import ceejay.advent.util.Debuggable
import ceejay.advent.util.Vector2D
import java.util.*
import java.util.Objects.hash

class Valley(
    private val start: Vector2D,
    private val end: Vector2D,
    xRange: IntRange,
    yRange: IntRange,
    private val blizzards: BlizzardCalculator,
) : Debuggable {
    override var debugEnabled = false

    private val xMin = xRange.min()
    private val xMax = xRange.max()
    private val yMin = yRange.min()
    private val yMax = yRange.max()

    val flipped: Valley by lazy {
        Valley(
            start = end,
            end = start,
            xRange = xMin..xMax,
            yRange = yMin..yMax,
            blizzards = blizzards,
        )
    }

    private fun Vector2D.heuristic() = manhattan(end)

    fun findShortestPathTime(startTime: Int = 0): Int {
        val queue: PriorityQueue<Path> = PriorityQueue(compareBy { it.time + it.heuristic })
        queue += Path(start, startTime, start.heuristic())

        val visited = mutableSetOf<Pair<Vector2D, Int>>()

        while (queue.isNotEmpty()) {
            val current = queue.remove()

            if (current.position == end) {
                return current.time
            }

            val nextTime = current.time + 1

            current.position.getNeighbors()
                .filter { it !in blizzards[nextTime] }
                .map {
                    Path(
                        position = it,
                        time = nextTime,
                        heuristic = it.heuristic(),
                        prev = current
                    )
                }
                .forEach {
                    if (visited.add(it.position to it.time)) {
                        queue += it
                    }
                }
        }

        error("$end is not reachable from $start")
    }

    private fun Vector2D.getNeighbors(): Collection<Vector2D> {
        return listOf(
            // wait here
            this,
            // move left
            copy(x = x - 1),
            // move right
            copy(x = x + 1),
            // move up
            copy(y = y - 1),
            // move down
            copy(y = y + 1),
        ).filter {
            it == end || it == start || (it.x in xMin..xMax && it.y in yMin..yMax)
        }
    }

    private data class Path(
        val position: Vector2D,
        val time: Int,
        val heuristic: Int,
        var prev: Path? = null,
    ) {
        override fun equals(other: Any?): Boolean =
            other is Path && position == other.position && time == other.time

        override fun hashCode(): Int = hash(position, time)

        override fun toString(): String = generateSequence(this) { it.prev }
            .toList()
            .asReversed()
            .zipWithNext { curr, next ->
                "-->move${curr.position}" +
                    "-->wait${curr.position}".repeat(next.time - curr.time - 1)
            }.joinToString("")
            .substringAfter("-->") + "-->$position"
    }
}
