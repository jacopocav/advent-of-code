package ceejay.advent.day11


internal data class Operation(val operator: Operator, val operand: Int) {
    operator fun invoke(old: Int) = operator.getNew(old, operand)

    enum class Operator(private inline val newGenerator: (Int, Int) -> Int) {
        PLUS({ old, addend -> old + addend }),
        TIMES({ old, multiplier -> old * multiplier }),
        SQUARE({ old, _ -> old * old }),
        DIVIDE({ old, divisor -> old / divisor });

        fun getNew(old: Int, operand: Int): Int {
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
                    "old" -> return Operation(Operator.SQUARE, -1)
                    else -> Operator.TIMES
                }

                else -> throw IllegalArgumentException("unknown operator: $operatorString")
            }

            return Operation(operator, operand.toInt())
        }
    }
}