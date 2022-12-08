package ceejay.advent.day06

fun main() {
    println(Part1())
}

object Part1 {
    operator fun invoke(): Int {
        return findFirstSubstringOfDistinctCharsOfLength(4)
    }
}