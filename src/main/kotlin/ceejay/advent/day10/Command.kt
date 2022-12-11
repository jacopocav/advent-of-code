package ceejay.advent.day10

sealed interface Command {
    val clocks: Int
}

object NoOp : Command {
    override val clocks = 1
}

@JvmInline
value class AddX(val amount: Int) : Command {
    override val clocks
        get() = 2

    companion object {
        const val register = "X"
    }
}