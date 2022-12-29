package ceejay.advent.day17

import java.util.*

class BitShape private constructor(private val matrix: List<BitSet>) {
    constructor(shape: Shape) : this(shape.matrix())

    val height: Int get() = matrix.size

    fun padLeft(charCount: Int) =
        BitShape(matrix.map { it.padLeft(charCount) })

    fun shift(move: Move) = BitShape(matrix.map {
        when (move) {
            Move.LEFT -> it.shiftLeft()
            Move.RIGHT -> it.shiftRight()
        }
    })

    operator fun get(row: Int) = matrix[row]

    companion object {
        val empty = BitShape(listOf())
    }
}