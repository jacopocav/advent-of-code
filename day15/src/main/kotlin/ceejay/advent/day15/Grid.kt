package ceejay.advent.day15

import ceejay.advent.day15.Grid.Line.Slope.ASCENDING
import ceejay.advent.day15.Grid.Line.Slope.DESCENDING
import ceejay.advent.util.Vector2D
import ceejay.advent.util.Vector2D.Companion.vector
import kotlin.collections.Map.Entry
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign


class Grid(sensors: Collection<SensorBeacon>) {

    private val sensorRanges: Map<Vector2D, Int> = sensors.groupingBy { it.sensor }
        .aggregate { _, _, sensorBeacon, first ->
            if (first) sensorBeacon.range
            else error("Duplicate sensor found: ${sensorBeacon.sensor}")
        }
    private val beacons = sensors.map { it.beacon }.toSet()

    fun countExcludedInRow(y: Int): Int {
        val intersectingSensors = sensorRanges
            .mapValues { it.rangeAt(y) }
            .filterValues { it > 0 }

        assert(sensorRanges.filter { it.hasRowInRange(y) }.keys == intersectingSensors.keys)

        val xMin = intersectingSensors.minOfOrNull { (sensor, rangeAtY) -> sensor.x - rangeAtY + 1 }
            ?: return 0
        val xMax = intersectingSensors.maxOf { (sensor, rangeAtY) -> sensor.x + rangeAtY - 1 }

        var count = 0
        var x = xMin
        while (x <= xMax) {
            val mostExclusive = intersectingSensors
                .filter { it.hasInRange(x) }
                .maxByOrNull { (sensor, range) -> sensor.x + range }

            mostExclusive?.let { (sensor, range) ->
                val prev = x
                x = min(xMax + 1, sensor.x + range)
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
        // every sensor has 4 lines that delimit its area
        // the empty cell must be at the intersection of these lines
        val lines = sensorRanges.flatMapTo(mutableSetOf()) { (sensor, range) ->
            listOf(
                Line(ASCENDING, (sensor.y + range + 1) - sensor.x),
                Line(ASCENDING, (sensor.y - range - 1) - sensor.x),
                Line(DESCENDING, (sensor.y + range + 1) + sensor.x),
                Line(DESCENDING, (sensor.y - range - 1) + sensor.x),
            )
        }

        val (ascending, descending) = lines.partition { it.slope == ASCENDING }

        val intersections = ascending.flatMapTo(mutableSetOf()) { asc ->
            descending.mapNotNull { desc -> asc.intersectionWith(desc) }
        }.filter { it.inRectangle(min = minCoordinates, max = maxCoordinates) }

        return intersections.find { int -> sensorRanges.none { int in it } }
            ?: error("no free ")
    }

    companion object {

        private fun Vector2D.inRectangle(min: Vector2D, max: Vector2D): Boolean =
            x in min.x..max.x && y in min.y..max.y

        private fun Entry<Vector2D, Int>.rangeAt(y: Int): Int = let { (sensor, range) ->
            max(0, range - abs(sensor.y - y) + 1)
        }

        private fun Entry<Vector2D, Int>.hasInRange(x: Int) = let { (sensor, range) ->
            x in (sensor.x - range + 1) until sensor.x + range
        }

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

    /**
     * 2d line in the form y = ax + b, where a can be only +1 or -1
     */
    data class Line(val slope: Slope, val offset: Int) {
        operator fun get(x: Int): Int = slope.coefficient * x + offset
        fun intersectionWith(other: Line): Vector2D? {
            if (slope == other.slope) {
                // lines are parallel -> no intersection
                return null
            }

            val crossX = (other.offset - offset) / (slope.coefficient - other.slope.coefficient)

            if (this[crossX] != other[crossX]) {
                // intersection is decimal (exactly between two cells) -> no (integer) intersection
                return null
            }

            val crossY = this[crossX]
            return vector(crossX, crossY)
        }

        override fun toString(): String {
            val bSign = offset.sign.let {
                if (it >= 0) '+' else '-'
            }
            return "y = ${slope}x $bSign ${abs(offset)}"
        }

        enum class Slope(val coefficient: Int) {
            ASCENDING(1), DESCENDING(-1);

            override fun toString(): String = when (this) {
                ASCENDING -> "+"
                DESCENDING -> "-"
            }
        }
    }
}
