package day7

import kotlin.test.Test
import kotlin.test.assertEquals

class HandTest {

    @Test
    fun compare() {
        val allA = Hand(listOf(
            Card(Label.A),
            Card(Label.A),
            Card(Label.A),
            Card(Label.A),
            Card(Label.A)
        ), 0)
        val allK = Hand(listOf(
            Card(Label.K),
            Card(Label.K),
            Card(Label.K),
            Card(Label.K),
            Card(Label.K)
        ), 0)
        val house = Hand(listOf(
            Card(Label.K),
            Card(Label.K),
            Card(Label.A),
            Card(Label.A),
            Card(Label.A)
        ), 0)
        val smallerHouse = Hand(listOf(
            Card(Label.Q),
            Card(Label.Q),
            Card(Label.A),
            Card(Label.A),
            Card(Label.A)
        ), 0)
        val hands = listOf(allA, allK, house, smallerHouse)
        val sorted = hands.sorted()
        assertEquals(smallerHouse, sorted[0])
        assertEquals(house, sorted[1])
        assertEquals(allK, sorted[2])
        assertEquals(allA, sorted[3])
    }

    @Test
    fun getType() {

        val fourOfAKindWithJoker = Hand(listOf(
            Card(Label.T),
            Card(Label._5),
            Card(Label._5),
            Card(Label.J),
            Card(Label._5)
        ), 0, true)

        val fourOfAKindWithTwoJokers = Hand(listOf(
            Card(Label.K),
            Card(Label.T),
            Card(Label.J),
            Card(Label.J),
            Card(Label.T)
        ), 0, true)

        assertEquals(Type.FOUR_OF_A_KIND, fourOfAKindWithJoker.getType())
        assertEquals(Type.FOUR_OF_A_KIND, fourOfAKindWithTwoJokers.getType())
    }

    @Test
    fun compareWithJoker() {

        val rank1 = Hand(listOf(
            Card(Label._3),
            Card(Label._2),
            Card(Label.T),
            Card(Label._3),
            Card(Label.K)
        ), 0, true)

        val rank3 = Hand(listOf(
            Card(Label.T),
            Card(Label._5),
            Card(Label._5),
            Card(Label.J),
            Card(Label._5)
        ), 0, true)

        val rank2 = Hand(listOf(
            Card(Label.K),
            Card(Label.K),
            Card(Label._6),
            Card(Label._7),
            Card(Label._7)
        ), 0, true)

        val rank5 = Hand(listOf(
            Card(Label.K),
            Card(Label.T),
            Card(Label.J),
            Card(Label.J),
            Card(Label.T)
        ), 0, true)

        val rank4 = Hand(listOf(
            Card(Label.Q),
            Card(Label.Q),
            Card(Label.Q),
            Card(Label.J),
            Card(Label.A)
        ), 0, true)

        val hands = listOf(rank1, rank2, rank3, rank4, rank5)
        val sorted = hands.sorted()
        assertEquals(rank1, sorted[0])
        assertEquals(rank2, sorted[1])
        assertEquals(rank3, sorted[2])
        assertEquals(rank4, sorted[3])
        assertEquals(rank5, sorted[4])
    }

    @Test
    fun compareWithJokerWithoutJoker() {

        val onePair = Hand(listOf(
            Card(Label._3),
            Card(Label._2),
            Card(Label.T),
            Card(Label._3),
            Card(Label.K)
        ), 0, true)

        val twoPairs = Hand(listOf(
            Card(Label.K),
            Card(Label.K),
            Card(Label._6),
            Card(Label._7),
            Card(Label._7)
        ), 0, true)

        assertEquals(Type.ONE_PAIR, onePair.getType())
        assertEquals(Type.TWO_PAIR, twoPairs.getType())
        val hands = listOf(onePair, twoPairs)
        val sorted = hands.sorted()
        assertEquals(onePair, sorted[0])
        assertEquals(twoPairs, sorted[1])
    }

    @Test
    fun getTypeWithMostJokers() {

        val fourOfAKind = Hand(listOf(
            Card(Label.J),
            Card(Label.J),
            Card(Label.J),
            Card(Label._3),
            Card(Label.K)
        ), 0, true)

        assertEquals(Type.FOUR_OF_A_KIND, fourOfAKind.getType())
    }

    @Test
    fun allJokers() {

        val allJokers = Hand(listOf(
            Card(Label.J),
            Card(Label.J),
            Card(Label.J),
            Card(Label.J),
            Card(Label.J)
        ), 0, true)

        assertEquals(Type.FIVE_OF_A_KIND, allJokers.getType())
    }

    @Test
    fun fourJokers() {

        val fourJokers = Hand(listOf(
            Card(Label.J),
            Card(Label.J),
            Card(Label.J),
            Card(Label.J),
            Card(Label._3)
        ), 0, true)

        assertEquals(Type.FIVE_OF_A_KIND, fourJokers.getType())
    }
}