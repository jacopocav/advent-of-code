package ceejay.advent.day17

import ceejay.advent.util.Debuggable
import ceejay.advent.util.Debuggable.Companion.debug

internal class ShapeDropper(
    width: Int,
    private val shapes: List<Shape>,
    override var debugEnabled: Boolean = false,
) : Debuggable {
    private val bitChamber = BitChamber(width)

    fun drop(shapeCount: Int, moves: List<Move>): BitChamber {
        var moveCounter = 0
        for (i in 0 until shapeCount) {
            val shape = shapes[i % shapes.size]
            bitChamber.makeSpaceFor(shape)

            var bitShape = BitShape(shape).padLeft(3)
            var bottomOffset = bitChamber.towerHeight() + 4

            debug { "--- Shape $i incoming ---" }
            debug { bitChamber.encode(bitShape, bottomOffset) }

            while (true) {
                // move laterally
                val move = moves[moveCounter++ % moves.size]
                val shapeMatrixAfterMove = bitShape.shift(move)

                if (!bitChamber.collidesWith(shapeMatrixAfterMove, offset = bottomOffset)) {
                    bitShape = shapeMatrixAfterMove
                }

                // move down
                bottomOffset--
                if (bitChamber.collidesWith(bitShape, offset = bottomOffset)) {
                    bottomOffset++
                    bitChamber.addRestingShape(bitShape, offset = bottomOffset)
                    break
                }
            }
        }
        debug { "--- Final situation ---" }
        debug { bitChamber.encode(BitShape.empty, 0) }
        return bitChamber
    }
}