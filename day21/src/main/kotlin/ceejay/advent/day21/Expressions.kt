package ceejay.advent.day21

import ceejay.advent.day21.Operator.*

sealed interface Expression {
    fun compute(): Rational

    fun toPolynomial(): Polynomial
}

data class Binary(
    val left: Expression,
    val right: Expression,
    val operator: Operator,
) : Expression {
    override fun compute(): Rational {
        val l = left.compute()
        val r = right.compute()

        return operator.function(l, r)
    }

    override fun toPolynomial(): Polynomial {
        val l = left.toPolynomial()
        val r = right.toPolynomial()

        return when (operator) {
            PLUS -> l + r
            MINUS -> l - r
            TIMES -> l * r
            DIV -> l / r
        }
    }
}

data class Literal(val value: Rational) : Expression {
    override fun compute(): Rational = value

    override fun toPolynomial(): Polynomial = Polynomial(listOf(value))
}

class Polynomial(coefficients: List<Rational>) : Expression {
    val coefficients: List<Rational>

    init {
        require(coefficients.isNotEmpty()) { "Polynomial must have at least one term" }
        this.coefficients = coefficients.dropLastWhile { it == Rational(0) }
    }

    val degree = this.coefficients.size - 1

    fun isZero() = degree == 0 && this[0] == Rational(0)

    override fun compute(): Rational = throw UnsupportedOperationException()

    override fun toPolynomial(): Polynomial = this

    operator fun get(degree: Int): Rational = coefficients[degree]
    override fun toString(): String = coefficients.mapIndexed { i, c ->
        val coeff = if (c > 0) "+$c" else "$c"
        val x = when (i) {
            0 -> ""
            1 -> "x"
            else -> "x^$i"
        }
        coeff + x

    }.asReversed().joinToString(separator = " ")

    companion object {
        val x = Polynomial(listOf(Rational(0), Rational(1)))
    }
}