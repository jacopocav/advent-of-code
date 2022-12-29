package ceejay.advent.day09

enum class Direction(val code: String, val diff: Position) {
    RIGHT("R", Position(0, 1)),
    LEFT("L", Position(0, -1)),
    UP("U", Position(1, 0)),
    DOWN("D", Position(-1, 0));

    companion object {
        private val all = values().toSet()
        fun byCode(code: String): Direction {
            return all.find { it.code == code }
                ?: throw NoSuchElementException("no Direction found with code $code")
        }
    }
}