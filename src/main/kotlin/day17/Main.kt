package main.kotlin.day17

import day1.readFileAsLinesUsingUseLines
import java.util.*

enum class Direction {
    RIGHT, DOWN, LEFT, UP;

    fun opposite(): Direction {
        return when (this) {
            RIGHT -> LEFT
            DOWN -> UP
            LEFT -> RIGHT
            UP -> DOWN
        }
    }
}

data class Pos(val row: Int, val col: Int)

data class Tile(val pos: Pos, val heatLoss: Int, val nodes: MutableList<Node> = mutableListOf()) {
    override fun hashCode() = Objects.hash(pos)
}

data class Node(
    val tile: Tile,
    val directionIntoTile: Direction? = null,
    val totalHeatLoss: Int = 0,
    val parentMove: Node? = null,
    val childrenMoves: MutableList<Node> = mutableListOf(),
    var shouldInvestigateFurther: Boolean = true
) {
    override fun toString(): String {
        return "Node(${tile.pos.row},${tile.pos.col})"
    }
    override fun hashCode() = Objects.hash(tile, directionIntoTile, totalHeatLoss, shouldInvestigateFurther)
}

data class Grid(val rows: List<List<Tile>>) {

    fun findShortestWay(): Int {
        val root = Node(rows[0][0])
        rows[0][0].nodes.add(root)
        var leaves = listOf(root)
        while (leaves.isNotEmpty()) {
            leaves = leaves
                .filter { it.shouldInvestigateFurther }
                .flatMap { buildNewLeaves(it) }
                .filter { it.shouldInvestigateFurther }
            leaves.forEach { if (isMachinePartsFactory(it.tile)) it.shouldInvestigateFurther = false  }
        }

        return rows.last().last().nodes.minOf { it.totalHeatLoss }
    }

    private fun removeUnnecessaryPaths(currentNode: Node) {

        val currentPos = currentNode.tile.pos
        val tile = rows[currentPos.row][currentPos.col]

        val nodesAtPos = tile.nodes

        nodesAtPos
            .filter { it != currentNode }
            .mapNotNull { getNodeNotWorthInvestigating(currentNode, it) }
            .toSet()
            .forEach {
                it.shouldInvestigateFurther = false
                it.parentMove?.parentMove?.childrenMoves?.remove(it.parentMove)
                tile.nodes.remove(it)
            }
    }

    private fun getLastDirectionAndNumberOfThatOne(node: Node): Pair<Direction?, Int> {
        val lastDirection = node.directionIntoTile
        val secondLastDirection = node.parentMove?.directionIntoTile
        val thirdLastDirection = node.parentMove?.parentMove?.directionIntoTile
        val numberOfEqualLastDirs =
            if (thirdLastDirection == secondLastDirection && secondLastDirection == lastDirection) {
                3
            } else if (secondLastDirection == lastDirection) {
                2
            } else {
                1
            }
        return Pair(lastDirection, numberOfEqualLastDirs)
    }

    private fun getNodeNotWorthInvestigating(first: Node, second: Node): Node? {
        val (subjectDirection, subjectNumberOfSteps) = getLastDirectionAndNumberOfThatOne(first)
        val (otherDirection, otherNumberOfSteps) = getLastDirectionAndNumberOfThatOne(second)

        if (subjectDirection == otherDirection &&
            subjectNumberOfSteps >= otherNumberOfSteps &&
            first.totalHeatLoss >= second.totalHeatLoss) {
            return first
        } else if (subjectDirection == otherDirection &&
            subjectNumberOfSteps <= otherNumberOfSteps &&
            first.totalHeatLoss <= second.totalHeatLoss) {
            return second
        }
        return null
    }

    private fun buildNewLeaves(parentNode: Node): List<Node> {
        val directions = getPossibleDirections(parentNode)
        return directions.mapNotNull { goInDirection(parentNode, it) }
    }

    private fun isMachinePartsFactory(tile: Tile): Boolean {
        return tile.pos.row == rows.size - 1 && tile.pos.col == rows[0].size - 1
    }

    private fun goInDirection(node: Node, direction: Direction): Node? {
        val tile = node.tile
        return try {
            val newTile = when (direction) {
                Direction.RIGHT -> rows[tile.pos.row][tile.pos.col + 1]
                Direction.DOWN -> rows[tile.pos.row + 1][tile.pos.col]
                Direction.LEFT -> rows[tile.pos.row][tile.pos.col - 1]
                Direction.UP -> rows[tile.pos.row - 1][tile.pos.col]
            }
            val newNode = Node(newTile, direction, node.totalHeatLoss + newTile.heatLoss, node)
            rows[newTile.pos.row][newTile.pos.col].nodes.add(newNode)
            node.childrenMoves.add(newNode)
            removeUnnecessaryPaths(newNode)
            return newNode
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }

    private fun getPossibleDirections(node: Node): List<Direction> {
        return Direction.entries
            .filter { it != node.directionIntoTile?.opposite() } // Not allowed to go the same way back
            .filterNot {
                it == node.directionIntoTile
                    && it == node.parentMove?.directionIntoTile
                    && it == node.parentMove.parentMove?.directionIntoTile
            }
    }

    companion object {
        fun fromFile(filePath: String): Grid {
            return Grid(readFileAsLinesUsingUseLines(filePath)
                .mapIndexed { rowIndex, line ->
                    line.mapIndexed { colIndex, value ->
                        Tile(Pos(rowIndex, colIndex), value.digitToInt())
                    }
                })
        }
    }
}

fun part1(filePath: String): Int {
    val grid = Grid.fromFile(filePath)
    return grid.findShortestWay()
}

fun part2(filePath: String): Int {
    return 0
}

fun main() {
    val testResult = part1("./src/main/kotlin/day17/input-test.txt")
    println("Task 1 test result: $testResult")

    val result = part1("./src/main/kotlin/day17/input.txt")
    println("Task 1 result: $result")

//    val testResult2 = part2("./src/main/kotlin/day17/input-test.txt")
//    println("Task 2 test result: $testResult2")
//
//    val result2 = part2("./src/main/kotlin/day17/input.txt")
//    println("Task 2 result: $result2")
}
