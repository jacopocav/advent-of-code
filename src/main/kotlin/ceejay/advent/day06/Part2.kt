package ceejay.advent.day06

fun main() {
    println(Part2())
}

object Part2 {
    operator fun invoke(): Int {
        return findFirstSubstringOfDistinctCharsOfLength(14)
    }
}