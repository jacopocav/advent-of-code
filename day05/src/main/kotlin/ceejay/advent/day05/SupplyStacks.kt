package ceejay.advent.day05

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1(): String {
    return run(::Crate9000)
}

fun part2(): String {
    return run(::Crate9001)
}

internal fun run(crateCreator: (Cargo) -> Crate): String {
    val input = InputFile()

    val parts = input.split("\n\n")

    assert(parts.size == 2)

    val cargo = Cargo.parse(parts[0])
    val crate = crateCreator(cargo)

    val moves = parts[1].split("\n")
        .filter { it.isNotBlank() }
        .map { Move.parse(it) }

    moves.forEach {
        crate.move(it)
    }

    return cargo.peekAll().joinToString(separator = "")
}