package ceejay.advent.day17

import java.util.*

internal class BitChamber(internalWidth: Int) {
    private val fullWidth = internalWidth + 2

    private val content = mutableListOf(
        "#".repeat(fullWidth).toBitSet()
    )

    val height: Int get() = content.size

    fun addEmptyRow() {
        content += BitSet(fullWidth).apply {
            flip(0)
            flip(fullWidth - 1)
        }
    }

    fun towerHeight(): Int = content.indexOfLast { row ->
        row.nextSetBit(1) in 1..fullWidth - 2
    }

    fun makeSpaceFor(shape: Shape) {
        val towerHeight = towerHeight()
        val neededRows = towerHeight + 3 + shape.maxHeight + 1

        if (height < neededRows) {
            repeat(neededRows - content.size) { addEmptyRow() }
        }
    }

    fun collidesWith(shape: BitShape, offset: Int): Boolean =
        content.subList(offset, offset + shape.height)
            .filterIndexed { i, row -> row.intersects(shape[i]) }
            .isNotEmpty()

    fun addRestingShape(shape: BitShape, offset: Int) {
        for (i in offset until (offset + shape.height)) {
            content[i].or(shape[i - offset])
        }
    }

    fun encode(shape: BitShape, offset: Int): String =
        content.mapIndexed { i, row ->
            val orRow = if (i in offset until offset + shape.height)
                shape[i - offset].copy().apply { or(row) }
            else row

            i.toString().padStart(4) + " " +
                (0 until fullWidth).map { j ->
                    when {
                        row[j] && orRow[j] -> '#'
                        orRow[j] -> '@'
                        else -> '.'
                    }
                }.joinToString("")
        }
            .asReversed()
            .joinToString("\n")
}