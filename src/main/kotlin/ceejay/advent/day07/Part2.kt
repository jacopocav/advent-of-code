package ceejay.advent.day07

import ceejay.advent.util.InputFile
import kotlin.math.min

fun main() {
    println(Part2())
}

object Part2 {
    private const val totalDiskSpace = 70_000_000
    private const val minUnusedSpace = 30_000_000
    private var totalUsedSpace: Int = -1
    operator fun invoke(): Int {
        val input = InputFile("07-DiskFull.txt")

        val interpreter = FileTreeInterpreter()

        val root = interpreter.interpret(input)

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