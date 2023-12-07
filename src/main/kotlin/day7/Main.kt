package day7

import day1.readFileAsLinesUsingUseLines
import java.lang.Exception


fun task1(filePath: String): Int {
    return readFileAsLinesUsingUseLines(filePath)
        .map { Hand.fromString(it) }
        .sorted()
        .mapIndexed { index, hand -> (index + 1) * hand.bid }
        .sum()
}

fun task2(filePath: String): Int {
    return readFileAsLinesUsingUseLines(filePath)
        .map { Hand.fromString(it, true) }
        .sorted()
        .mapIndexed { index, hand -> (index + 1) * hand.bid }
        .sum()
}


data class Hand(val cards: List<Card>, val bid: Int, val useJoker: Boolean = false) : Comparable<Hand> {

    fun getType(): Type {
        var cardsByNumberOfLabel = cards.groupBy { it.label }.map { it.key to it.value.size }.toMutableList()
        if (useJoker) {
            val numberOfJokers = cardsByNumberOfLabel.find { it.first == Label.J }?.second ?: 0
            val labelOfMostCards = cardsByNumberOfLabel
                .filter { it.first != Label.J }
                .maxByOrNull { it.second }
            if (labelOfMostCards != null) {
                val newValue = labelOfMostCards.second + numberOfJokers
                cardsByNumberOfLabel = cardsByNumberOfLabel
                    .filter { it.first != Label.J }.toMutableList()
                    .filter { it.first != labelOfMostCards.first }.toMutableList()
                cardsByNumberOfLabel.add(Pair(labelOfMostCards.first, newValue))
            }
        }
        if (cardsByNumberOfLabel.any { it.second == 5 }) return Type.FIVE_OF_A_KIND
        if (cardsByNumberOfLabel.any { it.second == 4 }) return Type.FOUR_OF_A_KIND
        if (cardsByNumberOfLabel.any { it.second == 3 } && cardsByNumberOfLabel.any { it.second == 2 }) return Type.FULL_HOUSE
        if (cardsByNumberOfLabel.any { it.second == 3 }) return Type.THREE_OF_A_KIND
        if (cardsByNumberOfLabel.filter { it.second == 2 }.size == 2) return Type.TWO_PAIR
        return if (cardsByNumberOfLabel.any { it.second == 2 }) Type.ONE_PAIR
        else Type.HIGH_CARD
    }

    override fun compareTo(other: Hand): Int {
        val type = getType()
        val otherType = other.getType()
        if (type != otherType) return type.ordinal - otherType.ordinal
        cards.forEachIndexed { index, card ->
            val label = card.label
            val otherLabel = other.cards[index].label
            if (label != otherLabel) {
                if (useJoker) {
                    if (label == Label.J) {
                        return -1
                    }
                    if (otherLabel == Label.J) {
                        return 1
                    }
                }
                return label.ordinal - otherLabel.ordinal
            }
        }
        return 0
    }

    companion object {
        fun fromString(string: String, useJoker: Boolean = false): Hand {
            try {
                val asList = string.split(" ")
                val cards = asList.first().map { Card.fromChar(it) }
                val bid = asList.last().toInt()
                return Hand(cards, bid, useJoker)
            } catch (e: Exception) {
                throw Exception("Could not convert file to task: $e")
            }
        }
    }
}

enum class Type {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND,
}
data class Card(val label: Label) {

    companion object {
        fun fromChar(char: Char): Card {
            return Card(Label.fromChar(char))
        }
    }
}

enum class Label {
    _2,
    _3,
    _4,
    _5,
    _6,
    _7,
    _8,
    _9,
    T,
    J,
    Q,
    K,
    A;

    companion object {
        fun fromChar(char: Char): Label {
            return if (char.isDigit()) {
                Label.valueOf("_$char")
            } else {
                Label.valueOf(char.toString())
            }
        }
    }
}

fun main() {
    val testResult = task1("./src/main/kotlin/day7/input-test.txt")
    println("Task 1 test result: $testResult")

    val result = task1("./src/main/kotlin/day7/input.txt")
    println("Task 1 result: $result")

    val testResult2 = task2("./src/main/kotlin/day7/input-test.txt")
    println("Task 2 test result: $testResult2")

    val result2 = task2("./src/main/kotlin/day7/input.txt")
    println("Task 2 result: $result2")
}
