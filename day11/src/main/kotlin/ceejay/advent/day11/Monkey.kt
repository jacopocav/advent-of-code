package ceejay.advent.day11

import ceejay.advent.util.Debuggable

internal class Monkey(
    val id: Int,
    startingItems: List<Int>,
    val operation: Operation,
    val conditionalThrow: ConditionalThrow,
    val boredOperation: Operation,
    override val debugEnabled: Boolean = false
) : Debuggable {
    private val items =
        startingItems.map(::Item).toMutableList()
    var inspectCount: Int = 0
        private set

    fun forEachItem(block: Item.() -> Unit) {
        val toBeThrown = items.toList()
        items.clear()
        toBeThrown.forEach(block)
    }

    fun receive(value: Int) {
        items += Item(value)
    }

    inner class Item(
        private var value: Int
    ) {

        fun inspect() {
            debug { "Monkey $id inspects item $value" }
            inspectCount++
        }

        fun operate() {
            val old = value
            value = operation(value)
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