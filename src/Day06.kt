fun main() {
    fun String.indexOfMarker(numberOfDistinct: Int): Int =
        this
            .windowed(size = numberOfDistinct, step = 1)
            .indexOfFirst { it.toSet().size == numberOfDistinct }
            .let { it + numberOfDistinct }

    fun part1(input: String): Int = input.indexOfMarker(4)

    fun part2(input: String): Int = input.indexOfMarker(14)

    val input = readInputToText("Day06")

    println(part1(input))
    println(part2(input))
}
