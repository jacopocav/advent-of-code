package ceejay.advent.day17

import ceejay.advent.util.Debuggable
import ceejay.advent.util.Debuggable.Companion.debug
import java.util.Objects.hash

class ShapeDropper(
    private val width: Int,
    private val shapes: List<Shape>,
    private val jetPattern: List<Move>,
    override var debugEnabled: Boolean = false,
) : Debuggable {

    fun drop(shapeCount: Long): BitChamber {
        val bitChamber = BitChamber(width)
        val history = ArrayDeque<Drop>()

        var jetIndex = 0
        for (shapeIndex in 0 until shapeCount) {
            val shape = shapes[(shapeIndex % shapes.size).toInt()]
            bitChamber.makeSpaceFor(shape)

            var bitShape = BitShape(shape).padLeft(3)
            var bottomOffset = bitChamber.towerHeight() + 4

            debug { "--- Shape $shapeIndex incoming ---" }
            debug { bitChamber.encode(bitShape, bottomOffset) }

            val drop = Drop(
                shape = shape,
                shapeCount = shapeIndex,
                jetIndex = jetIndex,
                chamberProfile = bitChamber.profile(),
                towerHeight = bitChamber.towerHeight()
            )

            if (drop in history) {
                // found a cycle
                completeWithCycle(
                    history = history,
                    end = drop,
                    shapeIndex = shapeIndex,
                    shapeCount = shapeCount,
                    bitChamber = bitChamber
                )
                break
            }

            history += drop

            while (true) {
                // move laterally
                val move = jetPattern[jetIndex++ % jetPattern.size]
                jetIndex %= jetPattern.size

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

    private fun completeWithCycle(
        history: MutableList<Drop>,
        end: Drop,
        shapeIndex: Long,
        shapeCount: Long,
        bitChamber: BitChamber,
    ) {
        val cycleIndex = history.indexOf(end)
        val start = history[cycleIndex]
        val cycleShapes = end.shapeCount - start.shapeCount
        val cycleHeight = end.towerHeight - start.towerHeight

        debug { "--- Cycle (# shapes = $cycleShapes, height = $cycleHeight) --- " }

        // number of fully applied cycles
        val fullCycles = (shapeCount - shapeIndex) / cycleShapes

        debug { "Fully repeated cycle $fullCycles times" }

        // final cycle is applied partially, up to shapeCount shapes
        val finalCycleShapes = shapeCount - shapeIndex - fullCycles * cycleShapes
        val finalDrop = history[cycleIndex + finalCycleShapes.toInt()]
        val finalCycleHeight = finalDrop.towerHeight - start.towerHeight

        if (finalCycleShapes > 0) {
            debug { "Partially applied last cycle up to $finalCycleShapes shapes" }
        }

        bitChamber.addHeightOffset(fullCycles * cycleHeight + finalCycleHeight)
    }

    private data class Drop(
        val shape: Shape,
        val shapeCount: Long,
        val jetIndex: Int,
        val chamberProfile: List<Int>,
        val towerHeight: Long,
    ) {
        override fun equals(other: Any?): Boolean = other is Drop
            && shape == other.shape
            && jetIndex == other.jetIndex
            && chamberProfile == other.chamberProfile

        override fun hashCode(): Int = hash(shape, jetIndex, chamberProfile)
    }
}