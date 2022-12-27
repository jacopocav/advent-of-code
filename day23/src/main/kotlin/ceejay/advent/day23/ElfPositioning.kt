package ceejay.advent.day23

import ceejay.advent.day23.Cardinal.*
import ceejay.advent.util.Debuggable
import ceejay.advent.util.Debuggable.Companion.debug
import ceejay.advent.util.Vector2D

data class Result(val lastRound: Int, val elves: Collection<Vector2D>)

infix fun Int.with(elves: Collection<Vector2D>) = Result(this, elves)

object ElfPositioning : Debuggable {
    override var debugEnabled = false
    fun moveElves(elves: Collection<Vector2D>, rounds: Int = Int.MAX_VALUE): Result {
        var current = elves
        val directions = ArrayDeque(listOf(N, S, W, E))

        repeat(rounds) { round ->
            debug { "--- Round ${round + 1} ---" }
            val proposed = mutableMapOf<Vector2D, MutableList<Vector2D>>()
            val new = mutableSetOf<Vector2D>()

            for (elf in current) {
                val adjacents = current.filter { elf adjacentTo it }

                if (adjacents.isEmpty()) {
                    new += elf
                } else {
                    directions.getRightDirection(elf, adjacents)?.move(elf)
                        ?.let {
                            proposed.getOrPut(it) { mutableListOf() }.add(elf)
                        }
                        ?: new.add(elf)
                }
            }

            if (proposed.isEmpty()) {
                return round + 1 with current
            }

            for ((destination, sources) in proposed) {
                if (sources.size == 1) {
                    new += destination
                } else {
                    new += sources
                }
            }

            assert(current.size == new.size)

            current = new

            debug { current.toGrid() }

            directions.rotate()
        }

        return rounds with current
    }

    private fun List<Cardinal>.getRightDirection(
        elf: Vector2D,
        elves: Collection<Vector2D>,
    ): Cardinal? = firstOrNull { elf.hasNoNeighbors(it, elves) }

    private fun ArrayDeque<Cardinal>.rotate() = addLast(removeFirst())
}
