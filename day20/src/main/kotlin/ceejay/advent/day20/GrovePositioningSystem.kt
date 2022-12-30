package ceejay.advent.day20

import ceejay.advent.util.InputFile
import ceejay.advent.util.TimedResult
import ceejay.advent.util.timed

fun main() {
    part1().also {
        println("Part 1 Result: $it")
    }
    part2().also {
        println("Part 2 Result: $it")
    }
}

data class Number(val originalIndex: Int, val value: Long, var moved: Boolean = false)

fun part1(): TimedResult<Long> = InputFile.withLines {
    timed {
        toNumbers()
            .mix()
            .getFinalResult()
    }
}

fun part2(): TimedResult<Long> = InputFile.withLines {
    timed {
        toNumbers(distressSignal = 811589153)
            .mix(times = 10)
            .getFinalResult()
    }
}

private fun Sequence<String>.toNumbers(distressSignal: Long = 1) =
    map { it.toLong() * distressSignal }.toList()

private fun List<Long>.mix(times: Int = 1): List<IndexedValue<Long>> =
    this.withIndex().toMutableList().apply {
        repeat(times) {
            indices.forEach { index ->
                val currentIndex = indexOfFirst { it.index == index }
                val number = removeAt(currentIndex)
                // a.mod(b) -> a % b     if a > 0
                //          -> a % b + b if a < 0
                add((currentIndex + number.value).mod(size), number)
            }
        }
    }

private fun List<IndexedValue<Long>>.getFinalResult(): Long {
    val zeroIndex = indexOfFirst { it.value == 0L }

    return this[(zeroIndex + 1000) % size].value +
        this[(zeroIndex + 2000) % size].value +
        this[(zeroIndex + 3000) % size].value
}