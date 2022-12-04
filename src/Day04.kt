fun main() {
    fun part1(input: List<String>): Int {
        fun check(set1: Set<Int>, set2: Set<Int>): Boolean =
            (set1 intersect set2)
                .let { it == set1 || it == set2 }

        return input
            .map { line ->
                line
                    .split(",")
                    .map(ElfCleaner::fromString)
                    .let { it[0].overlaps(it[1], ::check) }
            }
            .sumOf(Boolean::toInt)
    }

    fun part2(input: List<String>): Int {
        fun check(set1: Set<Int>, set2: Set<Int>): Boolean =
            (set1 intersect set2).isNotEmpty()

        return input
            .map { line ->
                line
                    .split(",")
                    .map(ElfCleaner::fromString)
                    .let { it[0].overlaps(it[1], ::check)}
            }
            .sumOf(Boolean::toInt)
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

private class ElfCleaner private constructor(
    private val coverage: Set<Int>
) {
    fun overlaps(
        other: ElfCleaner,
        predicate: (Set<Int>, Set<Int>) -> Boolean
    ): Boolean =
        predicate(coverage, other.coverage)

    companion object {
        fun fromString(string: String) =
            ElfCleaner(
                coverage = string
                    .split("-")
                    .let {
                        (it[0].toInt()..it[1].toInt()).toSet()
                    }
            )
    }
}

private fun Boolean.toInt() = if (this) 1 else 0
