package ceejay.advent.day13


internal sealed interface Packet : Comparable<Packet> {
    fun isList(): Boolean
    fun isNumber(): Boolean
}

internal data class NumberPacket(val value: Int) : Packet {
    override fun compareTo(other: Packet): Int = when (other) {
        is NumberPacket -> value compareTo other.value
        is ListPacket -> ListPacket(this) compareTo other
    }

    override fun isList(): Boolean = false

    override fun isNumber(): Boolean = true
}

internal data class ListPacket(val content: List<Packet>) : Packet {
    constructor(numberPacket: Packet) : this(listOf(numberPacket))

    override fun compareTo(other: Packet): Int = when (other) {
        is NumberPacket -> compareTo(ListPacket(other))
        is ListPacket -> content.zip(other.content)
            .map { (mine, theirs) -> mine compareTo theirs }
            .find { it != 0 }
            ?: (content.size compareTo other.content.size)
    }

    override fun isList(): Boolean = true

    override fun isNumber(): Boolean = false
}
