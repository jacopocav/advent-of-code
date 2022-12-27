package ceejay.advent.day22

sealed interface Action

data class Move(val steps: Int) : Action

object TurnLeft : Action
object TurnRight : Action