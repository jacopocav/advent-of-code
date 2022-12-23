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

fun part1(): TimedResult<Any> = InputFile.withLines {
    timed {
        parse().buildTree("root").compute()
    }
}

fun part2(): TimedResult<Any> = InputFile.withLines {
    timed {
        TODO()
    }
}

private fun Sequence<String>.parse(): Map<String, RawExpression> =
    map { it.split(": ") }
        .associate { (name, exp) ->
            if (exp.trim().contains(" ")) {
                val (left, op, right) = exp.split(" ")
                name to RawBinary(left, right, Operator.byChar(op.single()))
            } else {
                name to RawLiteral(exp.toLong())
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
    }