package ceejay.advent.day19

import ceejay.advent.util.enumMapOf
import kotlin.text.RegexOption.IGNORE_CASE

enum class Material(val value: Int) {
    ORE(1), CLAY(2), OBSIDIAN(3), GEODE(4);

    companion object {
        val all = values().toSet()
    }
}

data class Blueprint(
    val id: Int,
    val robotCosts: Map<Material, Map<Material, Int>>,
) {
    companion object {
        private val headerRegex = "^Blueprint (\\d+)$".toRegex(IGNORE_CASE)
        private val entryRegex =
            "^each (ore|clay|obsidian|geode) robot costs (.+)$".toRegex(IGNORE_CASE)

        fun parse(line: String): Blueprint {
            val (header, body) = line.split(":").map { it.trim() }

            val robotCosts = enumMapOf<Material, MutableMap<Material, Int>>()

            val id = headerRegex.matchEntire(header)?.groups?.get(1)?.value?.toInt()
                ?: error("cannot parse line $line")

            body.split(".")
                .filter { it.isNotBlank() }
                .map { entry ->
                    val groups = entryRegex.matchEntire(entry.trim())?.groups
                        ?: error("cannot parse line $line")

                    val robotMaterial = groups[1]!!.value.let { Material.valueOf(it.uppercase()) }

                    groups[2]!!.value.split("and").forEach { cost ->
                        val (amount, costMaterial) = cost.trim().split(" ")


                        robotCosts.getOrPut(robotMaterial) { enumMapOf() }
                            .merge(
                                Material.valueOf(costMaterial.uppercase()),
                                amount.toInt()
                            ) { a, b -> a + b }

                    }

                }

            return Blueprint(id, robotCosts)
        }
    }
}