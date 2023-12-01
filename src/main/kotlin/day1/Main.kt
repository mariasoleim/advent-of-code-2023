package day1

import java.io.File

fun main(args: Array<String>) {
    val testResult = task1("./src/main/kotlin/day1/input-test.txt")
    println("Task 1 test result: $testResult")

    val result = task1("./src/main/kotlin/day1/input.txt")
    println("Task 1 result: $result")

    val testResult2 = task2("./src/main/kotlin/day1/input-task2-test.txt")
    println("Task 2 test result: $testResult2")

    val result2 = task2("./src/main/kotlin/day1/input.txt")
    println("Task 2 result: $result2")
}

fun task1(filePath: String): Int {
    val lines = readFileAsLinesUsingUseLines(filePath)
    val numbers = lines.map { getFirstAndLastNumber(it) }
    return numbers.sumOf { it.toInt() }
}

fun task2(filePath: String): Int {
    val lines = readFileAsLinesUsingUseLines(filePath)
    val numbers = lines.map { getFirstAndLastNumberPart2(it) }
    return numbers.sumOf { it.toInt() }
}

fun getFirstAndLastNumber(string: String): String {
    val first = string.find { it.isDigit() }
    val last = string.findLast { it.isDigit() }
    return "$first$last"
}

fun getFirstAndLastNumberPart2(string: String): String {
    val lengthOfString = string.length
    var first = ""
    var last = ""
    for (i in 0 until lengthOfString) {
        val substring = string.substring(i, lengthOfString)
        if (stringStartsWithDigit(substring)) {
            first = getDigitFromString(substring)
            break
        }
    }
    for (i in lengthOfString - 1 downTo 0) {
        val substring = string.substring(i, lengthOfString)
        if (stringStartsWithDigit(substring)) {
            last = getDigitFromString(substring)
            break
        }
    }
    return "$first$last"
}

fun stringStartsWithDigit(string: String): Boolean {
    if (string.first().isDigit()) return true
    if (string.startsWith("one")) return true
    if (string.startsWith("two")) return true
    if (string.startsWith("three")) return true
    if (string.startsWith("four")) return true
    if (string.startsWith("five")) return true
    if (string.startsWith("six")) return true
    if (string.startsWith("seven")) return true
    if (string.startsWith("eight")) return true
    if (string.startsWith("nine")) return true
    return false
}

fun getDigitFromString(string: String): String {
    if (string.first().isDigit()) return string.first().toString()
    if (string.startsWith("one")) return "1"
    if (string.startsWith("two")) return "2"
    if (string.startsWith("three")) return "3"
    if (string.startsWith("four")) return "4"
    if (string.startsWith("five")) return "5"
    if (string.startsWith("six")) return "6"
    if (string.startsWith("seven")) return "7"
    if (string.startsWith("eight")) return "8"
    if (string.startsWith("nine")) return "9"
    throw Exception("Not a spelled out digit: $string")
}

fun spelledOutDigitToDigit(string: String): Int {
    return when (string) {
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        else -> throw Exception("Not a spelled out digit: $string")
    }
}

fun readFileAsLinesUsingUseLines(fileName: String): List<String>
    = File(fileName).useLines { it.toList() }