package day3

import day1.readFileAsLinesUsingUseLines
import java.lang.IndexOutOfBoundsException


class Day3(filePath: String) {

    private val rows: List<String> = readFileAsLinesUsingUseLines(filePath)

    fun task1(): Int {
        return getSymbolLocations()
            .flatMap { getAdjacentCoordinatesWithDigits(it) }
            .map { getCoordinatesForFirstDigitInNumberAtCoordinate(it) }.toSet()
            .sumOf { getNumberBeginningAtCoordinate(it) }
    }

    fun task2(): Int {
        val gears = getSymbolLocations('*')
            .filter { getPartNumbers(it).size == 2 }
        return gears.sumOf { getGearRatio(it) }
    }

    private fun getGearRatio(gearLocation: Pair<Int, Int>): Int {
        val partNumbers = getPartNumbers(gearLocation)
        return partNumbers[0] * partNumbers[1]
    }

    private fun getPartNumbers(coordinate: Pair<Int, Int>): List<Int> {
        return getAdjacentCoordinatesWithDigits(coordinate)
            .map { getCoordinatesForFirstDigitInNumberAtCoordinate(it) }.toSet()
            .map { getNumberBeginningAtCoordinate(it) }
    }

    private fun getSymbolLocations(symbol: Char? = null): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        rows.forEachIndexed { indexRow, row -> run {
            row.forEachIndexed { indexColumn, column -> run {
                if (symbol != null) {
                    if (column == symbol) {
                        result.add(Pair(indexRow, indexColumn))
                    }
                }
                else if (!column.isDigit() && column != '.') {
                    result.add(Pair(indexRow, indexColumn))
                }
            } }
        } }
        return result
    }

    private fun getAdjacentCoordinatesWithDigits(symbol: Pair<Int, Int>): List<Pair<Int, Int>> {
        val i = symbol.first
        val j = symbol.second

        val adjacentPairs = listOf(
            Pair(i-1,j-1),
            Pair(i-1, j),
            Pair(i-1, j+1),
            Pair(i, j-1),
            Pair(i, j+1),
            Pair(i+1, j-1),
            Pair(i+1, j),
            Pair(i+1, j+1),
        )

        return adjacentPairs.filter {
            try {
                rows[it.first][it.second].isDigit()
            } catch (e: IndexOutOfBoundsException) {
                false
            }
        }
    }

    private fun getCoordinatesForFirstDigitInNumberAtCoordinate(pair: Pair<Int, Int>): Pair<Int, Int> {
        var col = pair.second
        while (col >= 0 && rows[pair.first][col].isDigit()) {
            col -= 1
        }
        return Pair(pair.first, col + 1)
    }

    private fun getNumberBeginningAtCoordinate(pair: Pair<Int, Int>): Int {
        val row = rows[pair.first]
        val rowFromFirstDigit = row.substring(pair.second)
        return rowFromFirstDigit.takeWhile { it.isDigit() }.toInt()
    }
}
fun main() {
    val testResult = Day3("./src/main/kotlin/day3/input-test.txt").task1()
    println("Task 1 test result: $testResult")

    val result = Day3("./src/main/kotlin/day3/input.txt").task1()
    println("Task 1 result: $result")

    val testResult2 = Day3("./src/main/kotlin/day3/input-test.txt").task2()
    println("Task 2 test result: $testResult2")

    val result2 = Day3("./src/main/kotlin/day3/input.txt").task2()
    println("Task 2 result: $result2")
}
