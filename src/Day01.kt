private class Elf(
    val calories: List<Int>,
) {
    val totalCalories: Int
        get() = calories.sum()

    companion object {
        fun fromCalories(string: String): Elf =
            Elf(calories = string.lines().map(String::toInt))
    }
}

fun main() {
    // Get the total calories from the elf carrying the most calories
    fun part1(input: String): Int =
        input
            .split("\n\n")
            .map { Elf.fromCalories(it) }
            .maxBy { it.totalCalories }
            .totalCalories

    // Get the total calories from the top 3 elves carrying the most calories
    fun part2(input: String): Int =
        input
            .split("\n\n")
            .map { Elf.fromCalories(it) }
            .sortedByDescending { it.totalCalories }
            .take(3)
            .sumOf { it.totalCalories }

    val input = readInputToText("Day01")
    println(part1(input))
    println(part2(input))
}
