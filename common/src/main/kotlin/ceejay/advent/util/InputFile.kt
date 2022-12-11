package ceejay.advent.util

import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader

object InputFile {
    operator fun invoke(name: String = "input.txt"): String {
        return ClassLoader.getSystemResourceAsStream(name)
            ?.let { stream -> BufferedReader(InputStreamReader(stream, Charsets.UTF_8)) }
            ?.use { reader -> reader.readText() }
            ?: throw FileNotFoundException(name)
    }

    fun <T> withLines(name: String = "input.txt", block: Sequence<String>.() -> T): T {
        return ClassLoader.getSystemResourceAsStream(name)
            ?.let { stream -> BufferedReader(InputStreamReader(stream)) }
            ?.use { reader -> block(reader.lineSequence().filter { it.isNotBlank() }) }
            ?: throw FileNotFoundException(name)
    }
}