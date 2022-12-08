package ceejay.advent.day05

fun main() {
    println(Part1())
}

object Part1 {
    operator fun invoke(): String {
        return Common.run(::Crate9000)
    }
}