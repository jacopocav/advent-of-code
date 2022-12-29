package ceejay.advent.day11

import ceejay.advent.day11.SimpleOperation.Operator.MODULO
import ceejay.advent.util.Debuggable
import ceejay.advent.util.Debuggable.Companion.debug
import java.util.*

class MonkeyEngine(
    monkeys: List<Monkey>,
    override var debugEnabled: Boolean = false
) : Debuggable {
    /**
     * Common multiple of [DivisibilityThrow.divisor] in [monkeyMap]
     *
     * This is used to keep numbers low after every operation (multiplication):
     * `(multiplicationResult % commonMultiple)` has the same divisors of `multiplicationResult`
     */
    private val commonMultiple: Long? = if (monkeys.all { it.boredOperation.isNoOp }) {
        // the trick only works if all boredOperations do nothing
        monkeys
            .asSequence()
            .map { it.conditionalThrow }
            .filterIsInstance<DivisibilityThrow>()
            .map { it.divisor }
            .distinct()
            .fold(1L) { a, b -> a * b }
    } else null


    private val monkeyMap: SortedMap<Int, Monkey> = monkeys.groupBy { it.id }
        .mapValues { (id, values) ->
            require(values.size == 1) { "Found multiple monkeys with id $id" }
            values.first().withModuloTrickOperation()
        }.toSortedMap()

    val inspectionCounts: List<Long>
        get() = monkeyMap.values.map { it.inspectCount }

    fun run(rounds: Int) {
        repeat(rounds) { runRound(it) }
    }

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

    private fun Monkey.withModuloTrickOperation() = commonMultiple
        ?.let { copy(operation = operation + Operation(MODULO, it)) }
        ?: this
}