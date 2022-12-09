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

data class Movement(val direction: Direction, val distance: Int) {

    /**
     * Converts this movement to a list of [direction] repeated [distance] times.
     *
     * This movement is equivalent to [distance] movements of one step toward [direction].
     */
    fun toUnitMovements(): List<Direction> {
        return List(distance) { direction }
    }

    companion object {
        fun parse(text: String): Movement {
            val split = text.split(" ")

            require(split.size == 2) { "Cannot parse line $text" }

            val direction = Direction.byCode(split[0])
            val distance = split[1].toInt()
            return Movement(direction, distance)
        }
    }
}
