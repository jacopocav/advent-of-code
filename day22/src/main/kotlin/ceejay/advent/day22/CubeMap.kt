package ceejay.advent.day22

import ceejay.advent.day22.Cell.WALL
import ceejay.advent.util.Vector2D.Companion.vector

class CubeMap(
    cells: List<List<Cell>>,
    private val cubeFaces: Map<Int, CubeFace>,
) {
    private val walls = cells.flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, cell ->
            if (cell == WALL) vector(x, y) else null
        }
    }.toSet()

    fun run(
        actions: List<Action>,
        initialFaceId: Int,
        initialRelativePosition: DirectedVector,
    ): DirectedVector {
        var face = cubeFaces.getValue(initialFaceId)
        var position = DirectedVector(
            face.toAbsolute(initialRelativePosition.vector),
            initialRelativePosition.direction
        )

        for (action in actions) {
            position = when (action) {
                is TurnLeft -> position.copy(direction = position.direction.left)
                is TurnRight -> position.copy(direction = position.direction.right)
                is Move -> {
                    val (newFace, newPosition) = move(
                        steps = action.steps,
                        fromFace = face,
                        fromPosition = position
                    )
                    face = newFace
                    newPosition
                }
            }
        }

        return position
    }

    private fun move(
        steps: Int,
        fromFace: CubeFace,
        fromPosition: DirectedVector,
    ): Pair<CubeFace, DirectedVector> {
        var currentPosition = fromPosition
        var nextPosition = fromPosition

        var currentFace = fromFace
        var nextFace = fromFace

        for (step in 0..steps) {
            currentPosition = nextPosition
            currentFace = nextFace

            nextPosition = currentPosition.advance()

            if (nextPosition.vector !in currentFace) {
                val transition = currentFace.getTransition(currentPosition.direction)

                nextPosition = DirectedVector(
                    transition.move(currentPosition.vector),
                    transition.endDirection
                )
                nextFace = transition.endFace
            }

            if (nextPosition.vector in walls) break
        }

        return currentFace to currentPosition
    }

    private operator fun List<List<Cell>>.get(pos: DirectedVector) =
        this[pos.vector.y][pos.vector.x]
}