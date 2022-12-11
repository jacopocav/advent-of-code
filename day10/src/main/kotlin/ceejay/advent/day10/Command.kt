package ceejay.advent.day10

internal sealed interface Command {
    val clocks: Int
}

internal object NoOp : Command {
    override val clocks = 1
}

@JvmInline
internal value class AddX(val amount: Int) : Command {
    override val clocks
        get() = 2

    companion object {
        const val register = "X"
    }
}