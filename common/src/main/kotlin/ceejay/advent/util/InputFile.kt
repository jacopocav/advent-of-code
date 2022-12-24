package ceejay.advent.util

import java.io.FileNotFoundException

object InputFile {
    const val defaultName = "input.txt"
    val newLine: String = System.lineSeparator()

    operator fun invoke(name: String = defaultName): String {
        return ClassLoader.getSystemResourceAsStream(name)
            ?.buffered()
            ?.reader()
            ?.readText()
            ?: throw FileNotFoundException(name)
    }

    inline fun <T> useLines(
        name: String = defaultName,
        ignoreBlankLines: Boolean = true,
        block: (Sequence<String>) -> T,
    ) = withLines(name, ignoreBlankLines, block)


    inline fun <T> withLines(
        name: String = defaultName,
        ignoreBlankLines: Boolean = true,
        block: Sequence<String>.() -> T,
    ): T {
        val reader = ClassLoader.getSystemResourceAsStream(name)
            ?.buffered()
            ?.reader()
            ?: throw FileNotFoundException(name)

        return reader.useLines { lines ->
            lines.run {
                if (ignoreBlankLines) filter { it.isNotBlank() }
                else this
            }.block()
        }
    }
}