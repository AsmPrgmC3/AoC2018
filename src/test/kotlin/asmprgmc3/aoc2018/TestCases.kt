package asmprgmc3.aoc2018

import io.kotlintest.TestContext
import io.kotlintest.specs.AbstractStringSpec
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

    fun AbstractStringSpec.testLoop(day: Int, part: Int, evaluate: TestContext.(TestCases.TestCase) -> Unit) {
        for (test in TestCases.getTestCases(day, part)) {
            "Day $day Part $part > ${test.name}" {
                evaluate(test)
            }
        }
    }
}
