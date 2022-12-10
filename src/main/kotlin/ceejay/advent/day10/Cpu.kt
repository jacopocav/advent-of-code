package ceejay.advent.day10

class Cpu(startingRegisters: Map<String, Int>, private val snapshotClocks: Set<Int>) {
    private val registers = startingRegisters.toMutableMap()
    private var clockCount = 0
    private val snapshots = mutableMapOf<Int, Map<String, Int>>()

    fun run(command: Command) {
        repeat(command.clocks) { tickClock() }
        when (command) {
            is NoOp -> Unit // does nothing
            is AddX -> registers["X"] = (registers["X"] ?: 1) + command.amount
        }
    }

    private fun tickClock() {
        clockCount++
        if (clockCount in snapshotClocks) {
            snapshots[clockCount] = registers.toMap()
        }
    }

    fun getSnapshots(): Map<Int, Map<String, Int>> {
        return snapshots.toMap()
    }
}