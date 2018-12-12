package asmprgmc3.aoc2018

import java.util.*

object Day12 {
    fun parseInput(input: List<String>): Pair<LinkedList<Boolean>, List<List<Boolean>>> {
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

    fun part1(input: List<String>): Int {
        var (state, rules) = parseInput(input)

        var offset = 0

        repeat(20) { _ ->
            var firstPlant = state.indexOf(true)
            if (firstPlant < 4) {
                repeat(4) { state.addFirst(false) }
                firstPlant += 4
                offset += 4
            }

            val lastPlant = state.lastIndexOf(true)
            if (lastPlant > state.size - 5) {
                repeat(4) { state.addLast(false) }
            }

            state = state
                    .asSequence()
                    .windowed(5)
                    .map { it in rules }
                    .toCollection(LinkedList())

            offset -= 2
        }

        return state
                .asSequence()
                .mapIndexed { i, alive -> if (alive) i - offset else 0 }
                .sum()
    }
}
