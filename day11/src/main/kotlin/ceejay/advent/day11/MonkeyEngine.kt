package ceejay.advent.day11

import ceejay.advent.util.Debuggable
import java.util.*

internal class MonkeyEngine(
    monkeys: List<Monkey>,
    override val debugEnabled: Boolean = false
) : Debuggable {
    private val monkeyMap: SortedMap<Int, Monkey> = monkeys.groupBy { it.id }
        .mapValues { (id, values) ->
            require(values.size == 1) { "Found multiple monkeys with id $id" }
            values.first()
        }.toSortedMap()

    fun run(rounds: Int) {
        repeat(rounds) { runRound(it) }
    }

    fun getInspectionCounts(): List<Int> = monkeyMap.values.map { it.inspectCount }

    private fun runRound(index: Int) {
        debug { "---ROUND ${index + 1}---" }
        monkeyMap.values
            .forEach { runMonkeyTurn(it) }
        debug()
    }

    private fun runMonkeyTurn(monkey: Monkey) {
        monkey.forEachItem {
            inspect()
            operate()
            getBored()
            throwTo(receiver)
            debug()
        }
    }

    private val Monkey.Item.receiver: Monkey
        get() {
            val id = getReceiverId()
            return monkeyMap[id] ?: throw NoSuchElementException("Monkey $id")
        }
}