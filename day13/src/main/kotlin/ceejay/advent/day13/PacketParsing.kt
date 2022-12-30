package ceejay.advent.day13

import ceejay.advent.util.composeString
import ceejay.advent.util.removeWhile
import ceejay.advent.util.toMutableDeque

object PacketParser {
    fun parse(sequence: Sequence<String>): Sequence<Packet> =
        sequence.map { it.parsePacket() }

    private fun String.parsePacket(): Packet {
        val tokens = tokenize().toMutableDeque()

        require(tokens.removeFirst() is Token.Open) { "Top-level packet is not a list" }
        return tokens.parseListPacket()
    }

    private fun MutableList<Token>.parseListPacket(): ListPacket {
        val content = mutableListOf<Packet>()
        while (isNotEmpty()) {
            when (val token = removeFirst()) {
                is Token.Number -> content += NumberPacket(token.value)
                is Token.Open -> content += parseListPacket()
                is Token.Close -> break
            }
        }
        return ListPacket(content)
    }

    private fun String.tokenize(): List<Token> {
        val tokens = mutableListOf<Token>()
        val chars = filterNot { it.isWhitespace() }.toMutableDeque()

        while (chars.isNotEmpty()) {
            val char = chars.removeFirst()
            if (char == ',') {
                continue
            }

            tokens += when (char) {
                '[' -> Token.Open
                ']' -> Token.Close
                else -> Token.Number(
                    (char + chars.removeWhile { it.isDigit() }.composeString()).toInt()
                )
            }
        }
        return tokens
    }

    sealed interface Token {
        object Open : Token
        object Close : Token
        data class Number(val value: Int) : Token
    }
}