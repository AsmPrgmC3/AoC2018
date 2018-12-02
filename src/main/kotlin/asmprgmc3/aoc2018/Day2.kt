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
}