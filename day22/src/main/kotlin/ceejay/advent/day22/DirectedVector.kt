package ceejay.advent.day22

import ceejay.advent.util.Vector2D

data class DirectedVector(val vector: Vector2D, val direction: Direction) {
    fun advance(): DirectedVector = copy(vector = vector + direction.vector)
}