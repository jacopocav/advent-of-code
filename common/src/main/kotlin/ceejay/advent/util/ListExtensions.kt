package ceejay.advent.util

inline fun <T> MutableList<T>.removeWhile(crossinline predicate: (T) -> Boolean): List<T> =
    sequence {
        while (isNotEmpty() && predicate(first())) {
            yield(removeFirst())
        }
    }.toList()

fun List<Char>.composeString(): String = joinToString("")

fun <T> Sequence<T>.indicesOfAll(predicate: (T) -> Boolean): Sequence<Int> = withIndex()
    .filter { (_, elem) -> predicate(elem) }
    .map { it.index }

fun Sequence<Int>.product(): Int = fold(1) { a, b -> a * b }