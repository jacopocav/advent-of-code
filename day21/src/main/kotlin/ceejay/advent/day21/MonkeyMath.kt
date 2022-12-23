package ceejay.advent.day21

import ceejay.advent.util.InputFile
import ceejay.advent.util.TimedResult
import ceejay.advent.util.timed

fun main() {
    part1().also {
        println("Part 1 Result: $it")
    }
    part2().also {
        println("Part 2 Result: $it")
    }
}

fun part1(): TimedResult<Long> = InputFile.withLines {
    timed {
        parse().buildTree("root").compute().simplify().toLong()
    }
}

fun part2(): TimedResult<Long> = InputFile.withLines {
    timed {
        val rawExpressions = parse().toMutableMap()

        rawExpressions["humn"] = Variable

        val superRoot = rawExpressions["root"] ?: error("no expression labeled 'root' found")

        require(superRoot is RawBinary) { "root expression is a literal" }

        val root1 = superRoot.left
        val root2 = superRoot.right

        val poly1 = rawExpressions.buildTree(root1).toPolynomial()
        val poly2 = rawExpressions.buildTree(root2).toPolynomial()

        // Solving equation poly1 - poly2 = 0
        val equation = poly1 - poly2

        // lazy trick: humn is referenced only once -> the resulting polynomial can only have degree 1
        check(equation.degree == 1) { "Unexpected polynomial with degree higher than 1" }

        // ax + b = 0 -> x = -b/a
        (-equation[0] / equation[1]).simplify().toLong()
    }
}

private fun Sequence<String>.parse(): Map<String, RawExpression> =
    map { it.split(": ") }
        .associate { (name, exp) ->
            if (exp.trim().contains(" ")) {
                val (left, op, right) = exp.split(" ")
                name to RawBinary(left, right, Operator.byChar(op.single()))
            } else {
                name to RawLiteral(exp.toInt().toRational())
            }
        }

private fun Map<String, RawExpression>.buildTree(rootName: String): Expression =
    when (val root = this[rootName] ?: error("no expression with name '$rootName' found")) {
        is RawLiteral -> Literal(root.value)
        is RawBinary -> Binary(
            left = buildTree(root.left),
            right = buildTree(root.right),
            operator = root.operator
        )

        is Variable -> Polynomial.x
    }