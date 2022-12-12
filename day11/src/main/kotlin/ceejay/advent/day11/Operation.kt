package ceejay.advent.day11


internal data class Operation(val operator: Operator, val operand: Long) {

    operator fun invoke(old: Long) = operator.getNew(old, operand)

    val isNoOp = operator == Operator.NOOP

    enum class Operator(private inline val newGenerator: (Long, Long) -> Long) {
        PLUS({ old, addend -> old + addend }),
        TIMES({ old, multiplier -> old * multiplier }),
        SQUARE({ old, _ -> old * old }),
        DIVIDE({ old, divisor -> old / divisor }),
        NOOP({ old, _ -> old });

        fun getNew(old: Long, operand: Long): Long {
            return newGenerator(old, operand)
        }
    }

    companion object {
        val noOp = Operation(Operator.NOOP, 0L)
        fun parse(string: String): Operation {
            require(string.startsWith("new = old "))

            val (operatorString, operand) = string.substringAfter("new = old ")
                .split(" ")

            val operator = when (operatorString) {
                "+" -> Operator.PLUS
                "*" -> when (operand) {
                    "old" -> return Operation(Operator.SQUARE, (-1).toLong())
                    else -> Operator.TIMES
                }

                else -> throw IllegalArgumentException("unknown operator: $operatorString")
            }

            return Operation(operator, operand.toLong())
        }
    }
}