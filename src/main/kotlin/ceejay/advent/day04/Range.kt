package ceejay.advent.day04

data class Range(val minInclusive: Int, val maxInclusive: Int) {
    operator fun contains(other: Range): Boolean {
        return minInclusive <= other.minInclusive
                && maxInclusive >= other.maxInclusive
    }

    infix fun overlaps(other: Range): Boolean {
        return minInclusive <= other.maxInclusive
                && maxInclusive >= other.minInclusive
    }
}
