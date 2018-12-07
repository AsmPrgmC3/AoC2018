package asmprgmc3.aoc2018

import kotlin.math.abs

object Day6 {
    private data class GridData(val gridWidth: Int, val gridHeight: Int, val gridLocations: List<Pair<Int, Int>>)

    private fun getGridData(locations: List<Pair<Int, Int>>): GridData {
        val minX = locations.minBy { it.first }!!.first
        val maxX = locations.maxBy { it.first }!!.first
        val minY = locations.minBy { it.second }!!.second
        val maxY = locations.maxBy { it.second }!!.second

        val gridLocations = locations.map { Pair(it.first - minX + 1, it.second - minY + 1) }

        return GridData(maxX - minX + 2, maxY - minY + 2, gridLocations)
    }

    private fun getLocations(input: List<String>): List<Pair<Int, Int>> {
        return input.map {
            val nums = it.split(", ")
            Pair(nums[0].toInt(), nums[1].toInt())
        }
    }

    fun part1(input: List<String>): Int {
        val locations = getLocations(input)

        val (gridWidth, gridHeight, gridLocations) = getGridData(locations)

        val grid = (0 until gridWidth * gridHeight).map { idx ->
            val xPos = idx % gridWidth
            val yPos = idx / gridWidth
            val distances = gridLocations.mapIndexed { i, (x, y) -> i to abs(x - xPos) + abs(y - yPos) }
            val min = distances.minBy { it.second }!!

            if (distances.count { it.second == min.second } == 1) {
                min.first
            } else {
                -1
            }
        }

        val blackList = grid
                .asSequence()
                .filterIndexed { idx, _ ->
                    idx / gridWidth == 0
                            || idx / gridWidth == gridHeight - 1
                            || idx % gridWidth == 0
                            || idx % gridWidth == gridWidth - 1
                }
                .distinct()
                .toList()

        return (0 until locations.size)
                .filter { it !in blackList }
                .map { location -> grid.count { it == location } }
                .max()!!
    }

    fun part2(input: List<String>): Int {
        val locations = getLocations(input)

        val (gridWidth, gridHeight, gridLocations) = getGridData(locations)

        return (0 until gridWidth * gridHeight)
                .map { idx ->
                    val xPos = idx % gridWidth
                    val yPos = idx / gridWidth

                    gridLocations
                            .asSequence()
                            .map { abs(it.first - xPos) + abs(it.second - yPos) }
                            .sum()
                }
                .filter { it < 10000 }
                .count()
    }
}
