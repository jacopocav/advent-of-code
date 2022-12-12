package ceejay.advent.day11

import ceejay.advent.day11.MonkeyParser.MonkeyBuilder.Companion.conditionalThrowPrefix
import ceejay.advent.day11.MonkeyParser.MonkeyBuilder.Companion.operationPrefix
import ceejay.advent.day11.MonkeyParser.MonkeyBuilder.Companion.startingItemsPrefix
import ceejay.advent.day11.Operation.Operator.DIVIDE
import ceejay.advent.util.removeWhile

internal object MonkeyParser {
    fun parse(lines: Sequence<String>, debug: Boolean = false): List<Monkey> {
        val lineList = lines
            .filter { it.isNotBlank() }
            .toCollection(ArrayDeque())

        val monkeys = mutableListOf<Monkey>()

        while (lineList.isNotEmpty()) {
            val line = lineList.removeFirst()

            if (line.startsWith("Monkey ")) {
                monkeys += parseMonkey(line, lineList, debug)
            } else {
                throw IllegalArgumentException("Unexpected line: $line")
            }
        }

        return monkeys
    }

    private fun parseMonkey(
        firstLine: String,
        rest: MutableList<String>,
        debug: Boolean
    ): Monkey =
        MonkeyBuilder()
            .apply {
                id = firstLine.substringAfter("Monkey ")
                    .substringBefore(":")
                    .toInt()

                debugEnabled = debug

                boredOperation = Operation(DIVIDE, 3)

                val monkeyBody = rest.removeWhile { it.startsWith("  ") }.toMutableList()

                while (monkeyBody.isNotEmpty()) {
                    val line = monkeyBody.removeFirst().trim()
                    when {
                        line.startsWith(startingItemsPrefix) ->
                            startingItems = line.substringAfter(startingItemsPrefix)
                                .split(",")
                                .map { it.trim().toInt() }

                        line.startsWith(operationPrefix) ->
                            operation = line.substringAfter(operationPrefix)
                                .let { Operation.parse(it) }

                        line.startsWith(conditionalThrowPrefix) ->
                            conditionalThrow = line.substringAfter(conditionalThrowPrefix)
                                .let {
                                    ConditionalThrow.parse(
                                        listOf(
                                            it,
                                            monkeyBody.removeFirst(),
                                            monkeyBody.removeFirst()
                                        )
                                    )
                                }
                    }
                }

            }.build()

    class MonkeyBuilder {
        var id: Int? = null
        var startingItems: List<Int>? = null
        var operation: Operation? = null
        var conditionalThrow: ConditionalThrow? = null
        var boredOperation: Operation? = null
        var debugEnabled: Boolean = false

        fun build(): Monkey = Monkey(
            id = id ?: throwMissingProperty("id"),
            startingItems = startingItems ?: throwMissingProperty("startingItems"),
            operation = operation ?: throwMissingProperty("operation"),
            conditionalThrow = conditionalThrow ?: throwMissingProperty("conditionalThrow"),
            boredOperation = boredOperation ?: throwMissingProperty("boredOperation"),
            debugEnabled = debugEnabled
        )

        companion object {
            const val startingItemsPrefix = "Starting items: "
            const val operationPrefix = "Operation: "
            const val conditionalThrowPrefix = "Test: "
            fun throwMissingProperty(name: String): Nothing =
                throw IllegalArgumentException("no value for property '$name' was passed")
        }
    }
}