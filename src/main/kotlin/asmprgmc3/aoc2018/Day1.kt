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
        val freqs = HashSet<Int>()
        var i = -1

        val ops = input.map {
            val number = it.substring(1).toInt()
            when (it[0]) {
                '+' -> addOp(number)
                '-' -> subOp(number)
                else -> throw IllegalArgumentException("invalid Operation: ${it[0]}")
            }
        }

        while (freqs.add(freq)) { // HashSet#add returns false if the freq is already in the Set
            i = (i + 1) % ops.size

            freq = ops[i](freq)
        }

        return freq
    }
}
