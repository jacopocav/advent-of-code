package ceejay.advent.day21

import kotlin.math.max

operator fun Polynomial.unaryMinus(): Polynomial = Polynomial(coefficients.map { -it })

operator fun Polynomial.plus(other: Polynomial): Polynomial {
    val (c1, c2) = getPaddedCoefficients(this, other)

    return Polynomial(c1.zip(c2) { a, b -> a + b })
}

operator fun Polynomial.minus(other: Polynomial): Polynomial = this + (-other)

operator fun Polynomial.times(other: Polynomial): Polynomial {
    val resultCoefficients = MutableList(degree + other.degree + 1) { 0.toRational() }

    for ((deg1, coef1) in coefficients.withIndex()) {
        for ((deg2, coef2) in other.coefficients.withIndex()) {
            resultCoefficients[deg1 + deg2] += coef1 * coef2
        }
    }

    return Polynomial(resultCoefficients)
}

operator fun Polynomial.div(other: Polynomial): Polynomial {
    if (other.isZero()) {
        throw ArithmeticException("division of $this by zero")
    }

    // Synthetic Polynomial Division
    val dividend = coefficients.reversed()
    val divisor = other.coefficients.reversed()

    val out = dividend.toMutableList()
    val normalizer = divisor[0]
    val separator = dividend.size - divisor.size + 1

    for (i in 0 until separator) {
        out[i] /= normalizer
        val coef = out[i]

        if (coef != 0.toRational()) {
            for (j in 1 until divisor.size) {
                out[i + j] += -divisor[j] * coef
            }
        }
    }


    val quotient = out.subList(0, separator)
    val remainder = out.subList(separator, out.size)


    return if (remainder.isNotEmpty())
        Polynomial(quotient.reversed()) + Polynomial(remainder.reversed())
    else
        Polynomial(quotient.reversed())
}

private fun List<Rational>.padTo(size: Int): List<Rational> {
    if (this.size >= size) return this

    val zeros = List(size - this.size) { 0.toRational() }
    return this + zeros
}

private fun getPaddedCoefficients(
    first: Polynomial,
    second: Polynomial,
): Pair<List<Rational>, List<Rational>> {
    val maxDegree = max(first.degree, second.degree) + 1

    return first.coefficients.padTo(maxDegree) to second.coefficients.padTo(maxDegree)
}
