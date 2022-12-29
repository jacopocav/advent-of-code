package ceejay.advent.day05

class Stack {
    private val values: ArrayDeque<Char> = ArrayDeque()

    fun peek(): Char {
        return values.first()
    }

    fun pop(): Char {
        return values.removeFirst()
    }

    fun popFirst(num: Int): List<Char> {
        val elements = values.take(num)
        repeat(num) { values.removeFirst() }
        return elements
    }

    fun push(element: Char) {
        values.addFirst(element)
    }

    fun pushAll(elements: List<Char>) {
        elements
            .asReversed()
            .forEach { values.addFirst(it) }
    }

    override fun toString(): String {
        return values.joinToString("", "[", "]")
    }
}
