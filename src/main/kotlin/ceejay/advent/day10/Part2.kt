package ceejay.advent.day10

import ceejay.advent.util.InputFile

fun main() {
    println(Part2())
}

object Part2 {
    operator fun invoke(): String {
        val display = Display(40, 6, "X", 3)

        val cpu = Cpu(
            startingRegisters = mapOf(AddX.register to 1),
            components = listOf(display)
        )

        InputFile.withLines("10-Cpu.txt") { lines ->
            lines
                .map { Parser.parse(it) }
                .forEach { cpu.run(it) }
        }
        return display.toString()
    }
}