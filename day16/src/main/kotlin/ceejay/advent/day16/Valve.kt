package ceejay.advent.day16

data class Valve(val name: String, val flow: Int, val connections: Set<Connection>) {
    override fun toString(): String =
        "$name(flow = $flow, connections = [${connections.joinToString()}])"
}

data class Connection(val toValve: String, val length: Int) {
    override fun toString(): String = "$toValve($length)"
}