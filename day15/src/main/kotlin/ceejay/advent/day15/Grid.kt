package ceejay.advent.day15

import ceejay.advent.util.isOdd
import kotlin.collections.Map.Entry
import kotlin.math.abs


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

    fun findFirstEmptyCellInRectangle(minCoordinates: Coordinates, maxCoordinates: Coordinates): Coordinates {
        var column = minCoordinates.column
        var row = minCoordinates.row

        while (column <= maxCoordinates.column && row <= maxCoordinates.row) {
            val coordinates = Coordinates(column = column, row = row)
            val (sensor, range) = sensorRanges
                .entries
                .firstOrNull { coordinates in it }
                ?: return coordinates

            val rangeDifference =
                range - abs(coordinates.row - sensor.row)
            val sensorZoneWidth = 1 + 2 * rangeDifference
            val nextCandidateColumn = sensor.column + (sensorZoneWidth / 2) + 1

            column = if (nextCandidateColumn > maxCoordinates.column) {
                row++
                minCoordinates.column
            } else {
                nextCandidateColumn
            }
        }
        throw IllegalArgumentException("no empty cell in range $minCoordinates - $maxCoordinates")
    }

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
