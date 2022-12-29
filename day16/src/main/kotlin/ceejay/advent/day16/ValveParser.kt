package ceejay.advent.day16

fun Sequence<String>.parse(): Set<Valve> = map { it.parseValve() }.toSet()

private val valveRegex =
    "^Valve (?<name>[A-Z]+) has flow rate=(?<flowRate>\\d+); tunnels? leads? to valves? (?<connections>[A-Z]+(, [A-Z]+)*)$"
        .toRegex()

private fun String.parseValve(): Valve {
    val match = valveRegex.matchEntire(this)

    require(match != null) { "line '$this' does not match regex '$valveRegex'" }

    val name = match.groups["name"]!!.value
    val flowRate = match.groups["flowRate"]!!.value.toInt()
    val connections = match.groups["connections"]!!.value.split(", ")
        .map { Connection(toValve = it, length = 1) }
        .toSet()

    return Valve(name, flowRate, connections)
}