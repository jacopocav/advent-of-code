package ceejay.advent.day10

class Checkpointer(vararg checkpointClocks: Int) : Component {
    private var clockCounter = 0
    private val mutableCheckpoints = mutableMapOf<Int, Map<String, Int>>()
    private val checkpointClocks = checkpointClocks.toSet()
    override fun onClockTick(registers: Map<String, Int>) {
        clockCounter++

        if (clockCounter in checkpointClocks) {
            mutableCheckpoints[clockCounter] = registers.toMap()
        }
    }

    val checkpoints: Map<Int, Map<String, Int>>
        get() = mutableCheckpoints.toMap()
}