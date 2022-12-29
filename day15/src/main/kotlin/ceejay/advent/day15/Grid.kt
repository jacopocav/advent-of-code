package ceejay.advent.day15

import ceejay.advent.util.Vector2D
import kotlin.collections.Map.Entry
import kotlin.math.abs
import kotlin.math.max


class Grid(sensors: Collection<SensorBeacon>) {

    private val sensorRanges: Map<Vector2D, Int> = sensors.groupingBy { it.sensor }
        .aggregate { _, _, sensorBeacon, first ->
            if (first) sensorBeacon.range
            else error("Duplicate sensor found: ${sensorBeacon.sensor}")
        }
    private val beacons = sensors.map { it.beacon }.toSet()

    fun countExcludedInRow(y: Int): Int {
        fun Entry<Vector2D, Int>.rangeAtY(): Int = let { (sensor, range) ->
            max(0, range - abs(sensor.y - y) + 1)
        }

        fun Entry<Vector2D, Int>.hasInRange(x: Int) = let { (sensor, range) ->
            x in (sensor.x - range + 1) until sensor.x + range
        }

        val intersectingSensors = sensorRanges
            .mapValues { it.rangeAtY() }
            .filterValues { it > 0 }

        assert(sensorRanges.filter { it.hasRowInRange(y) }.keys == intersectingSensors.keys)

        val xMin = intersectingSensors.minOf { (sensor, rangeAtY) -> sensor.x - rangeAtY + 1 }
        val xMax = intersectingSensors.maxOf { (sensor, rangeAtY) -> sensor.x + rangeAtY - 1 }

        var count = 0
        var x = xMin
        while (x <= xMax) {
            val mostExclusive = intersectingSensors
                .filter { it.hasInRange(x) }
                .maxByOrNull { (sensor, range) -> sensor.x + range }

            mostExclusive?.let { (sensor, range) ->
                val prev = x
                x = sensor.x + range
                count += (x - prev)
            }

            if (mostExclusive == null) {
                x++
            }
        }

        return count - beacons.count { it.y == y }
    }

    fun findFirstEmptyCellInRectangle(
        minCoordinates: Vector2D,
        maxCoordinates: Vector2D,
    ): Vector2D {
        var column = minCoordinates.y
        var row = minCoordinates.x

        while (column <= maxCoordinates.x && row <= maxCoordinates.y) {
            val coordinates = Vector2D(x = column, y = row)
            val (sensor, range) = sensorRanges
                .entries
                .firstOrNull { coordinates in it }
                ?: return coordinates

            val rangeDifference =
                range - abs(coordinates.y - sensor.y)
            val sensorZoneWidth = 1 + 2 * rangeDifference
            val nextCandidateColumn = sensor.x + (sensorZoneWidth / 2) + 1

            column = if (nextCandidateColumn > maxCoordinates.x) {
                row++
                minCoordinates.x
            } else {
                nextCandidateColumn
            }
        }
        throw IllegalArgumentException("no empty cell in range $minCoordinates - $maxCoordinates")
    }

    companion object {
        private operator fun Entry<Vector2D, Int>.contains(coordinates: Vector2D): Boolean =
            let { (sensor, range) ->
                coordinates.manhattan(sensor) <= range
            }

        private fun Entry<Vector2D, Int>.hasRowInRange(row: Int): Boolean {
            val (sensor, range) = this
            val min = sensor.y - range
            val max = sensor.y + range
            return row in min..max
        }
    }

    data class SensorBeacon(val sensor: Vector2D, val beacon: Vector2D) {
        val range = sensor.manhattan(beacon)
    }
}
