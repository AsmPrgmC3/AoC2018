package asmprgmc3.aoc2018

import java.util.*

object Day12 {
    private fun parseInput(input: List<String>): Pair<LinkedList<Boolean>, List<List<Boolean>>> {
        val stateRegex = Regex("""initial state: (?<state>[.#]+)""")
        val ruleRegex = Regex("""(?<rule>[.#]{5}) => (?<alive>[.#])""")

        fun Char.toBoolean() = this == '#'

        return Pair(
                stateRegex.find(input[0])!!
                        .groupString("state")
                        .map(Char::toBoolean)
                        .toCollection(LinkedList()),
                input
                        .asSequence()
                        .drop(2)
                        .map { ruleRegex.find(it)!!.run { Pair(groupString("rule"), groupString("alive")) } }
                        .map { Pair(it.first.map(Char::toBoolean), it.second[0].toBoolean()) }
                        .filter { it.second }
                        .map { it.first }
                        .toList()
        )
    }

    private fun nextGeneration(offset: Int, rules: List<List<Boolean>>, state: LinkedList<Boolean>): Pair<Int, LinkedList<Boolean>> {
        var newOffset = offset

        var firstPlant = state.indexOf(true)
        if (firstPlant < 4) {
            repeat(4) { state.addFirst(false) }
            firstPlant += 4
            newOffset += 4
        }

        newOffset -= 2

        val lastPlant = state.lastIndexOf(true)
        if (lastPlant > state.size - 5) {
            repeat(4) { state.addLast(false) }
        }

        return Pair(
                newOffset,
                state
                        .asSequence()
                        .windowed(5)
                        .map { it in rules }
                        .toCollection(LinkedList())
        )
    }

    private fun calculateSum(offset: Long, state: List<Boolean>): Long {
        return state
                .asSequence()
                .mapIndexed { i, alive -> if (alive) i - offset else 0 }
                .sum()
    }

    fun part1(input: List<String>): Long {
        var (state, rules) = parseInput(input)

        var offset = 0

        repeat(20) { _ ->
            val next = nextGeneration(offset, rules, state)
            offset = next.first
            state = next.second
        }

        return calculateSum(offset.toLong(), state)
    }

    private fun getAliveIndices(state: List<Boolean>): List<Int> {
        val firstAlive = state.indexOf(true)

        return state
                .asSequence()
                .mapIndexed { i, alive -> Pair(i, alive) }
                .filter { it.second }
                .map { it.first - firstAlive }
                .toList()
    }

    fun part2(input: List<String>): Long {
        var (state, rules) = parseInput(input)

        var offset = 0

        var indices = getAliveIndices(state)
        var lastIndices = listOf(0)

        var generations = 0L

        while (indices != lastIndices) {
            val next = nextGeneration(offset, rules, state)
            offset = next.first
            state = next.second

            lastIndices = indices
            indices = getAliveIndices(state)

            generations++
        }

        val generationsLeft = 50_000_000_000 - generations

        return calculateSum(offset - generationsLeft, state)
    }
}
