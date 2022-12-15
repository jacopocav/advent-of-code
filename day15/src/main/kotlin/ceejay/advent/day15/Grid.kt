package ceejay.advent.day15

import ceejay.advent.util.isOdd
import kotlin.collections.Map.Entry


internal class Grid(sensors: Collection<SensorBeacon>) {

    private val sensorRanges: Map<Coordinates, Int> = sensors.groupingBy { it.sensor }
        .aggregate { _, _, sensorBeacon, first ->
            if (first) sensorBeacon.range
            else throw IllegalArgumentException("Duplicate sensor found: ${sensorBeacon.sensor}")
        }
    private val beacons = sensors.map { it.beacon }.toSet()

    fun countExcludedInRow(row: Int): Int =
        sensorRanges
            .asSequence()
            .filter { it.hasRowInRange(row) }
            .flatMap { (sensor, range) ->
                val rangeDistance = range -
                    (Coordinates(sensor.column, row).manhattanDistanceFrom(sensor))

                rangeCenteredAt(center = sensor.column, width = 1 + 2 * rangeDistance)
                    .asSequence()
                    .map { Coordinates(column = it, row = row) }
            }
            .distinct()
            .count { it !in beacons }

    private fun rangeCenteredAt(center: Int, width: Int): IntProgression {
        require(width.isOdd())
        return (center - width / 2)..(center + width / 2)
    }

    companion object {
        private operator fun Entry<Coordinates, Int>.contains(coordinates: Coordinates): Boolean =
            let { (sensor, range) ->
                coordinates.manhattanDistanceFrom(sensor) <= range
            }

        private fun Entry<Coordinates, Int>.hasRowInRange(row: Int): Boolean =
            let { (sensor, range) ->
                val min = sensor.row - range
                val max = sensor.row + range
                row in min..max
            }
    }

    internal data class SensorBeacon(val sensor: Coordinates, val beacon: Coordinates) {
        val range = sensor.manhattanDistanceFrom(beacon)
    }
}
