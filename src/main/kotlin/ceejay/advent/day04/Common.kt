package ceejay.advent.day04

object Common {
    fun parsePair(line: String): Pair<Range, Range> {
        val ranges = line.split(",")
            .map { parseRange(it) }

        assert(ranges.size == 2)

        return ranges[0] to ranges[1]
    }

    private fun parseRange(pair: String): Range {
        val bounds = pair.split("-")
            .map { it.toInt() }

        assert(bounds.size == 2)

        return Range(bounds[0], bounds[1])
    }
}