package ceejay.advent.day24

import ceejay.advent.util.Vector2D

class BlizzardCalculator(private val initialPositions: Collection<Blizzard>) {
    private val blizzardSeq = mutableMapOf<Int, Set<Vector2D>>()
    operator fun get(minute: Int): Set<Vector2D> =
        blizzardSeq.computeIfAbsent(minute) {
            initialPositions.mapTo(mutableSetOf()) { it[minute] }
        }
}