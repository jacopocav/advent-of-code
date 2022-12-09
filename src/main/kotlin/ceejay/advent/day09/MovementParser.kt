package ceejay.advent.day09

object MovementParser {

    fun parseLine(line: String): Sequence<Direction> {
        val split = line.split(" ")

        require(split.size == 2) { "Cannot parse line $line" }

        val direction = Direction.byCode(split[0])
        val distance = split[1].toInt()

        return generateSequence { direction }.take(distance)
    }
}