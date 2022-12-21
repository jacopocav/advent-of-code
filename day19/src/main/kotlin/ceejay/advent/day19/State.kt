package ceejay.advent.day19

import ceejay.advent.day19.Material.*

data class State(
    val resources: Counter,
    val robots: Counter,
    val minutesRemaining: Int,
    val prev: State? = null,
) {
    val geodes: Int get() = resources[GEODE] + robots[GEODE] * minutesRemaining
    fun buildRobot(material: Material, costs: Counter): State = copy(
        minutesRemaining = minutesRemaining - 1,
        robots = robots + Counter.ofMaterial(material, 1),
        resources = robots + resources - costs,
        prev = this
    )

    fun wait(minutes: Int = 1): State = when {
        minutes == 0 -> this
        minutes > 0 -> copy(
            minutesRemaining = minutesRemaining - minutes,
            resources = robots * minutes + resources,
            prev = this
        )

        else -> error("cannot wait negative minutes ($minutes)")
    }

    fun canPay(costs: Counter): Boolean = resources gte costs

    fun canPayInTheFuture(costs: Counter): Boolean =
        (resources + robots * minutesRemaining) gte costs


    private fun logSingle() = "$minutesRemaining -> resources=$resources robots=$robots"

    fun log(): String = generateSequence(this) { it.prev }
        .toList()
        .asReversed()
        .joinToString(separator = "\n") { it.logSingle() }.let {
            if (minutesRemaining > 0) {
                it + "\n" + wait(minutesRemaining).logSingle()
            } else it
        } + "\nTotal Geodes: $geodes"
}