package ceejay.advent._02

enum class Symbol(val leftName: String, val rightName: String, val score: Int) {
    ROCK("A", "X", 1) {
        override fun winsAgainst(): Symbol {
            return SCISSORS
        }

        override fun losesAgainst(): Symbol {
            return PAPER
        }
    },
    PAPER("B", "Y", 2) {
        override fun winsAgainst(): Symbol {
            return ROCK
        }

        override fun losesAgainst(): Symbol {
            return SCISSORS
        }
    },
    SCISSORS("C", "Z", 3) {
        override fun winsAgainst(): Symbol {
            return PAPER
        }

        override fun losesAgainst(): Symbol {
            return ROCK
        }
    };

    abstract fun winsAgainst(): Symbol
    abstract fun losesAgainst(): Symbol
}

fun parseLeft(value: String): Symbol {
    return Symbol.values()
        .find { it.leftName == value }
        ?: throw NoSuchElementException()
}

fun parseRight(value: String): Symbol {
    return Symbol.values()
        .find { it.rightName == value }
        ?: throw NoSuchElementException()
}

fun scoreOf(opponent: Symbol, mine: Symbol): Int {
    val diffScore = if (mine == opponent) {
        3
    } else if (mine.winsAgainst() == opponent) {
        6
    } else {
        0
    }

    return mine.score + diffScore
}