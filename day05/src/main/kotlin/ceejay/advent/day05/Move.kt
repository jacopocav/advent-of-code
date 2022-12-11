package ceejay.advent.day05

internal data class Move(val count: Int, val fromStack: Int, val toStack: Int) {

    override fun toString(): String {
        return "move $count from $fromStack to $toStack"
    }

    companion object {
        private val regex = "^move (?<count>[1-9]\\d*) from (?<from>[1-9]\\d*) to (?<to>[1-9]\\d*)$"
            .toRegex()

        fun parse(line: String): Move {
            val match = regex.matchEntire(line)
                ?: throw IllegalArgumentException("line $line does not match regex $regex")
            val groups = match.groups

            val count = groups["count"]?.value?.toInt()
                ?: throw AssertionError()
            val from = groups["from"]?.value?.toInt()
                ?: throw AssertionError()
            val to = groups["to"]?.value?.toInt()
                ?: throw AssertionError()

            return Move(count, from - 1, to - 1)
        }
    }
}
