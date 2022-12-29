package ceejay.advent.day12


class GraphParser(
    val validChars: Set<Char> = defaultValidChars,
    val heightMapper: (Char) -> Int = ::defaultHeight,
    val startChar: Char,
    val endChars: String
) {
    init {
        require(startChar in validChars) { "start character $startChar is not in the set of valid characters" }
        endChars.forEach { char ->
            require(char in validChars)
            { "start character $char is not in the set of valid characters" }
        }
    }

    fun parse(lines: Sequence<String>): List<Node> {
        val grid = lines.mapIndexed { i, row -> row.parseRow(i) }
            .toList()

        grid.addEdges()

        return grid.asSequence()
            .flatten()
            .map { it.node }
            .toList()
    }

    private val Char.isValid: Boolean
        get() = this in validChars

    private val Char.isStart: Boolean
        get() = this == startChar
    private val Char.isEnd: Boolean
        get() = this in endChars
    private val Char.height: Int
        get() = heightMapper(this)

    private fun String.parseRow(rowIndex: Int): List<Cell> =
        mapIndexed { colIndex, char ->
            require(char.isValid) { "Unexpected character: $char" }
            Cell(
                char.height,
                Node(
                    id = Pair(rowIndex, colIndex),
                    isStart = char.isStart,
                    isEnd = char.isEnd
                )
            )
        }


    companion object {
        val defaultValidChars = ('a'..'z').toSet() + 'S' + 'E'
        fun defaultHeight(char: Char): Int = when (char) {
            'S' -> defaultHeight('a')
            'E' -> defaultHeight('z')
            else -> char.code - 'a'.code
        }

        private fun List<List<Cell>>.addEdges() {
            require(isNotEmpty()) { "grid is empty" }
            require(all { it.size == first().size }) { "rows have different sizes" }

            indices.forEach { row ->
                first().indices.forEach { col ->
                    val cell = this[row][col]

                    getNeighbors(row, col)
                        .filter { neighbor -> cell.height + 1 >= neighbor.height }
                        .forEach { neighbor ->
                            cell.node.edges.add(neighbor.node)
                        }
                }
            }
        }

        private fun List<List<Cell>>.getNeighbors(row: Int, col: Int) = sequence {
            val grid = this@getNeighbors
            if (row > 0) yield(grid[row - 1][col])
            if (col > 0) yield(grid[row][col - 1])
            if (row < size - 1) yield(grid[row + 1][col])
            if (col < first().size - 1) yield(grid[row][col + 1])
        }

        private data class Cell(val height: Int, val node: Node)

    }
}


