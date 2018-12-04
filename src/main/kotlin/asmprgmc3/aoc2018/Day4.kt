package asmprgmc3.aoc2018

import java.time.LocalDate

object Day4 {
    private data class Record(val date: String, val guard: Int, val from: Int, val to: Int) // Represents a time a certain guard is asleep

    private data class Records(val guards: List<Int>, val records: List<Record>)

    private abstract class InputLine
    private data class BeginShift(val guard: Int, val date: String) : InputLine()
    private data class BeginSleep(val date: String, val minute: Int) : InputLine()
    private data class EndSleep(val date: String, val minute: Int) : InputLine()

    private fun parseInput(lines: List<String>): List<InputLine> {
        val regex = Regex("""\[(?<date>\d{4}-\d{2}-\d{2}) (?<hour>\d{2}):(?<minute>\d{2})] """
                + """(?<beginShift>Guard #(?<guard>\d+) begins shift)?(?<beginSleep>falls asleep)?(?<endSleep>wakes up)?""")

        return lines.map {
            val match = regex.find(it)!!
            val date = LocalDate.parse(match.groupString("date")).run {
                if (match.groupInt("hour") > 12) {
                    plusDays(1)
                } else {
                    this
                }
                        .toString()
            }

            val minute = match.groupInt("minute")
            when {
                null != match.groups["beginShift"] -> BeginShift(match.groupInt("guard"), date)
                null != match.groups["beginSleep"] -> BeginSleep(date, minute)
                null != match.groups["endSleep"] -> EndSleep(date, minute)
                else -> throw Exception("Nothing happens?!")
            }
        }
    }

    private fun getRecords(input: List<String>): Records {
        val happenings = parseInput(input)

        @Suppress("UNCHECKED_CAST")
        val shifts = happenings.filter { it is BeginShift } as List<BeginShift>
        @Suppress("UNCHECKED_CAST")
        val beginSleeps = happenings.filter { it is BeginSleep } as List<BeginSleep>
        @Suppress("UNCHECKED_CAST")
        val endSleeps = happenings.filter { it is EndSleep } as List<EndSleep>

        val records = shifts.flatMap { shift ->
            beginSleeps
                    .asSequence()
                    .filter { it.date == shift.date }
                    .map { beginSleep ->
                        val shiftEnd = endSleeps
                                .asSequence()
                                .filter { it.date == shift.date && it.minute > beginSleep.minute }
                                .minBy { it.minute }!!
                                .minute - 1
                        Record(shift.date, shift.guard, beginSleep.minute, shiftEnd)
                    }
                    .toList()
        }

        val guards = records.asSequence().map { it.guard }.distinct().toList()

        return Records(guards, records)
    }

    fun part1(input: List<String>): Int {
        val (guards, records) = getRecords(input)

        val mostSleepyGuard = guards.maxBy { guard ->
            records
                    .asSequence()
                    .filter { it.guard == guard }
                    .map { it.to - it.from + 1 }
                    .sum()
        }!!

        val mostSleepyMinute = (0..59).maxBy { minute ->
            records
                    .asSequence()
                    .filter { it.guard == mostSleepyGuard }
                    .filter { minute >= it.from && minute <= it.to }
                    .count()
        }!!

        return mostSleepyGuard * mostSleepyMinute
    }

    fun part2(input: List<String>): Int {
        val (guards, records) = getRecords(input)


        val (guard, minute) = (1..59).flatMap { minute -> guards.map { Pair(it, minute) } }
                .maxBy { (guard, minute) ->
                    records
                            .asSequence()
                            .filter { it.guard == guard }
                            .filter { minute >= it.from && minute <= it.to }
                            .count()
                }!!

        return guard * minute
    }
}