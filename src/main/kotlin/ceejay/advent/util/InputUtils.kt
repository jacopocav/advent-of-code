package ceejay.advent.util

import java.nio.file.Files
import java.nio.file.Path

object InputUtils {
    val basePath = Path.of("src", "main", "resources")
    fun read(fileName: String): String {
        return Files.readString(basePath.resolve(fileName))
    }
}