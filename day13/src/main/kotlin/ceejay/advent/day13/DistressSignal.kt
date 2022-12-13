package ceejay.advent.day13

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result: ${part2()}")
}

fun part1(): Int = InputFile.withLines {
    PacketParser.parse(this)
        .windowed(2, 2)
        .map { (first, second) -> first compareTo second }
        .mapIndexed { i, result -> Pair(i, result) }
        .filter { (_, result) -> result < 0 }
        .sumOf { it.first + 1 }
}

fun part2(): Int = InputFile.withLines {
    val divider1 = ListPacket(ListPacket(NumberPacket(2)))
    val divider2 = ListPacket(ListPacket(NumberPacket(6)))
    val additionalDividers = sequenceOf(divider1, divider2)

    val sortedPackets = (PacketParser.parse(this) + additionalDividers).sorted().toList()

    (sortedPackets.indexOf(divider1) + 1) * (sortedPackets.indexOf(divider2) + 1)
}