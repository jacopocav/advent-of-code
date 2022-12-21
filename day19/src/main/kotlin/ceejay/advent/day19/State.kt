package ceejay.advent.day19

import ceejay.advent.day19.Material.GEODE
import ceejay.advent.util.toEnumMap

private typealias MaterialCounter = Map<Material, Int>

data class State private constructor(
    private val resources: MaterialCounter,
    private val robots: MaterialCounter,
    val minutesRemaining: Int,
    val prev: State? = null,
) {
    val geodes: Int get() = resources(GEODE) + robots(GEODE) * minutesRemaining
    fun buildRobot(material: Material, costs: MaterialCounter): State = copy(
        minutesRemaining = minutesRemaining - 1,
        robots = robots.add(material, 1),
        resources = robots.add(resources.subtract(costs)),
        prev = this
    )

    fun wait(minutes: Int = 1): State = when {
        minutes == 0 -> this
        minutes > 0 -> copy(
            minutesRemaining = minutesRemaining - minutes,
            resources = (robots * minutes).add(resources),
            prev = this
        )

        else -> error("cannot wait negative minutes ($minutes)")
    }

    fun canPay(costs: MaterialCounter): Boolean = costs.all { (material, cost) ->
        resources(material) >= cost
    }

    fun canPayInTheFuture(costs: MaterialCounter): Boolean = costs.all { (material, cost) ->
        resources(material) + robots(material) * minutesRemaining >= cost
    }

    fun canProduce(material: Material): Boolean = robots(material) > 0

    fun resources(material: Material) = resources[material]!!
    fun robots(producedMaterial: Material) = robots[producedMaterial]!!


    private fun logSingle() = "$minutesRemaining -> resources=$resources robots=$robots"

    fun log(): String = generateSequence(this) { it.prev }
        .toList()
        .asReversed()
        .joinToString(separator = "\n") { it.logSingle() }.let {
            if (minutesRemaining > 0) {
                it + "\n" + wait(minutesRemaining).logSingle()
            } else it
        } + "\nTotal Geodes: $geodes"

    companion object {
        operator fun invoke(
            resources: MaterialCounter,
            robots: MaterialCounter,
            minutesRemaining: Int,
            prev: State? = null,
        ): State = State(
            resources = resources.toEnumMap().fill(),
            robots = robots.toEnumMap().fill(),
            minutesRemaining = minutesRemaining,
            prev = prev,
        )

        private fun MaterialCounter.fill() = Material.all
            .associateWith { this[it] ?: 0 }
            .toEnumMap()

        private fun MaterialCounter.add(other: MaterialCounter): MaterialCounter = Material.all
            .associateWith { (this[it] ?: 0) + (other[it] ?: 0) }.toEnumMap()


        private fun MaterialCounter.subtract(other: MaterialCounter): MaterialCounter = Material.all
            .associateWith { (this[it] ?: 0) - (other[it] ?: 0) }.toEnumMap()

        private fun MaterialCounter.add(material: Material, amount: Int): MaterialCounter =
            Material.all.associateWith {
                (this[it] ?: 0) + if (it == material) amount else 0
            }.toEnumMap()


        private operator fun MaterialCounter.times(factor: Int): MaterialCounter =
            if (factor == 1) this.toEnumMap()
            else Material.all
                .associateWith { (this[it] ?: 0) * factor }.toEnumMap()
    }
}