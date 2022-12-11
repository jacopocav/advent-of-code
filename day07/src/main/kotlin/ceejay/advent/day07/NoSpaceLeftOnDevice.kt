package ceejay.advent.day07

import ceejay.advent.util.InputFile
import kotlin.math.min

fun main() {
    println("Part 1 Result: ${Part1()}")
    println("Part 2 Result: ${Part2()}")
}

object Part1 {
    operator fun invoke(): Int {
        val interpreter = FileTreeInterpreter()

        val root = InputFile()
            .let { interpreter.interpret(it) }

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

object Part2 {
    private const val totalDiskSpace = 70_000_000
    private const val minUnusedSpace = 30_000_000
    private var totalUsedSpace: Int = -1
    operator fun invoke(): Int {
        val interpreter = FileTreeInterpreter()
        val root = InputFile()
            .let { interpreter.interpret(it) }

        totalUsedSpace = root.totalSize()

        return root.getSmallestCandidateDirectory()
    }

    private fun Directory.getSmallestCandidateDirectory(): Int {
        val dirSize = if (isValidCandidate()) totalSize() else Int.MAX_VALUE

        val minChildSize = children.values
            .minOfOrNull { it.getSmallestCandidateDirectory() }
            ?: Int.MAX_VALUE

        return min(dirSize, minChildSize)
    }

    private fun Directory.isValidCandidate(): Boolean {
        val currentUnusedSpace = totalDiskSpace - totalUsedSpace
        val unusedSpaceAfterDeletion = currentUnusedSpace + totalSize()

        return unusedSpaceAfterDeletion >= minUnusedSpace
    }
}