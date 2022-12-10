package ceejay.advent.day10

sealed interface Command {
    val clocks: Int
}

object NoOp : Command {
    override val clocks = 1
}

data class AddX(val amount: Int) : Command {
    override val clocks = 2
}