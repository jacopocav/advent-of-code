package ceejay.advent.day11

import ceejay.advent.util.Debuggable
import ceejay.advent.util.Debuggable.Companion.debug

internal data class Monkey(
    val id: Int,
    val startingItems: List<Long>,
    val operation: Operation,
    val conditionalThrow: ConditionalThrow,
    val boredOperation: Operation,
    override var debugEnabled: Boolean = false
) : Debuggable {
    private val items =
        startingItems.map(::Item).toMutableList()
    var inspectCount: Long = 0L
        private set

    fun forEachItem(block: Item.() -> Unit) {
        val toBeThrown = items.toList()
        items.clear()
        toBeThrown.forEach(block)
    }

    fun receive(value: Long) {
        items += Item(value)
    }

    inner class Item(
        private var value: Long
    ) {

        fun inspect() {
            debug { "Monkey $id inspects item $value" }
            inspectCount++
        }

        fun operate() {
            val old = value
            value = operation(old)
            debug { "Monkey $id transformed item $old to $value" }
        }

        fun getBored() {
            val old = value
            value = boredOperation(value)
            debug { "Monkey $id got bored with item $old. Item is now $value" }
        }

        fun throwTo(receiver: Monkey) {
            receiver.receive(value)
            debug { "Monkey $id throws item $value to monkey ${receiver.id}" }
        }

        fun getReceiverId(): Int = conditionalThrow.getReceiverId(value)
    }
}