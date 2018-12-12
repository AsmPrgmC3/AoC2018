package asmprgmc3.aoc2018

import kotlin.math.max

object Day11 {
    private val GRID_SIZE = 300

    private fun getSerialNumber(input: List<String>): Int {
        return input[0].toInt()
    }

    private fun calculateGrid(serialNumber: Int): Array<IntArray> {
        return Array(GRID_SIZE) { ix ->
            IntArray(GRID_SIZE) { iy ->
                val x = ix + 1
                val y = iy + 1
                val rackId = x + 10
                val value = (rackId * y + serialNumber) * rackId

                (value / 100) % 10 - 5
            }
        }
    }

    fun part1(input: List<String>): String {
        val grid = calculateGrid(getSerialNumber(input))

        val (topX, topY) = (0..GRID_SIZE - 3).flatMap { i -> (0..GRID_SIZE - 3).map { Pair(i, it) } }
                .maxBy { (x, y) ->
                    (0 until 3).map { dx ->
                        (0 until 3).map { dy ->
                            grid[x + dx][y + dy]
                        }.sum()
                    }.sum()
                }!!
        return "${topX + 1},${topY + 1}"
    }

    fun part2(input: List<String>): String {
        val grid = calculateGrid(getSerialNumber(input))

        val sums = Array(GRID_SIZE) { IntArray(GRID_SIZE) { 0 } }
        sums[0][0] = grid[0][0]
        (1 until GRID_SIZE).forEach {
            sums[it][0] = sums[it - 1][0] + grid[it][0]
            sums[0][it] = sums[0][it - 1] + grid[0][it]
        }
        (1 until GRID_SIZE).forEach { x ->
            (1 until GRID_SIZE).forEach { y ->
                sums[x][y] = grid[x][y] + sums[x - 1][y] + sums[x][y - 1] - sums[x - 1][y - 1]
            }
        }

        val (topX, topY, size, _) = (1 until GRID_SIZE).flatMap { i -> (1 until GRID_SIZE).map { Pair(i, it) } }
                .flatMap { (x, y) ->
                    (0 until GRID_SIZE - max(x, y)).map { size ->
                        val cx = x - 1
                        val cy = y - 1
                        GridSquare(x + 1, y + 1, size, sums[cx + size][cy + size] - sums[cx + size][cy] - sums[cx][cy + size] + sums[cx][cy])
                    }
                }
                .maxBy { it.value }!!


        return "$topX,$topY,$size"
    }

    private data class GridSquare(val topX: Int, val topY: Int, val size: Int, val value: Int)
}
