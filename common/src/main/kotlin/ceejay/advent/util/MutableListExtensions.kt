package ceejay.advent.util

inline fun <T> MutableList<T>.removeWhile(crossinline predicate: (T) -> Boolean): List<T> =
    sequence {
        while (isNotEmpty() && predicate(first())) {
            yield(removeFirst())
        }
    }.toList()