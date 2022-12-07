package ceejay.advent.day03

import ceejay.advent.util.InputFile

fun main() {
    println(Part1())
}

object Part1 {
    operator fun invoke(): Int {
        val input = InputFile("03-Rucksacks.txt")

        return input.split("\n")
            .dropLast(1)
            .map { getDuplicateChar(it) }
            .sumOf { computePriority(it) }
    }

    private fun getDuplicateChar(line: String): Char {
        val halfLength = line.length / 2
        val firstHalfChars = line.substring(0, halfLength).toSet()
        val secondHalfChars = line.substring(halfLength).toSet()

        val commonChars = firstHalfChars.intersect(secondHalfChars)

        assert(commonChars.size == 1)

        return commonChars.first()
    }

    private fun computePriority(item: Char): Int {
        assert(item.isLetter())

        return if (item.isUpperCase()) {
            item.code - 'A'.code + 27
        } else {
            item.code - 'a'.code + 1
        }
    }
}