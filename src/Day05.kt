fun main() {
    fun part1(input: String): String {
        val (crateInput, instructionInput) = input.split("\n\n")
        val crates = crateInput.toCrates()

        instructionInput
            .toInstructions()
            .forEach { (count, from, to) ->
                val boxes = crates[from].takeLast(count).reversed()
                crates[to].addAll(boxes)
                repeat(count) {
                    crates[from].removeLast()
                }
            }
        return crates.fold("") { acc, stack ->
            acc + stack.last()
        }
    }

    fun part2(input: String): String {
        val (crateInput, instructionInput) = input.split("\n\n")
        val crates = crateInput.toCrates()
        instructionInput
            .toInstructions()
            .forEach { (count, from, to) ->
                val boxes = crates[from].takeLast(count)
                crates[to].addAll(boxes)
                repeat(count) {
                    crates[from].removeLast()
                }
            }

        return crates.fold("") { acc, stack ->
            acc + stack.last()
        }
    }

    val input = readInputToText("Day05")

    println(part1(input))
    println(part2(input))
}

private fun String.toCrates() =
    replace("[\\[\\]]".toRegex(), "")
        .replace("\\s\\s\\s\\s".toRegex(), " ")
        .lines()
        .let { it.take(it.size - 1) }
        .map { it.split(" ") }
        .let { Pair(it, it.maxBy { line -> line.size }.size) }
        .let {
            val (lines, cratesCount) = it
            lines.foldRight(MutableList(cratesCount) { mutableListOf<String>() }) { line, acc ->
                line.forEachIndexed { index, s ->
                    acc[index].add(s)
                    acc[index] = acc[index].filter(String::isNotEmpty).toMutableList()
                }
                acc
            }
        }

private fun String.toInstructions() =
    lines()
        .mapNotNull { "move (\\d+) from (\\d+) to (\\d+)".toRegex().matchEntire(it) }
        .map { it.groupValues.drop(1) }
        .map { listOf(it[0].toInt(), it[1].toInt() - 1, it[2].toInt() - 1) }
