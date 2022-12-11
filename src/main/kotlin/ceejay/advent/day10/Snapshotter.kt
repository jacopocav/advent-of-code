package ceejay.advent.day10

class Snapshotter(vararg clocksToSnapshot: Int) : Component {
    private var clockCounter = 0
    private val mutableSnapshots = mutableMapOf<Int, Map<String, Int>>()
    private val snapshotClocks = clocksToSnapshot.toSet()
    override fun onClockTick(registers: Map<String, Int>) {
        clockCounter++

        if (clockCounter in snapshotClocks) {
            mutableSnapshots[clockCounter] = registers.toMap()
        }
    }

    val snapshots: Map<Int, Map<String, Int>>
        get() = mutableSnapshots.toMap()
}