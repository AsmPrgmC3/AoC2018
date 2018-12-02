package asmprgmc3.aoc2018

import asmprgmc3.aoc2018.TestCases.testLoop
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class Tests : StringSpec({
    testLoop(1, 1) {test ->
        Day1.part1(test.input) shouldBe test.expected.toInt()
    }

    testLoop(1, 2) {test ->
        Day1.part2(test.input) shouldBe test.expected.toInt()
    }

    testLoop(2, 1) { test ->
        Day2.part1(test.input) shouldBe test.expected.toInt()
    }
})