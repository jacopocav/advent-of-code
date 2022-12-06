package ceejay.advent._02

enum class Outcome(val code: String) {
    LOSE("X") {
        override fun answerTo(opponent: Symbol): Symbol {
            return opponent.winsAgainst()
        }
    },
    DRAW("Y") {
        override fun answerTo(opponent: Symbol): Symbol {
            return opponent
        }
    },
    WIN("Z") {
        override fun answerTo(opponent: Symbol): Symbol {
            return opponent.losesAgainst()
        }
    };

    abstract fun answerTo(opponent: Symbol): Symbol
}

fun parseOutcome(code: String): Outcome {
    return Outcome.values()
        .find { it.code == code }
        ?: throw NoSuchElementException()
}