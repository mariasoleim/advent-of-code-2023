package day1

import kotlin.test.Test
import kotlin.test.assertEquals

class MainKtTest {

    @Test
    fun `getFirstAndLastNumber`() {
        assertEquals("12", getFirstAndLastNumber("1abc2"))
        assertEquals("38", getFirstAndLastNumber("pqr3stu8vwx"))
        assertEquals("15", getFirstAndLastNumber("a1b2c3d4e5f"))
        assertEquals("77", getFirstAndLastNumber("treb7uchet"))
    }

    @Test
    fun `task1 test`() {
        assertEquals(142, task1("./src/main/kotlin/day1/input-test.txt"))
    }

    @Test
    fun `getFirstAndLastNumberPart2`() {
        assertEquals("29", getFirstAndLastNumberPart2("two1nine"))
        assertEquals("83", getFirstAndLastNumberPart2("eightwothree"))
        assertEquals("13", getFirstAndLastNumberPart2("abcone2threexyz"))
        assertEquals("24", getFirstAndLastNumberPart2("xtwone3four"))
        assertEquals("42", getFirstAndLastNumberPart2("4nineeightseven2"))
        assertEquals("14", getFirstAndLastNumberPart2("zoneight234"))
        assertEquals("76", getFirstAndLastNumberPart2("7pqrstsixteen"))
    }

    @Test
    fun `task2 test`() {
        assertEquals(281, task2("./src/main/kotlin/day1/input-task2-test.txt"))
    }
}
