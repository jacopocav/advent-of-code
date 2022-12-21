package ceejay.advent.day19

import ceejay.advent.day19.Material.GEODE
import ceejay.advent.day19.Material.ORE
import ceejay.advent.util.enumMapOf
import kotlin.math.ceil

class BlueprintExecutor(private val blueprint: Blueprint) {
    private val maxCosts = Material.all.associateWith { material ->
        when (material) {
            GEODE -> Int.MAX_VALUE
            else -> blueprint.robotCosts.values.maxOf { cost -> cost[material] ?: 0 }
        }
    }.toMap(enumMapOf())

    fun findBestGeodeProducingPath(maxMinutes: Int): State {
        val queue = ArrayDeque<State>()

        queue += State(
            resources = enumMapOf(),
            robots = enumMapOf(ORE to 1),
            minutesRemaining = maxMinutes,
        )

        var best = worstState

        while (queue.isNotEmpty()) {
            val state = queue.first().also { queue.remove(it) }

            if (state > best) {
                best = state
            }

            val nextStates = blueprint.nextStates(state)
            queue += nextStates
        }

        return best
    }

    private fun Blueprint.nextStates(state: State): List<State> = when (state.minutesRemaining) {
        // building in the last minute has no benefit: just wait
        0, 1 -> emptyList()

        else -> robotCosts.mapNotNull { (producedMaterial, costs) ->
            when {
                // don't need to build more robots of this kind
                producedMaterial != GEODE
                    && state.robots(producedMaterial) >= maxCosts[producedMaterial]!! -> null

                // can build the robot right now
                state.canPay(costs) -> state.buildRobot(producedMaterial, costs)

                // cannot build the robot, even in the future
                !state.canPayInTheFuture(costs) -> null

                // can build the robot in the future
                else -> {
                    val waitMinutes = costs
                        .filterKeys { state.canProduce(it) }
                        .maxOf { (material, cost) ->
                            val difference = cost - state.resources(material)
                            val robots = state.robots(material)
                            ceil(difference.toDouble() / robots).toInt()
                        }

                    if (state.minutesRemaining - waitMinutes - 1 > 0)
                        state.wait(waitMinutes)
                            .buildRobot(producedMaterial, costs)
                    else null
                }
            }
        }
    }

    companion object {
        private val worstState = State(
            resources = mapOf(GEODE to -1),
            robots = mapOf(),
            minutesRemaining = 0
        )

        private operator fun State.compareTo(other: State): Int =
            geodes.compareTo(other.geodes)
    }
}