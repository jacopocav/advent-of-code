package ceejay.advent.day12

private val chars = ('a'..'z').toSet() + 'S' + 'E'
internal fun Sequence<String>.parse(): Graph {
    val grid = mapIndexed { i, row -> row.parseRow(i) }
        .toList()

    grid.addEdges()

    return grid.asSequence()
        .flatten()
        .map { it.node }
        .let { nodes ->
            require(nodes.any { it.isStart }) { "No start node found" }
            require(nodes.any { it.isEnd }) { "No end node found" }

            Graph(nodes.toList(), nodes.find { it.isStart }!!, nodes.find { it.isEnd }!!)
        }
}

private fun String.parseRow(rowIndex: Int): List<Cell> = mapIndexed { colIndex, char ->
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

private val Char.isValid: Boolean
    get() = this in chars
private val Char.isStart: Boolean
    get() = this == 'S'
private val Char.isEnd: Boolean
    get() = this == 'E'
private val Char.height: Int
    get() = when {
        isStart -> 'a'.height
        isEnd -> 'z'.height
        else -> code - 'a'.code
    }