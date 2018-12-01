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

    fun part2(input: List<String>): Int {
        var freq = 0
        val freqs = mutableListOf(0)
        var i = -1
        while (true) {
            i++
            if (i == input.size) {
                i = 0
            }

            val line = input[i]
            val number = line.substring(1).toInt()
            when (line[0]) {
                '+' -> freq += number
                '-' -> freq -= number
            }

            if (freqs.contains(freq)) {
                return freq
            }
            freqs.add(freq)
        }
    }
}
