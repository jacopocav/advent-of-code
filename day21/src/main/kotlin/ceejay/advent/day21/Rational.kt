package ceejay.advent.day21

import ceejay.advent.util.gcd
import ceejay.advent.util.lcm
import kotlin.math.abs
import kotlin.math.sign

data class Rational private constructor(val numerator: Long, val denominator: Long = 1L) :
    Comparable<Rational> {
    init {
        if (denominator == 0L) throw ArithmeticException("division by zero")
    }

    private val sign = when {
        numerator == 0L -> 0
        numerator.sign == denominator.sign -> 1
        else -> -1
    }

    private val isFractional = abs(denominator) != 1L

    operator fun plus(other: Rational): Rational {
        val lcm = lcm(denominator, other.denominator)

        return Rational(
            numerator = (lcm / denominator) * numerator + (lcm / other.denominator) * other.numerator,
            denominator = lcm,
        ).simplify() // always simplify, to avoid Long overflows
    }

    operator fun minus(other: Rational): Rational = this + (-other)

    operator fun times(other: Rational): Rational =
        Rational(numerator * other.numerator, denominator * other.denominator)
            .simplify() // always simplify, to avoid Long overflows

    operator fun div(other: Rational): Rational = this * other.inverted()

    operator fun unaryMinus() = Rational(-sign * numerator, abs(denominator))

    private fun inverted() = Rational(denominator, numerator)

    override fun compareTo(other: Rational): Int {
        val lcm = lcm(denominator, other.denominator)
        return ((lcm / denominator) * numerator).compareTo((lcm / other.denominator) * other.numerator)
    }

    operator fun compareTo(other: Long): Int = compareTo(Rational(other))
    fun simplify(): Rational {
        val gcd = gcd(numerator, denominator)
        return Rational(sign * abs(numerator) / gcd, abs(denominator) / gcd)
    }

    override fun toString(): String = simplify().run {
        if (denominator != 1L) "$numerator/$denominator"
        else numerator.toString()
    }

    fun toLong(): Long = (sign * abs(numerator)).takeIf { !isFractional }
        ?: throw ArithmeticException("cannot convert Rational $this to Long")

    companion object {
        operator fun invoke(numerator: Long, denominator: Long = 1L): Rational {
            return when {
                numerator == 0L && denominator == 1L -> zero
                numerator == 1L && denominator == 1L -> one
                else -> Rational(numerator, denominator).simplify()
            }
        }

        private val zero = Rational(0)
        private val one = Rational(1)
    }
}

fun Long.toRational() = Rational(this)
fun Int.toRational() = toLong().toRational()
