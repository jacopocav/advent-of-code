package ceejay.advent.day22

import ceejay.advent.util.Vector2D

sealed class Direction(val score: Int, val vector: Vector2D) {
    abstract val left: Direction
    abstract val right: Direction

    override fun toString(): String = this::class.simpleName!!
}

object Up : Direction(score = 3, vector = Vector2D(0, -1)) {
    override val left: Direction = Left
    override val right: Direction = Right
}

object Left : Direction(score = 2, vector = Vector2D(-1, 0)) {
    override val left: Direction = Down
    override val right: Direction = Up
}

object Right : Direction(score = 0, vector = Vector2D(1, 0)) {
    override val left: Direction = Up
    override val right: Direction = Down
}

object Down : Direction(score = 1, vector = Vector2D(0, 1)) {
    override val left: Direction = Right
    override val right: Direction = Left
}
