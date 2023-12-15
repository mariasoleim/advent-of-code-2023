package day15

import java.io.File

fun hashChar(initialValue: Int, char: Char): Int {
    return ((initialValue + char.code) * 17) % 256
}

fun hashAlgorithm(string: String): Int {
    return string.fold(0) { acc, char -> hashChar(acc, char) }
}

data class Lens(val label: String, val operation: Operation, val focalLength: Int?) {

    companion object {
        fun fromString(string: String): Lens {
            val operation = if (string.indexOf('=') >= 0) Operation.ADD else Operation.REMOVE
            val split = string.split("=", "-")
            val label = split[0]
            val focalLength = split[1].takeIf { it.isNotEmpty() }?.toInt()
            return Lens(label, operation, focalLength)
        }
    }
}

enum class Operation {
    ADD, REMOVE
}

private fun orderInBoxes(lenses: List<Lens>): Map<Int, List<Lens>> {
    val boxes = (0..<256).associateWith { mutableListOf<Lens>() }
    lenses.forEach { lens ->
        val boxNumber = hashAlgorithm(lens.label)
        val box = boxes[boxNumber]!!
        val indexOfPreviousLens = box.indexOfFirst { it.label == lens.label }
        when (lens.operation) {
            Operation.ADD -> {
                if (indexOfPreviousLens != -1) box[indexOfPreviousLens] = lens else box.add(lens)
            }

            Operation.REMOVE -> {
                if (indexOfPreviousLens != -1) box.removeAt(indexOfPreviousLens)
            }
        }
    }
    return boxes
}

fun focusingPower(boxes: Map<Int, List<Lens>>): Int {
    return boxes.flatMap { entry ->
        val boxNumber = entry.key
        val lensesInBox = entry.value
        lensesInBox.mapIndexed { index, lens ->
            (boxNumber + 1) * (index + 1) * lens.focalLength!!
        }
    }.sum()
}

private fun parseFile(filePath: String) = File(filePath).readText().split(",").map { it.trim() }

fun task1(filePath: String): Int {
    val steps = parseFile(filePath)
    val hashes = steps.map { hashAlgorithm(it) }
    return hashes.sum()
}

fun task2(filePath: String): Int {
    val steps = parseFile(filePath)
    val lenses = steps.map { Lens.fromString(it) }
    val boxes = orderInBoxes(lenses)
    return focusingPower(boxes)
}

fun main() {
    val testResult = task1("./src/main/kotlin/day15/input-test.txt")
    println("Task 1 test result: $testResult")

    val result = task1("./src/main/kotlin/day15/input.txt")
    println("Task 1 result: $result")

    val testResult2 = task2("./src/main/kotlin/day15/input-test.txt")
    println("Task 2 test result: $testResult2")

    val result2 = task2("./src/main/kotlin/day15/input.txt")
    println("Task 2 result: $result2")
}
