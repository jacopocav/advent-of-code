package ceejay.advent.day18

import ceejay.advent.util.InputFile
import ceejay.advent.util.consume

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1() = InputFile.withLines {
    val voxels = map { Voxel.parse(it) }
        .toCollection(ArrayDeque())

    val adjacentCounters = voxels.associateWith { 0L }
        .toMutableMap()

    voxels.consume { cube ->
        voxels
            .filter { cube isAdjacentTo it }
            .forEach { adjacent ->
                adjacentCounters.increment(cube)
                adjacentCounters.increment(adjacent)
            }
    }

    adjacentCounters.values.sumOf { 6 - it }
}

fun part2(): Long = InputFile.withLines {
    TODO()
}

private fun <T> MutableMap<T, Long>.increment(key: T) {
    this[key] = this[key]!! + 1
}