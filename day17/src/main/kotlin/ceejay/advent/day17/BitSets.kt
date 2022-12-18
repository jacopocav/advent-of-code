package ceejay.advent.day17

import java.util.*

fun BitSet.padLeft(count: Int) = BitSet(size() + count)
    .also { newBitSet ->
        for (i in 0 until size()) {
            newBitSet.set(i + count, get(i))
        }
    }

fun BitSet.shiftLeft() = BitSet(size())
    .also { newSet ->
        for (i in 1 until size()) {
            newSet[i - 1] = this[i]
        }
    }

fun BitSet.shiftRight() = BitSet(size())
    .also { newSet ->
        for (i in 0 until size() - 1) {
            newSet[i + 1] = this[i]
        }
    }

fun BitSet.copy(): BitSet = clone() as BitSet
fun String.toBitSet(trueChar: Char = '#'): BitSet {
    val bitSet = BitSet(length)

    forEachIndexed { i, char -> if (char == trueChar) bitSet.flip(i) }

    return bitSet
}

fun String.linesToBitSets(trueChar: Char = '#'): List<BitSet> =
    lines().map { it.toBitSet(trueChar) }