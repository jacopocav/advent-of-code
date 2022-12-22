package ceejay.advent.day20

import ceejay.advent.util.InputFile
import ceejay.advent.util.TimedResult
import ceejay.advent.util.timed

fun main() {
    part1().apply {
        println("Part 1 Result: $result (total time: $time)")
    }
//    part2().apply {
//        println("Part 2 Result: $result (total time: $time)")
//    }
}

data class Number(val value: Int, var moved: Boolean)

fun part1(): TimedResult<Any> = InputFile.withLines {
    timed {
        val numbers = map { it.toInt() }.map { Number(it, false) }.toMutableList()
        val count = numbers.size
//        println(numbers.map { it.value })

        while (true) {
            val i = numbers.indexOfFirst { !it.moved && it.value != 0 }
                .takeIf { it >= 0 }
                ?: break

            val number = numbers[i]

            numbers.removeAt(i)

            val shift = (i + number.value) % (count - 1)

            val newIndex = if (shift < 0) count - 1 + shift else shift

            numbers.add(newIndex, number)
            number.moved = true
//            println(numbers.map { it.value })
        }

        val result = numbers.map { it.value }
        val zero = result.indexOf(0)

        result[(zero + 1000) % count] + result[(zero + 2000) % count] + result[(zero + 3000) % count]
    }
}

fun part2(): TimedResult<Any> = InputFile.withLines {
    TODO()
}