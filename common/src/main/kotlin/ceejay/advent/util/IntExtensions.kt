package ceejay.advent.util

fun Int.isEven() = isDivisibleBy(2)
fun Int.isOdd() = !isEven()
fun Int.isDivisibleBy(divisor: Int) = this % divisor == 0