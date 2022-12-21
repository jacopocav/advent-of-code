package ceejay.advent.day19

import ceejay.advent.day19.Material.GEODE
import kotlin.math.ceil

class BlueprintExecutor(private val blueprint: Blueprint) {
    private val maxCosts = Counter(
        ore = blueprint.robotCosts.values.maxOf { it.ore },
        clay = blueprint.robotCosts.values.maxOf { it.clay },
        obsidian = blueprint.robotCosts.values.maxOf { it.obsidian },
        geode = Int.MAX_VALUE,
    )

    fun findBestGeodeProducingPath(maxMinutes: Int): State {
        val queue = ArrayDeque<State>()

        queue += State(
            resources = Counter(),
            robots = Counter(ore = 1),
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

        else -> {
            robotCosts[GEODE]!!
                .takeIf { state.canPay(it) }
                ?.let { geodeCost ->
                    // heuristic: if we can build a geode right now (not in the future),
                    // always do that instead of anything else
                    listOf(state.buildRobot(GEODE, geodeCost))
                }
                ?: robotCosts.mapNotNull { (producedMaterial, costs) ->
                    when {
                        // don't need to build more robots of this kind
                        state.robots[producedMaterial] >= maxCosts[producedMaterial] -> null

                        // can build the robot right now
                        state.canPay(costs) -> state.buildRobot(producedMaterial, costs)

                        // cannot build the robot, even in the future
                        !state.canPayInTheFuture(costs) -> null

                        // can build the robot in the future
                        else -> {

                            val waitMinutes =
                                ceil((costs - state.resources) maxDiv state.robots).toInt()

                            if (state.minutesRemaining - waitMinutes - 1 > 0)
                                state.wait(waitMinutes)
                                    .buildRobot(producedMaterial, costs)
                            else null
                        }
                    }
                }
        }
    }

    companion object {
        private val worstState = State(
            resources = Counter(geode = -1),
            robots = Counter(),
            minutesRemaining = 0
        )

        private operator fun State.compareTo(other: State): Int =
            geodes.compareTo(other.geodes)
    }
}