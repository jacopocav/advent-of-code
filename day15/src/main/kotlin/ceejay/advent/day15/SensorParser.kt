package ceejay.advent.day15

import ceejay.advent.day15.Grid.SensorBeacon

object SensorParser {
    private val lineRegex =
        "^Sensor at x=(?<sensorColumn>-?\\d+), y=(?<sensorRow>-?\\d+): closest beacon is at x=(?<beaconColumn>-?\\d+), y=(?<beaconRow>-?\\d+)$".toRegex()

    fun parse(lines: Sequence<String>): Collection<SensorBeacon> =
        lines.map { it.parseSensorBeacon() }
            .toList()

    private fun String.parseSensorBeacon(): SensorBeacon {
        val result = lineRegex.matchEntire(this)
            ?: throw IllegalArgumentException("line $this does not match regex $lineRegex")

        val sensorColumn = result.groups["sensorColumn"]!!.value.toInt()
        val sensorRow = result.groups["sensorRow"]!!.value.toInt()

        val beaconColumn = result.groups["beaconColumn"]!!.value.toInt()
        val beaconRow = result.groups["beaconRow"]!!.value.toInt()

        return SensorBeacon(
            sensor = Coordinates(column = sensorColumn, row = sensorRow),
            beacon = Coordinates(column = beaconColumn, row = beaconRow)
        )
    }
}