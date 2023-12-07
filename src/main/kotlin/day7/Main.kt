package day7

import day1.readFileAsLinesUsingUseLines
import java.lang.Exception


fun task1(filePath: String): Int {
    val hands = readFileAsLinesUsingUseLines(filePath).map { Hand.fromString(it) }
    val sortedHands = hands.sorted()
    val result = sortedHands.mapIndexed { index, hand ->  (index + 1) * hand.bid}.sum()
    return result
}

fun task2(): Int {
    return 0
}


data class Hand(val cards: List<Card>, val bid: Int) : Comparable<Hand> {

    fun getType(): Type {
        val cardsByNumberOfLabel = cards.groupBy { it.label }.map { it.key to it.value.size }
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
            if (label != otherLabel) return label.ordinal - otherLabel.ordinal
        }
        return 0
    }

    companion object {
        fun fromString(string: String): Hand {
            try {
                val asList = string.split(" ")
                val cards = asList.first().map { Card.fromChar(it) }
                val bid = asList.last().toInt()
                return Hand(cards, bid)
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
}
