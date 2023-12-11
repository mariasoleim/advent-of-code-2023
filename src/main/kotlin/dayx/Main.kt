package dayx


class Dayy(filePath: String) {

    fun task1(): Int {
        return 0
    }

    fun task2(): Int {
        return 0
    }
}

fun main() {
    val testResult = Dayy("./src/main/kotlin/dayx/input-test.txt").task1()
    println("Task 1 test result: $testResult")

    val result = Dayy("./src/main/kotlin/dayx/input.txt").task1()
    println("Task 1 result: $result")

    val testResult2 = Dayy("./src/main/kotlin/dayx/input-test.txt").task2()
    println("Task 2 test result: $testResult2")

    val result2 = Dayy("./src/main/kotlin/dayx/input.txt").task2()
    println("Task 2 result: $result2")
}
