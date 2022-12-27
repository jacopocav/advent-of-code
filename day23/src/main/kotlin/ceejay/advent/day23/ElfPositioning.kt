package ceejay.advent.day23

import ceejay.advent.day23.Cardinal.*
import ceejay.advent.util.Debuggable
import ceejay.advent.util.Debuggable.Companion.debug
import ceejay.advent.util.Vector2D

data class Result(val lastRound: Int, val elves: Collection<Vector2D>)

infix fun Int.with(elves: Collection<Vector2D>) = Result(this, elves)

object ElfPositioning : Debuggable {
    override var debugEnabled = false
    private fun List<Cardinal>.firstCandidate(
        elf: Vector2D,
        elves: Collection<Vector2D>,
    ): Cardinal? =
        firstOrNull { elf.hasNoNeighbors(it, elves) }

    fun moveElves(elves: Collection<Vector2D>, rounds: Int = Int.MAX_VALUE): Result {
        var current = elves
        val directions = ArrayDeque(listOf(N, S, W, E))

        repeat(rounds) { round ->
            debug { "--- Round ${round + 1} ---" }
            val proposed = mutableMapOf<Vector2D, Vector2D>()

            for (elf in current) {
                val adjacents = current.filter { elf adjacentTo it }

                proposed[elf] = if (adjacents.isEmpty()) {
                    elf
                } else {
                    directions.firstCandidate(elf, adjacents)?.move(elf) ?: elf
                }
            }

            if (proposed.all { (from, to) -> from == to }) {
                return round + 1 with current
            }

            assert(proposed.size == current.size)

            current = proposed
                .mapTo(mutableSetOf()) { (from, to) ->
                    if (proposed.values.count { it == to } > 1) from else to
                }

            debug { current.toGrid() }
            directions.removeFirst().let { directions.addLast(it) }
        }

        return rounds with current
    }
}
