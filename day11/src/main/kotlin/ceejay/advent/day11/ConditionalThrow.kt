package ceejay.advent.day11

import ceejay.advent.util.isDivisibleBy

internal sealed interface ConditionalThrow {
    fun getReceiverId(item: Long): Int

    fun test(item: Long): Boolean

    companion object {
        fun parse(lines: List<String>): ConditionalThrow {
            require(lines.size == 3)

            val header = lines.first()
            val trueId = lines.find { it.trim().startsWith("If true: ") }
                .let {
                    it ?: throw IllegalArgumentException("no line found starting with 'If true: '")
                }.substringAfter("If true: throw to monkey ")
                .toInt()

            val falseId = lines.find { it.trim().startsWith("If false: ") }
                .let {
                    it ?: throw IllegalArgumentException("no line found starting with 'If false: '")
                }.substringAfter("If false: throw to monkey ")
                .toInt()



            if (header.startsWith("divisible by ")) {
                val divisor = header.substringAfter("divisible by ")
                    .toLong()

                return DivisibilityThrow(divisor, trueId, falseId)
            } else {
                throw IllegalArgumentException("unknown test type: $header")
            }
        }
    }
}

internal data class DivisibilityThrow(
    val divisor: Long,
    private val trueReceiverId: Int,
    private val falseReceiverId: Int
) : ConditionalThrow {
    override fun getReceiverId(item: Long): Int =
        if (test(item)) trueReceiverId else falseReceiverId

    override fun test(item: Long): Boolean = item isDivisibleBy divisor
}