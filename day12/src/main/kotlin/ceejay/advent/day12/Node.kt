package ceejay.advent.day12

class Node(
    val id: Pair<Int, Int>,
    val edges: MutableSet<Node> = mutableSetOf(),
    val isStart: Boolean,
    val isEnd: Boolean,
    var dist: Int = Int.MAX_VALUE,
    var prev: Node? = null
) {
    init {
        if (isStart) {
            dist = 0
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is Node && other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}