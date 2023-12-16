package day16

import day1.readFileAsLinesUsingUseLines
import java.lang.Exception
import java.lang.IndexOutOfBoundsException


private fun getNewDirections(
    tile: Tile,
    directionIntoThisTile: Direction
) = when (tile.tileType) {
    TileType.EMPTY_SPACE -> listOf(directionIntoThisTile)
    TileType.MIRROR_LEANING_RIGHT ->
        when (directionIntoThisTile) {
            Direction.RIGHT -> listOf(Direction.UP)
            Direction.DOWN -> listOf(Direction.LEFT)
            Direction.LEFT -> listOf(Direction.DOWN)
            Direction.UP -> listOf(Direction.RIGHT)
        }

    TileType.MIRROR_LEANING_LEFT ->
        when (directionIntoThisTile) {
            Direction.RIGHT -> listOf(Direction.DOWN)
            Direction.DOWN -> listOf(Direction.RIGHT)
            Direction.LEFT -> listOf(Direction.UP)
            Direction.UP -> listOf(Direction.LEFT)
        }

    TileType.SPLITTED_VERTICAL ->
        when (directionIntoThisTile) {
            Direction.RIGHT, Direction.LEFT -> listOf(Direction.DOWN, Direction.UP)
            Direction.DOWN, Direction.UP -> listOf(directionIntoThisTile)
        }

    TileType.SPLITTED_HORIZONTAL ->
        when (directionIntoThisTile) {
            Direction.RIGHT, Direction.LEFT -> listOf(directionIntoThisTile)
            Direction.DOWN, Direction.UP -> listOf(Direction.RIGHT, Direction.LEFT)
        }
}

data class Grid(val rows: List<MutableList<Tile>>) {

    private fun energizeTile(tile: Tile) {
        val energized = rows[tile.row][tile.col].energize()
        rows[tile.row][tile.col] = energized
    }

    private fun goInDirection(tile: Tile, direction: Direction): Tile? {
        return try {
            when (direction) {
                Direction.RIGHT -> rows[tile.row][tile.col + 1]
                Direction.DOWN -> rows[tile.row + 1][tile.col]
                Direction.LEFT -> rows[tile.row][tile.col - 1]
                Direction.UP -> rows[tile.row - 1][tile.col]
            }
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }

    fun moveLight(movement: Movement, history: MutableList<Movement> = mutableListOf()) {

        val goingInCircle = history.any { it.matches(movement) }
        if (goingInCircle) return

        val tile = movement.intoTile

        energizeTile(tile)

        val newDirections = getNewDirections(tile, movement.directionIntoTile)
        val newTiles = newDirections.map { goInDirection(tile, it) }
        newTiles.forEachIndexed { index, newTile ->
            if (newTile != null) {
                val newDirection = newDirections[index]
                history.add(movement)
                moveLight(Movement(newTile, newDirection), history)
            }
        }
    }

    fun countEnergized(): Int {
        return rows.sumOf { row -> row.count { it.energized } }
    }

    companion object {
        fun fromFile(filePath: String): Grid {
            return Grid(
                readFileAsLinesUsingUseLines(filePath).mapIndexed { indexRow, line ->
                    line.mapIndexed { indexCol, it -> Tile.fromChar(indexRow, indexCol, it) }
                        .toMutableList()
                }
            )
        }
    }
}

data class Movement(val intoTile: Tile, val directionIntoTile: Direction) {
    fun matches(other: Movement): Boolean {
        return intoTile.row == other.intoTile.row
            && intoTile.col == other.intoTile.col
            && directionIntoTile == other.directionIntoTile
    }
}

data class Tile(val row: Int, val col: Int, val char: Char, val tileType: TileType, var energized: Boolean = false) {

    fun energize(): Tile {
        return Tile(row, col, char, tileType, true)
    }

    companion object {
        fun fromChar(row: Int, col: Int, char: Char): Tile {
            return Tile(row, col, char, TileType.fromChar(char))
        }
    }
}

enum class TileType {
    EMPTY_SPACE,
    MIRROR_LEANING_RIGHT,
    MIRROR_LEANING_LEFT,
    SPLITTED_VERTICAL,
    SPLITTED_HORIZONTAL;

    companion object {
        fun fromChar(char: Char): TileType {
            return when (char) {
                '.' -> EMPTY_SPACE
                '/' -> MIRROR_LEANING_RIGHT
                '\\' -> MIRROR_LEANING_LEFT
                '|' -> SPLITTED_VERTICAL
                '-' -> SPLITTED_HORIZONTAL
                else -> throw Exception("Tile type $char not supported")
            }
        }
    }

}

enum class Direction {
    RIGHT, DOWN, LEFT, UP
}

fun part1(filePath: String): Int {
    val grid = Grid.fromFile(filePath)
    grid.moveLight(Movement(grid.rows[0][0], Direction.RIGHT))
    return grid.countEnergized()
}

fun part2(filePath: String): Int {
    val grid = Grid.fromFile(filePath)
    val fromTopRow = grid.rows[0].indices.map { Movement(grid.rows[0][it], Direction.DOWN) }
    val fromBottomRow = grid.rows[0].indices.map { Movement(grid.rows[grid.rows[0].size - 1][it], Direction.UP) }
    val fromLeftCol = grid.rows.indices.map { Movement(grid.rows[it][0], Direction.RIGHT) }
    val fromRightCol = grid.rows.indices.map { Movement(grid.rows[it][grid.rows.size - 1], Direction.LEFT) }
    val allStartMovements = fromTopRow + fromBottomRow + fromLeftCol + fromRightCol
    return allStartMovements.maxOf {
        val gridForThisMovement = Grid.fromFile(filePath)
        gridForThisMovement.moveLight(it)
        gridForThisMovement.countEnergized()
    }
}

fun main() {
    val testResult = part1("./src/main/kotlin/day16/input-test.txt")
    println("Task 1 test result: $testResult")

    val result = part1("./src/main/kotlin/day16/input.txt")
    println("Task 1 result: $result")

    val testResult2 = part2("./src/main/kotlin/day16/input-test.txt")
    println("Task 2 test result: $testResult2")

    val result2 = part2("./src/main/kotlin/day16/input.txt")
    println("Task 2 result: $result2")
}
