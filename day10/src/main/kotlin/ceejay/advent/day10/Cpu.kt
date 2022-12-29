package ceejay.advent.day10

class Cpu(
    startingRegisters: Map<String, Int>,
    private val components: Iterable<Component> = listOf()
) {
    private val registers = startingRegisters.toMutableMap()

    fun run(command: Command) {
        repeat(command.clocks) { tickClock() }
        when (command) {
            is NoOp -> Unit // does nothing
            is AddX -> registers[AddX.register] = (registers[AddX.register] ?: 1) + command.amount
        }
    }

    private fun tickClock() {
        components.forEach { it.onClockTick(registers) }
    }
}

interface Component {
    fun onClockTick(registers: Map<String, Int>)
}