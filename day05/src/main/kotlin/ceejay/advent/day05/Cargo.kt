package ceejay.advent.day05

internal class Cargo(private val stacks: List<Stack>) {
    operator fun get(index: Int): Stack {
        return stacks[index]
    }

    fun peekAll(): List<Char> {
        return stacks.map { it.peek() }
    }

    override fun toString(): String {
        return stacks
            .mapIndexed { i, stack -> "$i: $stack" }
            .joinToString(separator = "\n")
    }

    companion object {
        fun parse(text: String): Cargo {
            val rows = text.lines()
                .filter { it.isNotBlank() }
                .reversed()

            val idLine = rows.first()
            val stackCount = idLine.split(" ")
                .last { it.isNotBlank() }
                .toInt()

            val stacks = generateSequence { Stack() }
                .take(stackCount)
                .toList()

            rows.drop(1)
                .forEach { line ->
                    repeat(stackCount) { i ->
                        val element = line.getOrElse(4 * i + 1) { ' ' }
                        if (element.isLetter()) {
                            stacks[i].push(element)
                        }
                    }
                }

            return Cargo(stacks)
        }
    }
}