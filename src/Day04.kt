fun main() {
    fun part1(input: List<String>): Int =
        input
            .map { line ->
                line
                    .split(",")
                    .map(ElfCleaner::fromString)
                    .let { it[0].overlapsWithAllSection(it[1]) }
            }
            .sumOf(Boolean::toInt)

    fun part2(input: List<String>): Int =
        input
            .map { line ->
                line
                    .split(",")
                    .map(ElfCleaner::fromString)
                    .let { it[0].overlapsWithOneSection(it[1]) }
            }
            .sumOf(Boolean::toInt)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

private class ElfCleaner private constructor(
    private val sections: Set<Int>
) {
    fun overlapsWithAllSection(other: ElfCleaner): Boolean =
        overlapsSection(other) { section1, section2 ->
            (section1 intersect section2)
                .let { it == this.sections || it == other.sections }
        }

    fun overlapsWithOneSection(other: ElfCleaner): Boolean =
        overlapsSection(other) { section1, section2 ->
            (section1 intersect section2).isNotEmpty()
        }

    private fun overlapsSection(
        other: ElfCleaner,
        predicate: (Set<Int>, Set<Int>) -> Boolean
    ): Boolean =
        predicate(sections, other.sections)

    companion object {
        fun fromString(string: String) =
            ElfCleaner(
                sections = string
                    .split("-")
                    .let {
                        (it[0].toInt()..it[1].toInt()).toSet()
                    }
            )
    }
}

private fun Boolean.toInt() = if (this) 1 else 0
