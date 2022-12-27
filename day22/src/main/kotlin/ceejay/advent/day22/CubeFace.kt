package ceejay.advent.day22

import ceejay.advent.util.Vector2D
import ceejay.advent.util.Vector2D.Companion.vector

class CubeFace(
    val id: Int,
    val edge: Int,
    val topLeft: Vector2D,
    val up: FaceTransition,
    val right: FaceTransition,
    val down: FaceTransition,
    val left: FaceTransition,
) {
    private val xMin = topLeft.x
    private val xMax = xMin + edge - 1
    private val yMin = topLeft.y
    private val yMax = yMin + edge - 1

    operator fun contains(vector: Vector2D): Boolean =
        vector.x in xMin..xMax && vector.y in yMin..yMax

    fun getTransition(direction: Direction): FaceTransition = when (direction) {
        Up -> up
        Right -> right
        Down -> down
        Left -> left
    }

    fun toRelative(absoluteVector: Vector2D): Vector2D = absoluteVector - topLeft
    fun toAbsolute(relativeVector: Vector2D): Vector2D = relativeVector + topLeft

    class FaceTransition(
        startId: Int,
        endId: Int,
        startDirection: Direction,
        val endDirection: Direction,
        allFaces: Map<Int, CubeFace>,
    ) {
        private val directionChange = startDirection to endDirection
        private val startFace: CubeFace by lazy { allFaces.getValue(startId) }
        val endFace: CubeFace by lazy { allFaces.getValue(endId) }

        private fun Vector2D.rescale() = endFace.toAbsolute(startFace.toRelative(this))

        private fun Vector2D.rescaleFlipped() =
            endFace.toAbsolute(startFace.toRelative(this).flip())

        fun move(from: Vector2D): Vector2D = when (directionChange) {
            Up to Up -> vector(from.rescale().x, endFace.yMax)
            Right to Right -> vector(endFace.xMin, from.rescale().y)
            Left to Left -> vector(endFace.xMax, from.rescale().y)
            Down to Down -> vector(from.rescale().x, endFace.yMin)
            Up to Right -> vector(endFace.xMin, from.rescaleFlipped().y)
            Right to Up -> vector(from.rescaleFlipped().x, endFace.yMax)
            Right to Left -> vector(endFace.xMax, endFace.yMax - startFace.toRelative(from).y)
            Left to Right -> vector(endFace.xMin, endFace.yMax - startFace.toRelative(from).y)
            Left to Down -> vector(from.rescaleFlipped().x, endFace.yMin)
            Down to Left -> vector(endFace.xMax, from.rescaleFlipped().y)
            else -> error("No transition from ${directionChange.first} to ${directionChange.second}")
        }
    }

    companion object {
        fun fakeFacesOfSize(edge: Int): Map<Int, CubeFace> = buildMap {
            fun transition(startId: Int, endId: Int, direction: Direction) =
                FaceTransition(startId, endId, direction, direction, this)

            listOf(
                CubeFace(
                    id = 1,
                    edge = edge,
                    topLeft = Vector2D(edge, 0),
                    up = transition(1, 5, Up),
                    right = transition(1, 2, Right),
                    down = transition(1, 3, Down),
                    left = transition(1, 2, Left),
                ),
                CubeFace(
                    id = 2,
                    edge = edge,
                    topLeft = Vector2D(2 * edge, 0),
                    up = transition(2, 2, Up),
                    right = transition(2, 1, Right),
                    down = transition(2, 2, Down),
                    left = transition(2, 1, Left),
                ),
                CubeFace(
                    id = 3,
                    edge = edge,
                    topLeft = Vector2D(edge, edge),
                    up = transition(3, 1, Up),
                    right = transition(3, 3, Right),
                    down = transition(3, 5, Down),
                    left = transition(3, 3, Left),
                ),
                CubeFace(
                    id = 4,
                    edge = edge,
                    topLeft = Vector2D(0, 2 * edge),
                    up = transition(4, 6, Up),
                    right = transition(4, 5, Right),
                    down = transition(4, 6, Down),
                    left = transition(4, 5, Left),
                ),
                CubeFace(
                    id = 5,
                    edge = edge,
                    topLeft = Vector2D(edge, 2 * edge),
                    up = transition(5, 3, Up),
                    right = transition(5, 4, Right),
                    down = transition(5, 1, Down),
                    left = transition(5, 4, Left),
                ),
                CubeFace(
                    id = 6,
                    edge = edge,
                    topLeft = Vector2D(0, 3 * edge),
                    up = transition(6, 4, Up),
                    right = transition(6, 6, Right),
                    down = transition(6, 4, Down),
                    left = transition(6, 6, Left),
                ),
            ).forEach { this[it.id] = it }
        }

        fun realFacesOfSize(edge: Int): Map<Int, CubeFace> = buildMap {
            fun transition(
                startId: Int,
                endId: Int,
                startDirection: Direction,
                endDirection: Direction,
            ) = FaceTransition(startId, endId, startDirection, endDirection, this)

            listOf(
                CubeFace(
                    id = 1,
                    edge = edge,
                    topLeft = Vector2D(edge, 0),
                    up = transition(1, 6, Up, Right),
                    right = transition(1, 2, Right, Right),
                    down = transition(1, 3, Down, Down),
                    left = transition(1, 4, Left, Right),
                ),
                CubeFace(
                    id = 2,
                    edge = edge,
                    topLeft = Vector2D(2 * edge, 0),
                    up = transition(2, 6, Up, Up),
                    right = transition(2, 5, Right, Left),
                    down = transition(2, 3, Down, Left),
                    left = transition(2, 1, Left, Left),
                ),
                CubeFace(
                    id = 3,
                    edge = edge,
                    topLeft = Vector2D(edge, edge),
                    up = transition(3, 1, Up, Up),
                    right = transition(3, 2, Right, Up),
                    down = transition(3, 5, Down, Down),
                    left = transition(3, 4, Left, Down),
                ),
                CubeFace(
                    id = 4,
                    edge = edge,
                    topLeft = Vector2D(0, 2 * edge),
                    up = transition(4, 3, Up, Right),
                    right = transition(4, 5, Right, Right),
                    down = transition(4, 6, Down, Down),
                    left = transition(4, 1, Left, Right),
                ),
                CubeFace(
                    id = 5,
                    edge = edge,
                    topLeft = Vector2D(edge, 2 * edge),
                    up = transition(5, 3, Up, Up),
                    right = transition(5, 2, Right, Left),
                    down = transition(5, 6, Down, Left),
                    left = transition(5, 4, Left, Left),
                ),
                CubeFace(
                    id = 6,
                    edge = edge,
                    topLeft = Vector2D(0, 3 * edge),
                    up = transition(6, 4, Up, Up),
                    right = transition(6, 5, Right, Up),
                    down = transition(6, 2, Down, Down),
                    left = transition(6, 1, Left, Down),
                ),
            ).forEach { this[it.id] = it }
        }
    }
}
