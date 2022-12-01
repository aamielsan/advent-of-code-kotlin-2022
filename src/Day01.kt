private class Elf(
    val calories: MutableList<Int> = mutableListOf(),
) {
    val totalCalories: Int
        get() = calories.sum()
}

typealias Input = List<String>

private fun Input.toElfList(): List<Elf> =
    fold(emptyList()) { acc, calorie ->
        when {
            calorie.isEmpty() -> acc + listOf(Elf())
            else -> {
                val lastElf = if (acc.isEmpty()) Elf() else acc.last()
                acc.dropLast(1) + listOf(lastElf.also { it.calories.add(calorie.toInt()) })
            }
        }
    }

fun main() {
    // Get the total calories from the elf carrying the most calories
    fun part1(input: List<String>): Int =
        input.toElfList()
            .maxBy { it.totalCalories }
            .totalCalories

    // Get the total calories from the top 3 elves carrying the most calories
    fun part2(input: List<String>): Int =
        input.toElfList()
            .sortedByDescending { it.totalCalories }
            .take(3)
            .sumOf { it.totalCalories }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
