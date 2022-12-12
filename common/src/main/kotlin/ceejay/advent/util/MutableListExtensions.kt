package ceejay.advent.util

fun <T> MutableList<T>.removeWhile(predicate: (T) -> Boolean): List<T> = sequence {
    while (isNotEmpty() && predicate(first())) {
        yield(removeFirst())
    }
}.toList()