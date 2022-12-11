package ceejay.advent.day03

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1(): Int {
    return InputFile.withLines {
        map { it.getDuplicateChar() }
            .sumOf { it.getPriority() }
    }
}

fun part2(): Int {
    return InputFile.withLines {
        windowed(3, 3)
            .map { it.getCommonChar() }
            .sumOf { it.getPriority() }
    }
}

private fun List<String>.getCommonChar(): Char {
    assert(size == 3)

    val commonChars = map { it.toSet() }
        .reduce { a, b -> a.intersect(b) }

    assert(commonChars.size == 1)

    return commonChars.first()
}

private fun String.getDuplicateChar(): Char {
    val halfLength = length / 2
    val firstHalfChars = substring(0, halfLength).toSet()
    val secondHalfChars = substring(halfLength).toSet()

    val commonChars = firstHalfChars.intersect(secondHalfChars)

    assert(commonChars.size == 1)

    return commonChars.first()
}

private fun Char.getPriority(): Int {
    require(isLetter())

    return if (isUpperCase()) {
        code - 'A'.code + 27
    } else {
        code - 'a'.code + 1
    }
}
