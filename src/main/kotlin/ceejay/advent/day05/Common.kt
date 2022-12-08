package ceejay.advent.day05

import ceejay.advent.util.InputFile

object Common {
    fun run(crateCreator: (Cargo) -> Crate): String {
        val input = InputFile("05-Crates.txt")

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
}