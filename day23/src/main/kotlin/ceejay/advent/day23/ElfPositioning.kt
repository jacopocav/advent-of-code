package ceejay.advent.day23

import ceejay.advent.day23.Cardinal.*
import ceejay.advent.util.Debuggable
import ceejay.advent.util.Debuggable.Companion.debug
import ceejay.advent.util.Vector2D

data class Result(val lastRound: Int, val elves: Collection<Vector2D>)

infix fun Int.with(elves: Collection<Vector2D>) = Result(this, elves)

object ElfPositioning : Debuggable {
    override var debugEnabled = false
    fun moveElves(elves: Set<Vector2D>, rounds: Int = Int.MAX_VALUE): Result {
        var current: Set<Vector2D> = elves
        val directions = ArrayDeque(listOf(N, S, W, E))

        repeat(rounds) { round ->
            debug { "--- Round ${round + 1} ---" }
            val next = mutableSetOf<Vector2D>()

            var moved = false
            for (elf in current) {
                val adjacents = elf.neighbors().filterTo(mutableSetOf()) { it in current }

                if (adjacents.isEmpty()) {
                    // no need to move
                    next += elf
                    continue
                }

                val direction = directions.getRightDirection(elf, adjacents)

                if (direction == null) {
                    // can't move
                    next += elf
                    continue
                }

                val destination = direction.move(elf)

                if (next.remove(destination)) {
                    // collision -> the other elf must have come from the opposite direction
                    // both go back to their previous position
                    next += direction.move(destination)
                    next += elf
                } else {
                    // no collision -> move successful
                    moved = true
                    next += destination
                }

            }

            assert(current.size == next.size)

            if (!moved) {
                return round + 1 with current
            }

            current = next

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

    private fun Vector2D.neighbors() = setOf(
        copy(x = x + 1),
        copy(x = x - 1),
        copy(y = y - 1),
        copy(y = y + 1),
        copy(x = x + 1, y = y + 1),
        copy(x = x + 1, y = y - 1),
        copy(x = x - 1, y = y - 1),
        copy(x = x - 1, y = y + 1),
    )
}
