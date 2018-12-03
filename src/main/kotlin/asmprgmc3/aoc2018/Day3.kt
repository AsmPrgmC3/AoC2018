package asmprgmc3.aoc2018

object Day3 {
    private data class Claim(val id: Int, val x1: Int, val y1: Int, val x2: Int, val y2: Int)

    private fun extractClaims(input: List<String>): List<Claim> {
        val regex = Regex("""#(?<id>\d+) @ (?<x>\d+),(?<y>\d+): (?<w>\d+)x(?<h>\d+)""")
        return input
                .asSequence()
                .map { regex.find(it)!! }
                .map {
                    val x = it.groups["x"]!!.value.toInt()
                    val y = it.groups["y"]!!.value.toInt()
                    Claim(
                            it.groups["id"]!!.value.toInt(),
                            x,
                            y,
                            x - 1 + it.groups["w"]!!.value.toInt(),
                            y - 1 + it.groups["h"]!!.value.toInt()
                    )
                }
                .toList()
    }

    fun part1(input: List<String>): Int {
        val claims = extractClaims(input)

        val gridSize = 1000

        val grid = IntArray(gridSize * gridSize) { 0 }
        for (claim in claims) {
            for (y in claim.y1..claim.y2) {
                for (x in claim.x1..claim.x2) {
                    grid[x + y * gridSize] += 1
                }
            }
        }

        return grid.count { it > 1 }
    }

    fun part2(input: List<String>): Int {
        val claims = extractClaims(input)
        val overlaps = IntArray(claims.size) { claims[it].id }

        val gridSize = 1000

        val grid = IntArray(gridSize * gridSize) { 0 }
        for (claim in claims) {
            for (y in claim.y1..claim.y2) {
                for (x in claim.x1..claim.x2) {
                    val idx = x + y * gridSize
                    if (grid[idx] != 0) {
                        overlaps[grid[idx] - 1] = 0
                        overlaps[claim.id - 1] = 0
                    }
                    grid[idx] = claim.id
                }
            }
        }

        return overlaps.find { it != 0 }!!
    }
}
