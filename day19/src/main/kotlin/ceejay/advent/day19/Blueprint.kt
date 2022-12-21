package ceejay.advent.day19

import ceejay.advent.util.enumMapOf
import kotlin.text.RegexOption.IGNORE_CASE

enum class Material {
    ORE, CLAY, OBSIDIAN, GEODE
}

data class Blueprint(
    val id: Int,
    val robotCosts: Map<Material, Counter>,
) {
    companion object {
        private val headerRegex = "^Blueprint (\\d+)$".toRegex(IGNORE_CASE)
        private val entryRegex =
            "^each (ore|clay|obsidian|geode) robot costs (.+)$".toRegex(IGNORE_CASE)

        fun parse(line: String): Blueprint {
            val (header, body) = line.split(":").map { it.trim() }

            val robotCosts = enumMapOf<Material, Counter>()

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


                        val counter = Counter.ofMaterial(
                            material = Material.valueOf(costMaterial.uppercase()),
                            amount = amount.toInt()
                        )

                        robotCosts.merge(robotMaterial, counter) { a, b -> a + b }
                    }

                }

            return Blueprint(id, robotCosts)
        }
    }
}