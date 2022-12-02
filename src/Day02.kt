private fun String.splitToPair(): Pair<String, String> =
    Pair(substringBefore(" "), substringAfter(" "))

enum class Hand(val score: Int) {
    Rock(1),
    Paper(2),
    Scissor(3),
    Unknown(0);

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

enum class Play(val hand: Hand, val wins: Hand, val loses: Hand) {
    Rock(Hand.Rock, wins = Hand.Scissor, loses = Hand.Paper),
    Paper(Hand.Paper, wins = Hand.Rock, loses = Hand.Scissor),
    Scissor(Hand.Scissor, wins = Hand.Paper, loses = Hand.Rock),
    Unknown(Hand.Unknown, wins = Hand.Unknown, loses = Hand.Unknown);

    fun compare(other: Play): Result =
        when (other.hand) {
            wins -> Result.Win
            loses -> Result.Lose
            hand -> Result.Draw
            else -> Result.Unknown
        }

    companion object {
        fun fromString(string: String): Play =
            fromHand(Hand.fromString(string))

        fun fromHand(play: Hand): Play =
            when (play) {
                Hand.Rock -> Rock
                Hand.Paper -> Paper
                Hand.Scissor -> Scissor
                Hand.Unknown -> Unknown
            }
    }
}

enum class Result(val score: Int) {
    Win(6),
    Lose(0),
    Draw(3),
    Unknown(0);

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
        val rounds: List<Pair<Play, Play>> =
            input
                .map(String::splitToPair)
                .map { Pair(Play.fromString(it.first), Play.fromString(it.second)) }

        return rounds.fold(0) { acc, (opponentPlay, myPlay) ->
            val roundResult: Result = myPlay.compare(opponentPlay)
            acc + roundResult.score + myPlay.hand.score
        }
    }

    fun part2(input: List<String>): Int {
        val rounds: List<Pair<Play, Result>> =
            input
                .map(String::splitToPair)
                .map { Pair(Play.fromString(it.first), Result.fromString(it.second)) }

        return rounds.fold(0) { acc, (opponentPlay, expectedResult) ->
            val myHand: Hand = when (expectedResult) {
                Result.Draw -> opponentPlay.hand
                Result.Win -> opponentPlay.loses
                Result.Lose -> opponentPlay.wins
                else -> opponentPlay.hand
            }

            acc + myHand.score + expectedResult.score
        }
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
