package ceejay.advent.day17

import java.util.*

internal enum class Shape(private val matrix: List<BitSet>) {
    HOR_LINE(
        """
        ####
    """.trimIndent().linesToBitSets()
    ),
    CROSS(
        """
        .#.
        ###
        .#.
    """.trimIndent().linesToBitSets()
    ),
    L(
        """
        ###
        ..#
        ..#
    """.trimIndent().linesToBitSets()
    ),
    VER_LINE(
        """
        #
        #
        #
        #
    """.trimIndent().linesToBitSets()
    ),
    SQUARE(
        """
        ##
        ##
    """.trimIndent().linesToBitSets()
    );

    fun matrix(): List<BitSet> = matrix.map { it.clone() as BitSet }

    val maxHeight = matrix.size
}