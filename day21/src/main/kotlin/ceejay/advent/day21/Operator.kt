package ceejay.advent.day21

enum class Operator(val char: Char, inline val function: (Rational, Rational) -> Rational) {
    PLUS(char = '+', function = { a, b -> a + b }),
    MINUS(char = '-', function = { a, b -> a - b }),
    TIMES(char = '*', function = { a, b -> a * b }),
    DIV(char = '/', function = { a, b -> a / b });

    companion object {
        private val charMap = values().associateBy { it.char }
        fun byChar(value: Char): Operator =
            charMap[value] ?: error("unsupported character: $value")
    }
}