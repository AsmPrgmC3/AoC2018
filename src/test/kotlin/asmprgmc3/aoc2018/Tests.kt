package asmprgmc3.aoc2018

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class Tests : StringSpec({
    for (test in TestCases.getTestCases(1, 1)) {
        "Day 1 Part 1 > ${test.name}" {
            Day1.part1(test.input) shouldBe test.expected.toInt()
        }
    }
})