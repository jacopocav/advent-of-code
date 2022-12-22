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
        val numbers = toNumbers()
        numbers.run()
        numbers.getResult()
    }
}

fun part2(): TimedResult<Long> = InputFile.withLines {
    timed {
        val numbers = toNumbers(distressSignal = 811589153)

        repeat(10) {
            numbers.run()
            numbers.forEach { it.moved = false }
        }

        numbers.getResult()
    }
}

private fun Sequence<String>.toNumbers(distressSignal: Long = 1) =
    map { it.toLong() * distressSignal }
        .mapIndexed { i, num -> Number(originalIndex = i, value = num) }
        .toMutableList()

private fun MutableList<Number>.run() {
    val count = size

    while (true) {
        val number = filter { !it.moved && it.value != 0L }
            .minByOrNull { it.originalIndex }
            ?: break

        val i = indexOf(number)

        removeAt(i)

        val shift = (i + number.value) % (count - 1)

        val newIndex = if (shift < 0) count - 1 + shift else shift

        add(newIndex.toInt(), number)
        number.moved = true
    }
}

private fun List<Number>.getResult(): Long {
    val result = map { it.value }
    val zero = result.indexOf(0)

    return result[(zero + 1000) % result.size] +
        result[(zero + 2000) % result.size] +
        result[(zero + 3000) % result.size]
}