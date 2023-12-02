package day2

import day1.readFileAsLinesUsingUseLines

fun main() {
    val testResult = task1("./src/main/kotlin/day2/input-test.txt")
    println("Task 1 test result: $testResult")

    val result = task1("./src/main/kotlin/day2/input.txt")
    println("Task 1 result: $result")
//
//    val testResult2 = task2("./src/main/kotlin/day2/input-task2-test.txt")
//    println("Task 2 test result: $testResult2")
//
//    val result2 = task2("./src/main/kotlin/day2/input.txt")
//    println("Task 2 result: $result2")
}

fun task1(filePath: String): Int {
    val games = readFileAsLinesUsingUseLines(filePath).map { Game.fromFile(it) }
    val maximumNumberOfCubes = mapOf(
        Color.RED to 12,
        Color.GREEN to 13,
        Color.BLUE to 14
    )
    return games.filter { it.isPossible(maximumNumberOfCubes) }.sumOf { it.id }
}

fun task2(filePath: String): Int {
    return 0
}

data class Game(val id: Int, val subsets: List<Subset>) {

    fun isPossible(maximumNumberOfCubes: Map<Color, Int>): Boolean {
        return subsets.all { it.isPossible(maximumNumberOfCubes) }
    }

    companion object {
        fun fromFile(string: String): Game {
            val (gameIdPart, subsetsPart) = string.split(":")
            val id = gameIdPart.split(" ")[1].toInt()
            val subsets = subsetsPart.trim().split(";").map { Subset.fromFile(it) }
            return Game(id, subsets)
        }
    }
}



data class Subset(val subsetColors: List<SubsetColor>) {

    fun isPossible(maximumNumberOfCubes: Map<Color, Int>): Boolean {
        return subsetColors.all { it.isPossible(maximumNumberOfCubes) }
    }

    companion object {
        fun fromFile(string: String): Subset {
            return Subset(string.split(",").map { SubsetColor.fromFile(it.trim()) })
        }
    }
}

data class SubsetColor(val color: Color, val numberOfCubes: Int) {

    fun isPossible(maximumNumberOfCubes: Map<Color, Int>): Boolean {
        return numberOfCubes <= maximumNumberOfCubes[color]!!
    }

    companion object {
        fun fromFile(string: String): SubsetColor {
            val (numberOfCubesFromFile, colorFromFile) = string.split(" ")
            val numberOfCubes = numberOfCubesFromFile.toInt()
            val color = Color.valueOf(colorFromFile.uppercase())
            return SubsetColor(color, numberOfCubes)
        }
    }
}

enum class Color {
    RED,
    GREEN,
    BLUE
}
