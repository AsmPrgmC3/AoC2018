package asmprgmc3.aoc2018

import java.util.*

object Day8 {
    private fun getNumbers(input: List<String>): LinkedList<Int> {
        return input[0]
                .split(' ')
                .asSequence()
                .map { it.toInt() }
                .toCollection(LinkedList())
    }

    fun part1(input: List<String>): Int {
        val numbers = getNumbers(input)

        fun nextPair(): Pair<Int, Int> {
            return Pair(numbers.poll(), numbers.poll())
        }

        val stack = Stack<Pair<Int, Int>>()
        stack.push(nextPair())

        var sum = 0

        while (stack.size > 0) {
            if (stack.peek().first > 0) {
                stack.push(stack.pop().run { Pair(first - 1, second) })
                stack.push(nextPair())
            } else {
                (1..stack.pop().second).forEach { _ ->
                    sum += numbers.poll()
                }
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val numbers = getNumbers(input)

        fun nextPair(): Pair<Int, Int> {
            return Pair(numbers.poll(), numbers.poll())
        }

        fun nodeValue(node: Pair<Int, Int>): Int {
            return if (node.first == 0) {
                (1..node.second).map { numbers.poll() }.sum()
            } else {
                val childs = (1..node.first).map { nodeValue(nextPair()) }
                (1..node.second)
                        .map { numbers.poll() }
                        .map { if (it > 0 && it <= childs.size) childs[it - 1] else 0 }
                        .sum()
            }
        }

        return nodeValue(nextPair())
    }
}
