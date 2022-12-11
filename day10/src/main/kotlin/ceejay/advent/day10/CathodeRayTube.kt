package ceejay.advent.day10

import ceejay.advent.util.InputFile

fun main() {
    println("Part 1 Result: ${part1()}")
    println("Part 2 Result:")

}

fun part1(): Int {
    fun getIntensity(clockOrdinal: Int, registers: Map<String, Int>): Int {
        return clockOrdinal * (registers["X"] ?: 0)
    }

    val checkpointer = Checkpointer(20, 60, 100, 140, 180, 220)

    Cpu(
        startingRegisters = mapOf(AddX.register to 1),
        components = listOf(checkpointer)
    ).also { cpu ->
        InputFile.withLines {
            map { Parser.parse(it) }
                .forEach { cpu.run(it) }
        }
    }

    return checkpointer.checkpoints
        .map { (clock, registers) -> getIntensity(clock, registers) }
        .sum()
}

fun part2(
    pixelOnChar: Char = '█',
    pixelOffChar: Char = ' '
): String {
    val display = Display(
        width = 40,
        height = 6,
        spriteRegister = "X",
        spriteWidth = 3
    )

    Cpu(
        startingRegisters = mapOf(AddX.register to 1),
        components = listOf(display)
    ).also { cpu ->
        InputFile.withLines {
            map { Parser.parse(it) }
                .forEach { cpu.run(it) }
        }
    }

    return display.toString(
        onChar = pixelOnChar,
        offChar = pixelOffChar
    )
}

