package ceejay.advent.day17

enum class Move(val char: Char) {
    LEFT('<'), RIGHT('>');

    companion object {
        private val map = values().associateBy { it.char }
        fun byChar(char: Char): Move = map[char]
            ?: error("unsupported character: $char")
    }
}