package ceejay.advent.day24

import ceejay.advent.util.Vector2D

sealed class Blizzard(val startPosition: Vector2D) {
    abstract operator fun get(minute: Int): Vector2D
}

class HorizontalBlizzard(
    startPosition: Vector2D,
    val reversed: Boolean,
    val xMin: Int,
    val xMax: Int,
) : Blizzard(startPosition) {
    override fun get(minute: Int): Vector2D {
        val startX = startPosition.x
        val newX = (xMin..xMax).getNext(
            start = startX,
            steps = minute,
            reversed = reversed,
        )

        return startPosition.copy(x = newX)
    }
}

class VerticalBlizzard(
    startPosition: Vector2D,
    val reversed: Boolean,
    val yMin: Int,
    val yMax: Int,
) : Blizzard(startPosition) {
    override fun get(minute: Int): Vector2D {
        val newY = (yMin..yMax).getNext(
            start = startPosition.y,
            steps = minute,
            reversed = reversed
        )

        return startPosition.copy(y = newY)
    }
}

private fun IntRange.getNext(start: Int, steps: Int, reversed: Boolean): Int =
    if (reversed) {
        val relativeStart = last - start
        val relativeEnd = first + ((relativeStart + steps) % last) - 1
        last - relativeEnd
    } else {
        val relativeStart = start - first
        val relativeEnd = (relativeStart + steps) % (last - first + 1)
        relativeEnd + first
    }