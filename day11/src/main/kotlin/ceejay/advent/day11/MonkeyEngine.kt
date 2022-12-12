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


    /**
     * Common multiple of [DivisibilityThrow.divisor] in [monkeyMap]
     *
     * This is used to keep numbers low after every operation (multiplication):
     * `(multiplicationResult % commonMultiple)` has the same divisors of `multiplicationResult`
     */
    private val commonMultiple: Long? = if (monkeys.all { it.boredOperation.isNoOp }) {
        // the trick only works if the boredOperation does nothing
        monkeys
            .asSequence()
            .map { it.conditionalThrow }
            .filterIsInstance<DivisibilityThrow>()
            .map { it.divisor }
            .distinct()
            .fold(1L) { a, b -> a * b }
    } else null

    fun run(rounds: Int) {
        repeat(rounds) { runRound(it) }
    }

    val inspectionCounts: List<Long>
        get() = monkeyMap.values.map { it.inspectCount }

    private fun runRound(index: Int) {
        debug { "---ROUND ${index + 1}---" }
        monkeyMap.values
            .forEach { runMonkeyTurn(it) }
        debug()
    }

    private fun runMonkeyTurn(monkey: Monkey) {
        monkey.forEachItem {
            inspect()
            operate(commonMultiple)
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