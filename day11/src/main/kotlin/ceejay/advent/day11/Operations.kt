package ceejay.advent.day11


sealed interface Operation {
    val isNoOp: Boolean

    fun toList(): List<Operation>

    operator fun invoke(old: Long): Long

    operator fun plus(other: Operation): Operation =
        CompositeOperation(toList() + other.toList())

    companion object {
        operator fun invoke(operator: SimpleOperation.Operator, operand: Long): Operation =
            SimpleOperation(operator, operand)

        operator fun invoke(text: String): Operation =
            SimpleOperation.parse(text)
    }
}

object Squared : Operation {
    override val isNoOp = false


    override fun toList() = listOf(this)

    override fun invoke(old: Long) = old * old
}

object NoOp : Operation {
    override val isNoOp = true

    override fun toList() = listOf(this)

    override fun invoke(old: Long) = old
}

data class SimpleOperation(val operator: Operator, val operand: Long) : Operation {

    override fun invoke(old: Long) = operator.getNew(old, operand)

    override val isNoOp = false

    override fun toList() = listOf(this)

    enum class Operator(private inline val newGenerator: (Long, Long) -> Long) {
        PLUS({ old, addend -> old + addend }),
        TIMES({ old, multiplier -> old * multiplier }),
        DIVIDE({ old, divisor -> old / divisor }),
        MODULO({ old, divisor -> old % divisor });

        fun getNew(old: Long, operand: Long): Long {
            return newGenerator(old, operand)
        }
    }

    companion object {
        fun parse(string: String): Operation {
            require(string.startsWith("new = old "))

            val (operatorString, operand) = string.substringAfter("new = old ")
                .split(" ")

            val operator = when (operatorString) {
                "+" -> Operator.PLUS
                "*" -> when (operand) {
                    "old" -> return Squared
                    else -> Operator.TIMES
                }

                else -> throw IllegalArgumentException("unknown operator: $operatorString")
            }

            return SimpleOperation(operator, operand.toLong())
        }
    }
}

data class CompositeOperation(private val operations: List<Operation>) : Operation {
    override val isNoOp: Boolean = operations.all { it.isNoOp }

    override fun toList() = operations

    override fun invoke(old: Long) = operations
        .fold(old) { accumulator, operation -> operation(accumulator) }
}