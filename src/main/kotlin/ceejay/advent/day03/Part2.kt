package ceejay.advent.day03

import ceejay.advent.util.InputFile

fun main() {
    println(Part2())
}

object Part2 {
    operator fun invoke(): Int {
        val input = InputFile("03-Rucksacks.txt")

        return input.split("\n")
            .dropLast(1)
            .windowed(3, 3)
            .map { getCommonChar(it) }
            .sumOf { computePriority(it) }
    }

    private fun getCommonChar(lines: List<String>): Char {
        assert(lines.size == 3)

        val commonChars = lines
            .map { it.toSet() }
            .reduce { a, b -> a.intersect(b) }

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