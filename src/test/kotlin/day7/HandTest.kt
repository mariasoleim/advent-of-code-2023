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
}