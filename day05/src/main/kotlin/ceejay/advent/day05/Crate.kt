package ceejay.advent.day05

internal interface Crate {
    val cargo: Cargo
    fun move(move: Move)
}

internal class Crate9000(override val cargo: Cargo) : Crate {
    override fun move(move: Move) {
        val from = cargo[move.fromStack]
        val to = cargo[move.toStack]

        repeat(move.count) {
            val element = from.pop()
            to.push(element)
        }
    }
}

internal class Crate9001(override val cargo: Cargo) : Crate {
    override fun move(move: Move) {
        val from = cargo[move.fromStack]
        val to = cargo[move.toStack]

        val elements = from.popFirst(move.count)
        to.pushAll(elements)
    }
}