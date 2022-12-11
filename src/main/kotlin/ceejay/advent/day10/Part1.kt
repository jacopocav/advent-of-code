package ceejay.advent.day10

import ceejay.advent.util.InputFile

fun main() {
    println(Part1())
}

object Part1 {
    operator fun invoke(): Int {
        val snapshotter = Snapshotter(20, 60, 100, 140, 180, 220)

        val cpu = Cpu(
            startingRegisters = mapOf(AddX.register to 1),
            components = listOf(snapshotter)
        )

        InputFile.withLines("10-Cpu.txt") { lines ->
            lines
                .map { Parser.parse(it) }
                .forEach { cpu.run(it) }
        }

        return snapshotter.snapshots
            .map { (clock, registers) -> getIntensity(clock, registers) }
            .sum()
    }

    private fun getIntensity(clockOrdinal: Int, registers: Map<String, Int>): Int {
        return clockOrdinal * (registers["X"] ?: 0)
    }
}