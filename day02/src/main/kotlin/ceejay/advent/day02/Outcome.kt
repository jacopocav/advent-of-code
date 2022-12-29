package ceejay.advent.day02

enum class Outcome(val code: String) {
    LOSE("X") {
        override fun getAnswerTo(opponentHand: Hand): Hand {
            return opponentHand.getWeakerHand()
        }
    },
    DRAW("Y") {
        override fun getAnswerTo(opponentHand: Hand): Hand {
            return opponentHand
        }
    },
    WIN("Z") {
        override fun getAnswerTo(opponentHand: Hand): Hand {
            return opponentHand.getStrongerHand()
        }
    };

    abstract fun getAnswerTo(opponentHand: Hand): Hand

    companion object {
        fun findByCode(code: String): Outcome {
            return Outcome.values()
                .find { it.code == code }
                ?: throw NoSuchElementException("no Outcome found with code = $code")
        }
    }
}