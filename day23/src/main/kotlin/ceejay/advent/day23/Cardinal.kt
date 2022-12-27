package ceejay.advent.day23

import ceejay.advent.util.Vector2D
import ceejay.advent.util.Vector2D.Companion.vector

enum class Cardinal(private val offset: Vector2D) {
    N(vector(0, -1)) {
        override fun hasNoNeighbors(point: Vector2D, points: Iterable<Vector2D>): Boolean =
            points.none { it.y == point.y - 1 && it.x - point.x in -1..1 }
    },
    E(vector(1, 0)) {
        override fun hasNoNeighbors(point: Vector2D, points: Iterable<Vector2D>): Boolean =
            points.none { it.x == point.x + 1 && it.y - point.y in -1..1 }
    },
    S(vector(0, 1)) {
        override fun hasNoNeighbors(point: Vector2D, points: Iterable<Vector2D>): Boolean =
            points.none { it.y == point.y + 1 && it.x - point.x in -1..1 }
    },
    W(vector(-1, 0)) {
        override fun hasNoNeighbors(point: Vector2D, points: Iterable<Vector2D>): Boolean =
            points.none { it.x == point.x - 1 && it.y - point.y in -1..1 }
    };

    abstract fun hasNoNeighbors(point: Vector2D, points: Iterable<Vector2D>): Boolean

    fun move(vector: Vector2D) = vector + offset
}

fun Vector2D.hasNoNeighbors(cardinal: Cardinal, all: Collection<Vector2D>): Boolean =
    cardinal.hasNoNeighbors(this, all)