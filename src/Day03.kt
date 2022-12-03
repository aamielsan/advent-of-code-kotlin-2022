fun main() {
    val scoreByItemType = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    fun part1(input: List<String>): Int =
        input
            .map { it.chunkedSequence(it.length / 2, CharSequence::toSet) }
            .map { it.reduce(Set<Char>::intersect).first() }
            .sumOf { scoreByItemType.indexOf(it) }

    fun part2(input: List<String>): Int =
        input
            .map(String::toSet)
            .chunked(3)
            .map { it.reduce(Set<Char>::intersect).first() }
            .sumOf { scoreByItemType.indexOf(it) }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
