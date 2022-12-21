package ceejay.advent.util

// Int
inline fun Int.isEven() = isDivisibleBy(2)
inline fun Int.isOdd() = !isEven()
inline infix fun Int.isDivisibleBy(divisor: Int) = this % divisor == 0

// BigInteger
inline infix fun Long.isDivisibleBy(divisor: Long) = this % divisor == 0L