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
    val adjacentCounters = voxels.associateWith { 0 }
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

fun part2() = InputFile.withLines {
    val voxels = map { Voxel.parse(it) }
        .toSet()

    val xMax = voxels.maxOf { it.x } + 1
    val xMin = voxels.minOf { it.x } - 1
    val yMax = voxels.maxOf { it.y } + 1
    val yMin = voxels.minOf { it.y } - 1
    val zMax = voxels.maxOf { it.z } + 1
    val zMin = voxels.minOf { it.z } - 1

    fun Voxel.isWithinBoundaries() = x in xMin..xMax
        && y in yMin..yMax
        && z in zMin..zMax

    val firstAir = Voxel(xMin, yMin, zMin)
    val queue = mutableSetOf(firstAir)
    val outsideRockCounters = mutableMapOf<Voxel, Int>().withDefault { 0 }
    val visited = mutableSetOf(firstAir)

    while (queue.isNotEmpty()) {
        val air = queue.removeFirst()
        visited += air

        val (rocks, airs) = air.neighbors()
            .filter { it.isWithinBoundaries() && it !in visited }
            .partition { it in voxels }

        for (rock in rocks) {
            outsideRockCounters.increment(rock)
        }

        queue += airs
    }
    outsideRockCounters.values.sum()
}

private inline fun <T> MutableMap<T, Int>.increment(key: T) {
    this[key] = getValue(key) + 1
}

private inline fun <T> MutableSet<T>.removeFirst() = first().also { remove(it) }