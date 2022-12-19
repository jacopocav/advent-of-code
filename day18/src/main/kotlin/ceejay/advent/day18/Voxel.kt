package ceejay.advent.day18

import kotlin.math.abs

data class Voxel(val x: Int, val y: Int, val z: Int) {
    infix fun isAdjacentTo(other: Voxel): Boolean =
        (x == other.x && y == other.y && abs(z - other.z) == 1)
            || (y == other.y && z == other.z && abs(x - other.x) == 1)
            || (x == other.x && z == other.z && abs(y - other.y) == 1)

    companion object {
        fun parse(text: String): Voxel = text.split(",")
            .apply { require(size == 3) }
            .map { it.toInt() }
            .let { (x, y, z) -> Voxel(x, y, z) }
    }
}
