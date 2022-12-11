package ceejay.advent.day02

internal enum class Hand(val opponentCode: String, val myCode: String, private val score: Int) {
    ROCK("A", "X", 1) {
        override fun getWeakerHand(): Hand {
            return SCISSORS
        }

        override fun getStrongerHand(): Hand {
            return PAPER
        }
    },
    PAPER("B", "Y", 2) {
        override fun getWeakerHand(): Hand {
            return ROCK
        }

        override fun getStrongerHand(): Hand {
            return SCISSORS
        }
    },
    SCISSORS("C", "Z", 3) {
        override fun getWeakerHand(): Hand {
            return PAPER
        }

        override fun getStrongerHand(): Hand {
            return ROCK
        }
    };

    abstract fun getWeakerHand(): Hand
    abstract fun getStrongerHand(): Hand

    fun scoreAgainst(opponent: Hand): Int {
        return when (opponent) {
            // draw
            this -> score + 3
            // win
            getWeakerHand() -> score + 6
            // loss
            getStrongerHand() -> score
            else -> throw AssertionError("cannot happen")
        }
    }

    companion object {
        private val all = values().toSet()
        fun findByOpponentCode(value: String): Hand {
            return all.find { it.opponentCode == value }
                ?: throw NoSuchElementException("no Hand found with opponentCode = $value")
        }

        fun findByMyCode(value: String): Hand {
            return all.find { it.myCode == value }
                ?: throw NoSuchElementException("no Hand found with myCode = $value")
        }
    }
}