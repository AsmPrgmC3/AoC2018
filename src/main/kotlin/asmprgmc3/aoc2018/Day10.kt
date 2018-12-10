package asmprgmc3.aoc2018

object Day10 {
    private data class Point(val x: Int, val y: Int, val dx: Int, val dy: Int) {
        fun moved(): Point {
            return copy(x = x + dx, y = y + dy)
        }
    }

    private fun parseInput(input: List<String>): List<Point> {
        val regex = Regex("""position=<\s*(?<x>-?\d+),\s*(?<y>-?\d+)> velocity=<\s*(?<dx>-?\d+),\s*(?<dy>-?\d+)>""")

        return input
                .asSequence()
                .map { regex.find(it)!! }
                .map {
                    Point(
                            it.groupInt("x"),
                            it.groupInt("y"),
                            it.groupInt("dx"),
                            it.groupInt("dy")
                    )
                }
                .toList()
    }

    fun part1(input: List<String>) {
        var points = parseInput(input)

        (1..20000).forEach { t ->
            points = points.map { it.moved() }

            // I don't want to do any kind of character recognition, so just print it when there are 5 points above each other
            val line = points.any { p ->
                (0 until 5).all { dy ->
                    points.any {
                        it.x == p.x && it.y == p.y + dy
                    }
                }
            }

            if (line) {
                print(t)
                println("--------------------------------------------------------------------------------------------")

                val xs = points.map { it.x }
                val ys = points.map { it.y }
                val minX = xs.min()!!
                val maxX = xs.max()!!
                val minY = ys.min()!!
                val maxY = ys.max()!!

                (minY..maxY).forEach { y ->
                    (minX..maxX).forEach { x ->
                        if (points.any { it.x == x && it.y == y }) {
                            print('#')
                        } else {
                            print(' ')
                        }
                    }
                    print('\n')
                }
            }
        }
    }
}
