package asmprgmc3.aoc2018

import java.util.*

object Day7 {
    private fun parseInput(input: List<String>): SortedMap<String, List<String>> {
        val regex = Regex("""Step (?<required>[A-Z]) must be finished before step (?<step>[A-Z]) can begin.""")

        val map = TreeMap<String, MutableList<String>>()

        for (line in input) {
            val match = regex.find(line)!!
            map.merge(
                    match.groupString("step"),
                    mutableListOf(match.groupString("required"))
            ) { l1, l2 -> l1.apply { addAll(l2) } }
        }

        @Suppress("UNCHECKED_CAST")
        return map as SortedMap<String, List<String>>
    }

    private fun getStarts(map: Map<String, List<String>>): MutableList<String> {
        return map
                .asSequence()
                .flatMap { it.value.asSequence() }
                .distinct()
                .filter { it !in map }
                .sorted()
                .toMutableList()
    }

    fun part1(input: List<String>): String {
        val map = parseInput(input)

        val toDo = getStarts(map)

        var result = toDo.removeAt(0)
        while (map.size > 0) {
            val (step) = map.entries.find { (_, requirements) -> requirements.all { result.contains(it) } }!!

            if (toDo.size > 0 && toDo[0] < step) {
                result += toDo.removeAt(0)
                continue
            }

            map.remove(step)
            result += step
        }

        return result
    }


    private data class Worker(var letter: String = "", var time: Int = 0) {
        fun newJob(letter: Char): Worker {
            this.letter = letter.toString()
            time = letter - 'A' + 60

            return this
        }

        fun tick(): Worker {
            time -= 1

            return this
        }
    }

    private fun updateToDo(toDo: MutableList<String>, map: MutableMap<String, List<String>>, done: String) {
        val toDoUpdate = map
                .asSequence()
                .filter { (_, requirements) ->
                    requirements.all { done.contains(it) }
                }
                .map { it.key }
                .toList()

        toDoUpdate.forEach { map.remove(it) }
        toDo += toDoUpdate
        toDo.sort()
    }

    fun part2(input: List<String>): Int {
        val map = parseInput(input)

        val toDo = getStarts(map)

        var done = ""

        var workers = List(5) { Worker() }

        var time = 0
        while (map.size > 0 || toDo.size > 0) {
            workers
                    .asSequence()
                    .filter { it.time == 0 }
                    .forEach { done += it.letter }

            updateToDo(toDo, map, done)

            workers = workers
                    .asSequence()
                    .map {
                        if (it.time <= 0 && toDo.size > 0) {
                            val newOrder = toDo.removeAt(0)
                            it.newJob(newOrder[0])
                        } else {
                            it.tick()
                        }
                    }
                    .toList()
            time++
        }

        time += workers.maxBy { it.time }!!.time

        return time
    }
}
