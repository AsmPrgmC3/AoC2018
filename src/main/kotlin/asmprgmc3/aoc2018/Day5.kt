package asmprgmc3.aoc2018

import kotlin.math.abs

object Day5 {
    private fun react(polymer: Sequence<Char>): List<Char> {
        return polymer
                .fold(mutableListOf()) { result, char ->
                    if (result.isNotEmpty() && abs(result.last() - char) == 32)
                        result.apply { removeAt(size - 1) }
                    else
                        result.apply { add(char) }
                }
    }

    fun part1(input: List<String>): Int {
        return react(input[0].asSequence()).size
    }

    fun part2(input: List<String>): Int {
        return ('a'..'z')
                .asSequence()
                .map { Pair(it, it.toUpperCase()) }
                .map { (lower, upper) ->
                    input[0]
                            .asSequence()
                            .filter { it != lower && it != upper }
                }
                .map { react(it).size }
                .min()!!
    }
}
