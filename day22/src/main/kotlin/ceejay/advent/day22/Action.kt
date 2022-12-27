package ceejay.advent.day22

import ceejay.advent.day22.Direction.LEFT
import ceejay.advent.day22.Direction.RIGHT

sealed interface Action

data class Move(val steps: Int) : Action

data class Turn(val direction: Direction) : Action {
    init {
        require(direction == LEFT || direction == RIGHT) { "Cannot turn $direction, only LEFT or RIGHT" }
    }
}