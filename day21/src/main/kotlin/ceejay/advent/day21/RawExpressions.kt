package ceejay.advent.day21

sealed interface RawExpression
data class RawBinary(
    val left: String,
    val right: String,
    val operator: Operator,
) : RawExpression

data class RawLiteral(
    val value: Long,
) : RawExpression