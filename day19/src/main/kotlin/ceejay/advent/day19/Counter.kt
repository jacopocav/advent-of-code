package ceejay.advent.day19

data class Counter(
    val ore: Int = 0,
    val clay: Int = 0,
    val obsidian: Int = 0,
    val geode: Int = 0,
) {

    operator fun get(material: Material): Int = when (material) {
        Material.ORE -> ore
        Material.CLAY -> clay
        Material.OBSIDIAN -> obsidian
        Material.GEODE -> geode
    }

    operator fun plus(other: Counter): Counter = Counter(
        ore = ore + other.ore,
        clay = clay + other.clay,
        obsidian = obsidian + other.obsidian,
        geode = geode + other.geode,
    )

    operator fun minus(other: Counter): Counter = Counter(
        ore = ore - other.ore,
        clay = clay - other.clay,
        obsidian = obsidian - other.obsidian,
        geode = geode - other.geode,
    )

    operator fun times(amount: Int): Counter = Counter(
        ore = ore * amount,
        clay = clay * amount,
        obsidian = obsidian * amount,
        geode = geode * amount,
    )

    infix fun maxDiv(other: Counter): Double {
        val newOre = ore.toDouble() / other.ore
        val newClay = clay.toDouble() / other.clay
        val newObsidian = obsidian.toDouble() / other.obsidian
        val newGeode = geode.toDouble() / other.geode


        return listOf(newOre, newClay, newObsidian, newGeode)
            .filter { it.isFinite() }
            .max()
    }

    infix fun allGreaterThanOrEqual(other: Counter): Boolean =
        ore >= other.ore && clay >= other.clay && obsidian >= other.obsidian && geode >= other.geode

    companion object {
        fun ofMaterial(material: Material, amount: Int): Counter = when (material) {
            Material.ORE -> Counter(ore = amount)
            Material.CLAY -> Counter(clay = amount)
            Material.OBSIDIAN -> Counter(obsidian = amount)
            Material.GEODE -> Counter(geode = amount)
        }
    }
}