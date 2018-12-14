package asmprgmc3.aoc2018

object Day14 {
    private fun runRecipes(): Sequence<List<Int>> {
        val recipes = mutableListOf(3, 7)
        var elve1 = 0
        var elve2 = 1

        return generateSequence {
            val score1 = recipes[elve1]
            val score2 = recipes[elve2]

            val sum = score1 + score2

            if (sum >= 10) {
                recipes.add(sum / 10)
            }
            recipes.add(sum % 10)

            elve1 = (elve1 + score1 + 1) % recipes.size
            elve2 = (elve2 + score2 + 1) % recipes.size

            recipes
        }
    }

    fun part1(input: List<String>): String {
        val after = input[0].toInt()
        val wait = after + 10

        val recipes = runRecipes().find { it.size >= wait }!!

        return (after until after + 10).map { recipes[it] }.joinToString("")
    }

    fun part2(input: List<String>): Int {
        val suffix = input[0]
        val suffixZone = suffix.length + 1

        return runRecipes()
                .dropWhile { it.size < suffixZone }
                .map { Pair(it, it.subList(it.size - suffixZone, it.size).joinToString("")) }
                .filter { it.second.contains(suffix) }
                .map { it.first.joinToString("").indexOf(suffix) }
                .first()
    }
}
