package day11

import day1.readFileAsLinesUsingUseLines

data class Galaxy(val coordinates: Pair<Int, Int>) {
}
data class Image(val rows: List<List<Char>>) {

    fun getGalaxies(): List<Galaxy> {
        val galaxies = mutableListOf<Galaxy>()
        rows.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, spaceItem ->
                if (spaceItem == '#') {
                    galaxies.add(Galaxy(Pair(rowIndex, colIndex)))
                }
            }
        }
        return galaxies.toList()
    }

    fun getEmptyRows(): List<Int> {
        return rows.mapIndexed { index, row ->
            if (row.all { it == '.' }) {
                index
            } else {
                null
            }
        }.filterNotNull()
    }

    fun getEmptyCols(): List<Int> {
        return rows.indices.mapNotNull { colIndex ->
            val emptyCol = rows.all { it[colIndex] == '.' }
            if (emptyCol) {
                colIndex
            } else {
                null
            }
        }
    }
    
    fun getDistanceBetweenGalaxies(
        galaxyA: Galaxy, 
        galaxyB: Galaxy,
        emptyRows: List<Int>,
        emptyCols: List<Int>,
        emptySize: Long
    ): Long {
        val galaxyARowIndex = galaxyA.coordinates.first
        val galaxyAColIndex = galaxyA.coordinates.second
        val galaxyBRowIndex = galaxyB.coordinates.first
        val galaxyBColIndex = galaxyB.coordinates.second
        val verticalRange = if (galaxyARowIndex < galaxyBRowIndex) (galaxyARowIndex + 1.. galaxyBRowIndex) else (galaxyARowIndex - 1 downTo  galaxyBRowIndex)
        val verticalDistance = verticalRange.sumOf {
            if (it in emptyRows) emptySize else 1
        }
        val horizontalRange = if (galaxyAColIndex < galaxyBColIndex) (galaxyAColIndex + 1 .. galaxyBColIndex) else (galaxyAColIndex - 1 downTo galaxyBColIndex)
        val horizontalDistance = horizontalRange.sumOf { 
            if (it in emptyCols) emptySize else 1
        }
        return verticalDistance + horizontalDistance
    }

    companion object {
        fun fromFile(filePath: String): Image {
            return Image(
                readFileAsLinesUsingUseLines(filePath).map { row ->
                    row.map { spaceItem ->
                        spaceItem
                    }
                }
            )
        }
    }
}

fun task(filePath: String, emptySpaceSize: Long): Long {
    val image = Image.fromFile(filePath)
    val emptyRows = image.getEmptyRows()
    val emptyCols = image.getEmptyCols()
    val galaxies = image.getGalaxies()
    var sumDistance: Long = 0
    galaxies.forEachIndexed { firstIndex, galaxy ->
        galaxies.subList(firstIndex, galaxies.size).forEach { otherGalaxy ->
            val distance = image.getDistanceBetweenGalaxies(galaxy, otherGalaxy, emptyRows, emptyCols, emptySpaceSize)
            sumDistance += distance
        }
    }
    return sumDistance
}

fun main() {
    val testResult = task("./src/main/kotlin/day11/input-test.txt", 2)
    println("Task 1 test result: $testResult")

    val result = task("./src/main/kotlin/day11/input.txt", 2)
    println("Task 1 result: $result")

    val testResult2Ten = task("./src/main/kotlin/day11/input-test.txt", 10)
    println("Task 2 test result with 10 as empty space size: $testResult2Ten")

    val testResult2Hundred = task("./src/main/kotlin/day11/input-test.txt", 100)
    println("Task 2 test result with 100 as empty space size: $testResult2Hundred")

    val result2 = task("./src/main/kotlin/day11/input.txt", 1000000)
    println("Task 2 result: $result2")
}
