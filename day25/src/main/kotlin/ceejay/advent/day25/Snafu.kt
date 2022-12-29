package ceejay.advent.day25

data class Snafu(private val digits: List<Int>) {
    init {
        require(digits.all { it in reverseDigitMap.keys })
    }

    fun toLong(): Long = digits.toLong()


    operator fun unaryMinus() = Snafu(digits.map { -it })
    override fun toString(): String = digits.asReversed()
        .map { reverseDigitMap[it]!! }
        .joinToString("")

    companion object {
        private val digitMap = mapOf(
            '0' to 0,
            '1' to 1,
            '2' to 2,
            '-' to -1,
            '=' to -2,
        )

        private val reverseDigitMap = digitMap.entries.associate { (k, v) -> v to k }

        fun String.toSnafu(): Snafu {
            val digits = map { digitMap[it] ?: error("unsupported character: $it") }
                .reversed()
                .trimTrailingZeroes()
            return Snafu(digits)
        }

        private fun List<Int>.toLong() = indices.sumOf { i -> this[i].toLong() * (5L pow i) }

        private fun List<Int>.trimTrailingZeroes() = dropLastWhile { it == 0 }

        fun Long.toSnafu(): Snafu {
            val digits = toString(5).map { it.digitToInt() }.asReversed().toMutableList()

            for (i in digits.indices) {
                val digit = digits[i]

                if (digit > 2) {
                    digits.increment(i + 1)
                    digits[i] = digit - 5
                }
            }

            return Snafu(digits)
        }

        private infix fun Long.pow(exp: Int): Long {
            if (exp < 0) return 0

            var result = 1L
            repeat(exp) { result *= this }
            return result
        }

        private fun MutableList<Int>.increment(index: Int) {
            this[index] += 1
        }
    }
}
