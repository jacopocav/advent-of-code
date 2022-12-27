package ceejay.advent.day22

enum class Cell(val char: Char) {
    VOID(' '),
    FREE('.'),
    WALL('#');

    companion object {
        private val charMap = values().associateBy { it.char }
        fun byChar(char: Char): Cell = charMap[char] ?: error("unexpected character: $char")
    }
}