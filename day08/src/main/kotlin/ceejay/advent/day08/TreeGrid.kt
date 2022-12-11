package ceejay.advent.day08

internal class TreeGrid(private val grid: List<List<Int>>) {
    private val xMax = (grid.getOrNull(0)?.size ?: 0) - 1
    private val yMax = grid.size - 1
    private val maxCoordinates = Coordinates(xMax, yMax)
    operator fun get(coordinates: Coordinates): Int {
        return grid[coordinates.y][coordinates.x]
    }

    fun allCoordinates(): Sequence<Coordinates> {
        return (0..maxCoordinates.y)
            .asSequence()
            .flatMap { y ->
                (0..maxCoordinates.x).map { Coordinates(it, y) }
            }
    }

    fun scenicScoreOf(coordinates: Coordinates): Int {
        val height = this[coordinates]

        return scoreRange(height, rangeLeft(coordinates)) *
            scoreRange(height, rangeRight(coordinates)) *
            scoreRange(height, rangeUp(coordinates)) *
            scoreRange(height, rangeDown(coordinates))
    }

    private fun scoreRange(height: Int, range: List<Coordinates>): Int {
        var count = 0
        for (coordinates in range) {
            val otherHeight = this[coordinates]
            count++
            if (otherHeight >= height) {
                break
            }
        }
        return count
    }

    fun isVisible(coordinates: Coordinates): Boolean {
        if (isInBorder(coordinates)) {
            return true
        }

        val height = this[coordinates]

        return isVisibleFromRange(height, rangeLeft(coordinates))
            || isVisibleFromRange(height, rangeRight(coordinates))
            || isVisibleFromRange(height, rangeUp(coordinates))
            || isVisibleFromRange(height, rangeDown(coordinates))
    }

    private fun rangeLeft(coordinates: Coordinates) =
        (coordinates.x - 1 downTo 0).map { Coordinates(it, coordinates.y) }

    private fun rangeRight(coordinates: Coordinates) =
        (coordinates.x + 1..xMax).map { Coordinates(it, coordinates.y) }

    private fun rangeUp(coordinates: Coordinates) =
        (coordinates.y - 1 downTo 0).map { Coordinates(coordinates.x, it) }

    private fun rangeDown(coordinates: Coordinates) =
        (coordinates.y + 1..yMax).map { Coordinates(coordinates.x, it) }

    private fun isVisibleFromRange(height: Int, segment: List<Coordinates>): Boolean {
        return segment.all { this[it] < height }
    }

    private fun isInBorder(coordinates: Coordinates): Boolean =
        coordinates.x == 0 || coordinates.y == 0
            || coordinates.x == maxCoordinates.x || coordinates.y == maxCoordinates.y

    companion object {
        fun parse(text: String): TreeGrid {
            val grid = text.lines()
                .filter { it.isNotBlank() }
                .map { line -> line.windowed(1).map { it.toInt() } }

            return TreeGrid(grid)
        }

        data class Coordinates(val x: Int, val y: Int)
    }
}
