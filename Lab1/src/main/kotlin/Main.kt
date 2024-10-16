package org.example

import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    print("Enter the number of generations: ")
    val totalGenerations = scanner.nextInt()

    print("Enter the number of rows: ")
    val rows = scanner.nextInt()

    print("Enter the number of columns: ")
    val columns = scanner.nextInt()

    scanner.nextLine()

    println("Enter the initial state:")
    var board = Array(rows) {
        scanner.nextLine().toCharArray()
    }

    repeat(totalGenerations) { gen ->
        println("Generation ${gen + 1}:")
        board = board.computeNextGeneration()
        board.display()
    }

    scanner.close()
}

fun Array<CharArray>.computeNextGeneration(): Array<CharArray> {
    val rows = this.size
    val columns = this[0].size
    val newBoard = Array(rows) { CharArray(columns) }

    for (r in indices) {
        for (c in this[r].indices) {
            val activeNeighbors = countActiveNeighbors(r, c)

            newBoard[r][c] = when (this[r][c]) {
                'x' -> if (activeNeighbors in 2..3) 'x' else '.'
                else -> if (activeNeighbors == 3) 'x' else '.'
            }
        }
    }
    return newBoard
}

fun Array<CharArray>.countActiveNeighbors(row: Int, col: Int): Int {
    val rowOffsets = intArrayOf(-1, -1, -1, 0, 0, 1, 1, 1)
    val colOffsets = intArrayOf(-1, 0, 1, -1, 1, -1, 0, 1)
    var activeCount = 0
    val rows = this.size
    val columns = this[0].size

    for (i in rowOffsets.indices) {
        val neighborRow = (row + rowOffsets[i] + rows) % rows
        val neighborCol = (col + colOffsets[i] + columns) % columns
        if (this[neighborRow][neighborCol] == 'x') {
            activeCount++
        }
    }
    return activeCount
}

fun Array<CharArray>.display() {
    forEach { row ->
        println(row.joinToString(""))
    }
}