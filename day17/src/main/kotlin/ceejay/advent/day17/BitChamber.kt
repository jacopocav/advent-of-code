package ceejay.advent.day17

import java.util.*

internal class BitChamber(internalWidth: Int) {
    private val fullWidth = internalWidth + 2

    private val content = mutableListOf(
        "#".repeat(fullWidth).toBitSet()
    )

    val height: Long get() = content.size.toLong()
    private var heightOffset: Long = 0

    fun towerHeight(): Long = rawTowerHeight() + heightOffset

    private fun rawTowerHeight(): Int = content.indexOfLast { row ->
        row.nextSetBit(1) in 1..fullWidth - 2
    }

    fun makeSpaceFor(shape: Shape) {
        val towerHeight = rawTowerHeight()
        val neededRows = towerHeight + 3 + shape.maxHeight + 1

        if (height < neededRows) {
            repeat(neededRows - content.size) { addEmptyRow() }
        }
    }

    private fun addEmptyRow() {
        content += BitSet(fullWidth).apply {
            flip(0)
            flip(fullWidth - 1)
        }
    }

    fun collidesWith(shape: BitShape, offset: Long): Boolean = offset.withRaw { rawOffset ->
        return content.subList(rawOffset, rawOffset + shape.height)
            .filterIndexed { i, row -> row.intersects(shape[i]) }
            .isNotEmpty()
    }

    fun addRestingShape(shape: BitShape, offset: Long) = offset.withRaw { rawOffset ->
        for (i in rawOffset until (rawOffset + shape.height)) {
            content[i].or(shape[i - rawOffset])
        }
    }

    fun profile(): List<Int> = (1..fullWidth - 2)
        .map { column -> content.indexOfLast { it[column] } }
        .let { profile ->
            val minHeight = profile.min()
            profile.map { it - minHeight }
        }

    fun encode(shape: BitShape, offset: Long): String = offset.withRaw { rawOffset ->
        return content.mapIndexed { rawIndex, row ->
            val orRow = if (rawIndex in rawOffset until rawOffset + shape.height)
                shape[rawIndex - rawOffset].copy().apply { or(row) }
            else row

            val index = rawIndex + heightOffset

            "$index " +
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

    fun addHeightOffset(value: Long) {
        heightOffset += value
    }

    private inline fun <T> Long.withRaw(block: (Int) -> T): T {
        return block((this - heightOffset).toInt())
    }
}