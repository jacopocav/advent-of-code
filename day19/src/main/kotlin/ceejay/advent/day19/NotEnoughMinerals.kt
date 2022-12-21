package ceejay.advent.day19

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1(): Int = InputFile.withLines {
    map { Blueprint.parse(it) }
        .sumOf { blueprint ->
            val outcome = BlueprintExecutor(blueprint).findBestGeodeProducingPath(maxMinutes = 24)
            outcome.geodes * blueprint.id
        }
}

fun part2(): Int = InputFile.withLines {
    map { Blueprint.parse(it) }
        .take(3)
        .map { blueprint ->
            BlueprintExecutor(blueprint).findBestGeodeProducingPath(maxMinutes = 32).geodes
        }.reduce { a, b -> a * b }
}