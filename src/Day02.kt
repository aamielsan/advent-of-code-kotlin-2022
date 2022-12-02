private fun String.splitToPair(delimeter: String = " "): Pair<String, String> =
    Pair(substringBefore(delimeter), substringAfter(delimeter))

enum class Hand {
    Rock, Paper, Scissor, Unknown;

    companion object {
        fun fromString(string: String): Hand =
            when (string) {
                "A", "X" -> Rock
                "B", "Y" -> Paper
                "C", "Z" -> Scissor
                else -> Unknown
            }
    }
}

enum class Play(val play: Hand, val wins: Hand, val loses: Hand) {
    Rock(Hand.Rock, wins = Hand.Scissor, loses = Hand.Paper),
    Paper(Hand.Paper, wins = Hand.Rock, loses = Hand.Scissor),
    Scissor(Hand.Scissor, wins = Hand.Paper, loses = Hand.Rock),
    Unknown(Hand.Unknown, wins = Hand.Unknown, loses = Hand.Unknown);

    fun compare(other: Play): Int =
        when (other.play) {
            wins -> 6
            loses -> 0
            play -> 3
            else -> 0
        }

    companion object {
        fun fromHand(play: Hand): Play =
            when (play) {
                Hand.Rock -> Rock
                Hand.Paper -> Paper
                Hand.Scissor -> Scissor
                Hand.Unknown -> Unknown
            }
    }
}

enum class Result {
    Win, Lose, Draw, Unknown;

    companion object {
        fun fromString(string: String): Result =
            when(string) {
                "X" -> Lose
                "Y" -> Draw
                "Z" -> Win
                else -> Unknown
            }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val rounds: List<Pair<Hand, Hand>> =
            input
                .map { it.splitToPair() }
                .map { Pair(Hand.fromString(it.first), Hand.fromString(it.second)) }

        val totalScore: Int = rounds.fold(0) { acc, pair ->
            val (opponent, me) = pair
            // Score for my play
            val playScore: Int = when (me) {
                Hand.Rock -> 1
                Hand.Paper -> 2
                Hand.Scissor -> 3
                Hand.Unknown -> 0
            }

            val opponentPlay = Play.fromHand(opponent)
            val myPlay = Play.fromHand(me)
            val roundScore: Int = myPlay.compare(opponentPlay)

            acc + playScore + roundScore
        }

        return totalScore
    }

    fun part2(input: List<String>): Int {
        val rounds: List<Pair<Hand, Result>> =
            input
                .map { it.splitToPair() }
                .map { Pair(Hand.fromString(it.first), Result.fromString(it.second)) }

        val totalScore: Int = rounds.fold(0) { acc, (opponent, expectedResult) ->
            val opponentPlay = Play.fromHand(opponent)
            val me: Hand = when (expectedResult) {
                Result.Draw -> opponent
                Result.Win -> opponentPlay.loses
                Result.Lose -> opponentPlay.wins
                else -> opponentPlay.wins
            }

            val playScore: Int = when (me) {
                Hand.Rock -> 1
                Hand.Paper -> 2
                Hand.Scissor -> 3
                Hand.Unknown -> 0
            }

            val myPlay = Play.fromHand(me)

            val roundScore: Int = myPlay.compare(opponentPlay)

            acc + playScore + roundScore
        }

        return totalScore
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
