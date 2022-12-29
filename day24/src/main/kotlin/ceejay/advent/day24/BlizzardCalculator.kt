package ceejay.advent.day24

import ceejay.advent.util.Vector2D
import ceejay.advent.util.lcm

class BlizzardCalculator(
    private val initialPositions: Collection<Blizzard>,
    xRange: IntRange,
    yRange: IntRange,
) {
    private val repeatsEvery = lcm(xRange.count() - 2, yRange.count() - 2)
    private val blizzardSeq = mutableMapOf<Int, Set<Vector2D>>()

    operator fun get(minute: Int): Set<Vector2D> =
        blizzardSeq.computeIfAbsent(minute % repeatsEvery) {
            initialPositions.mapTo(mutableSetOf()) { it[minute % repeatsEvery] }
        }
}