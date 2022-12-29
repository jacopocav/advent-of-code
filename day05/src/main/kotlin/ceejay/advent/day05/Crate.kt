package ceejay.advent.day05

interface Crate {
    val cargo: Cargo
    fun move(move: Move)
}

class Crate9000(override val cargo: Cargo) : Crate {
    override fun move(move: Move) {
        val from = cargo[move.fromStack]
        val to = cargo[move.toStack]

        repeat(move.count) {
            val element = from.pop()
            to.push(element)
        }
    }
}

class Crate9001(override val cargo: Cargo) : Crate {
    override fun move(move: Move) {
        val from = cargo[move.fromStack]
        val to = cargo[move.toStack]

        val elements = from.popFirst(move.count)
        to.pushAll(elements)
    }
}