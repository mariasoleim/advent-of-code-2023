package day11

import day1.readFileAsLinesUsingUseLines
import kotlin.math.abs

data class Galaxy(val id: Int, val coordinates: Pair<Int, Int>) {

    fun getDistanceTo(other: Galaxy): Int {
        return abs(coordinates.first - other.coordinates.first) +
            abs(coordinates.second - other.coordinates.second)
    }
}
data class Image(val rows: List<List<Char>>) {

    fun getGalaxies(): List<Galaxy> {
        val galaxies = mutableListOf<Galaxy>()
        var galaxyId = 0
        rows.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, spaceItem ->
                if (spaceItem == '#') {
                    galaxies.add(Galaxy(galaxyId, Pair(rowIndex, colIndex)))
                }
            }
        }
        return galaxies.toList()
    }
    private fun expand(): Image {
        val expanded = mutableListOf<MutableList<Char>>()
        rows.forEach {
            expanded.add(it.toMutableList())
            if (it.all { spaceItem -> spaceItem == '.' }) {
                expanded.add(it.toMutableList())
            }
        }
        val colIndicesToExpand = mutableListOf<Int>()
        rows[0].forEachIndexed { colIndex, _ ->
            if (isColAllEmptySpace(colIndex)) {
                colIndicesToExpand.add(colIndex)
            }
        }
        colIndicesToExpand.reversed().forEach { colIndex ->
            expanded.forEach { row -> row.add(colIndex + 1, '.') }
        }
        return Image(expanded)
    }

    private fun isColAllEmptySpace(colIndex: Int): Boolean {
        return rows.all { row -> row[colIndex] == '.' }
    }

    companion object {
        fun fromFile(filePath: String): Image {
            val image = Image(
                readFileAsLinesUsingUseLines(filePath).map { row ->
                    row.map { spaceItem ->
                        spaceItem
                    }
                }
            )
            return image.expand()
        }
    }
}

fun task1(filePath: String): Int {
    val image = Image.fromFile(filePath)
    val galaxies = image.getGalaxies()
    var sumDistance = 0
    galaxies.forEachIndexed { firstIndex, galaxy ->
        galaxies.subList(firstIndex, galaxies.size).forEach { otherGalaxy ->
            val distance = galaxy.getDistanceTo(otherGalaxy)
            sumDistance += distance
        }
    }
    return sumDistance
}

fun task2(filePath: String): Int {
    return 0
}

fun main() {
    val testResult = task1("./src/main/kotlin/day11/input-test.txt")
    println("Task 1 test result: $testResult")

    val result = task1("./src/main/kotlin/day11/input.txt")
    println("Task 1 result: $result")

//    val testResult2 = task2("./src/main/kotlin/day11/input-test.txt")
//    println("Task 2 test result: $testResult2")
//
//    val result2 = task2("./src/main/kotlin/day11/input.txt")
//    println("Task 2 result: $result2")
}
