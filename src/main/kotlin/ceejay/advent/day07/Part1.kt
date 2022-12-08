package ceejay.advent.day07

import ceejay.advent.util.InputFile

fun main() {
    println(Part1())
}

object Part1 {
    operator fun invoke(): Int {
        val input = InputFile("07-DiskFull.txt")
        val interpreter = FileTreeInterpreter()

        val root = interpreter.interpret(input)

        return root.sumSizesOfSubDirectoriesSmallerThan(100_000)
    }

    private fun Directory.sumSizesOfSubDirectoriesSmallerThan(sizeInclusive: Int): Int {
        var sum = 0

        if (totalSize() <= sizeInclusive) {
            sum += totalSize()
        }

        sum += children.values
            .sumOf { it.sumSizesOfSubDirectoriesSmallerThan(sizeInclusive) }

        return sum
    }
}