package asmprgmc3.aoc2018

object Day1 {
    fun part1(input: List<String>): Int {
        var freq = 0
        for (line in input) {
            val number = line.substring(1).toInt()
            when (line[0]) {
                '+' -> freq += number
                '-' -> freq -= number
            }
        }

        return freq
    }
}
