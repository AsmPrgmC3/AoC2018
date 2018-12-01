package asmprgmc3.aoc2018

import java.io.File

object TestCases {
    data class TestCase(val name: String, val expected: String, val input: List<String>)

    fun getTestCases(day: Int, part: Int): List<TestCase> {
        return File(
                javaClass.getResource("/inputs/day$day/part$part").toURI()
        ).listFiles().map {
            val lines = it.readLines()

            TestCase(it.nameWithoutExtension, lines[0], lines.drop(1))
        }
    }
}
