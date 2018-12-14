package asmprgmc3.aoc2018

object Day14 {
    private fun parseInput(input: List<String>): Int {
        return input[0].toInt()
    }

    fun part1(input: List<String>): String {
        val after = parseInput(input)
        val wait = after + 10

        val recipes = mutableListOf(3, 7)
        var elve1 = 0
        var elve2 = 1

        while (recipes.size < wait) {
            val score1 = recipes[elve1]
            val score2 = recipes[elve2]

            val sum = score1 + score2

            if (sum >= 10) {
                recipes.add(sum / 10)
            }
            recipes.add(sum % 10)

            elve1 = (elve1 + score1 + 1) % recipes.size
            elve2 = (elve2 + score2 + 1) % recipes.size
        }

        return (after until after + 10).map { recipes[it] }.joinToString("")
    }
}
