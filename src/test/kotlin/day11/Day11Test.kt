package day11

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day11Test {

    @Test
    fun getEmptyRowsAndCols() {
        val image = Image.fromFile("./src/main/kotlin/day11/input-test.txt")
        val emptyRows = image.getEmptyRows()
        val emptyCols = image.getEmptyCols()
        assertEquals(listOf(3, 7), emptyRows)
        assertEquals(listOf(2, 5, 8), emptyCols)
    }
}