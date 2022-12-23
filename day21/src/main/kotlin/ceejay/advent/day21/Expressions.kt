package ceejay.advent.day21

sealed interface Expression {
    fun compute(): Long
}

data class Binary(
    val left: Expression,
    val right: Expression,
    val operator: Operator,
) : Expression {
    override fun compute(): Long {
        val l = left.compute()
        val r = right.compute()

        return operator.function(l, r)
    }
}

data class Literal(val value: Long) : Expression {
    override fun compute(): Long = value
}