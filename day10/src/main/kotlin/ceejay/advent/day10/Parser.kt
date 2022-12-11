package ceejay.advent.day10

internal object Parser {
    fun parse(line: String): Command {
        val split = line.split(" ")

        return when (split[0]) {
            "noop" -> NoOp
            "addx" -> AddX(split[1].toInt())
            else -> throw IllegalArgumentException("Unexpected command: $line")
        }
    }
}