package ceejay.advent.day19

import ceejay.advent.day19.Material.*

data class Counter(
    val ore: Int = 0,
    val clay: Int = 0,
    val obsidian: Int = 0,
    val geode: Int = 0,
) {

    operator fun get(material: Material): Int = when (material) {
        ORE -> ore
        CLAY -> clay
        OBSIDIAN -> obsidian
        GEODE -> geode
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

    infix fun gte(other: Counter): Boolean =
        ore >= other.ore && clay >= other.clay && obsidian >= other.obsidian && geode >= other.geode

    companion object {

        fun ofMaterial(material: Material, amount: Int): Counter = when (material) {
            ORE -> Counter(ore = amount)
            CLAY -> Counter(clay = amount)
            OBSIDIAN -> Counter(obsidian = amount)
            GEODE -> Counter(geode = amount)
        }
    }
}