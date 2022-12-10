package ceejay.advent.util

import java.nio.file.Files
import java.nio.file.Path

object InputFile {
    private val basePath: Path = Path.of("src", "main", "resources")
    operator fun invoke(name: String): String {
        return Files.readString(basePath.resolve(name))
    }

    fun <T> withLines(name: String, block: (Sequence<String>) -> T): T {
        return basePath.resolve(name).toFile().useLines(block = block)
    }
}