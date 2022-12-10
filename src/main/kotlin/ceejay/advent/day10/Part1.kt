package ceejay.advent.day10

import ceejay.advent.util.InputFile

fun main() {
    println(Part1())
}

object Part1 {
    operator fun invoke(): Int {
        val cpu = Cpu(
            startingRegisters = mapOf("X" to 1),
            snapshotClocks = setOf(20, 60, 100, 140, 180, 220)
        )

        InputFile.withLines("10-Cpu.txt") { lines ->
            lines
                .map { Parser.parse(it) }
                .forEach { cpu.run(it) }
        }

        return cpu.getSnapshots()
            .map { (clock, registers) -> getIntensity(clock, registers) }
            .sum()
    }

    fun getIntensity(clockOrdinal: Int, registers: Map<String, Int>): Int {
        return clockOrdinal * (registers["X"] ?: 0)
    }
}