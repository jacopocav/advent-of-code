package ceejay.advent.day05

fun main() {
    println(Part2())
}

object Part2 {
    operator fun invoke(): String {
        return Common.run(::Crate9001)
    }
}