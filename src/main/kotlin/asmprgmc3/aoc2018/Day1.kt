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
        fun addOp(number: Int) = { value: Int -> value + number }
        fun subOp(number: Int) = { value: Int -> value - number }

        var freq = 0
        val freqs = mutableSetOf(0)
        var i = -1

        val ops = input.map {
            val number = it.substring(1).toInt()
            when (it[0]) {
                '+' -> addOp(number)
                '-' -> subOp(number)
                else -> throw IllegalArgumentException("invalid Operation: ${it[0]}")
            }
        }

        while (true) {
            i++
            if (i == ops.size) {
                i = 0
            }

            freq = ops[i](freq)

            if (freqs.contains(freq)) {
                return freq
            }
            freqs.add(freq)
        }
    }
}
