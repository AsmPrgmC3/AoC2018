package asmprgmc3.aoc2018

fun MatchResult.groupString(name: String): String =
        groups[name]!!.value

fun MatchResult.groupInt(name: String): Int =
        groups[name]!!.value.toInt()
