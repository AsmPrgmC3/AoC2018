package asmprgmc3.aoc2018

object Day2 {
    fun part1(input: List<String>): Int {
        var twos = 0
        var threes = 0


        for (line in input) {
            val counts = line
                    .groupBy { it }
                    .map { it.value.size }
            if (counts.contains(2)) {
                twos++
            }
            if (counts.contains(3)) {
                threes++
            }
        }

        return twos * threes
    }

    fun part2(input: List<String>): String {
        val lineLength = input[0].length
        for (i in input.indices) {
            for (j in (i + 1) until input.size) {
                val equal = input[i].filterIndexed { pos, char -> input[j][pos] == char }
                if (equal.length == lineLength - 1) {
                    return equal
                }
            }
        }
        throw IllegalArgumentException("No two IDs differ at exactly 1 position")
    }
}
