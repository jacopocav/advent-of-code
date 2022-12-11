package ceejay.advent.day10

import ceejay.advent.util.isOdd
import kotlin.math.abs

class Display(
    private val width: Int,
    height: Int,
    private val spriteRegister: String,
    private val spriteWidth: Int
) : Component {
    private val pixels: BooleanArray
    private var currentPixel: Int

    init {
        require(width > 0) { "width cannot be negative or zero" }
        require(height > 0) { "height cannot be negative or zero" }
        require(spriteWidth > 0) { "spriteWidth cannot be negative or zero" }
        require(spriteWidth.isOdd()) { "spriteWidth cannot be an even number" }

        pixels = BooleanArray(width * height) { false }
        currentPixel = 0
    }

    override fun onClockTick(registers: Map<String, Int>) {
        val registerValue = registers[spriteRegister]
            ?: throw IllegalStateException("register $spriteRegister not found")

        val distance = abs((currentPixel % width) - registerValue)

        if (distance <= spriteWidth / 2) {
            pixels[currentPixel] = true
        }
        currentPixel++
    }

    override fun toString(): String = pixels
        .joinToString(separator = "") { pixelToString(it) }
        .windowed(size = width, step = width)
        .joinToString(separator = "\n")

    companion object {
        private fun pixelToString(active: Boolean): String = if (active) "#" else "."
    }
}