package model

import model.interfaces.NumbersSet
import model.interfaces.Board
import model.interfaces.BoardVisitor
import model.ui.Cell
import util.assertNineSq
import util.groupBy9
import util.quadrant
import util.replace
import util.rotate
import util.zeroUntilNine

class BoardImpl(private val cells: List<Cell>) : Board, List<Cell> by cells {
  init {
    cells.size.assertNineSq()
  }

  override val rows = cells.groupBy9()
  override val columns = rows.rotate()
  override val houses = zeroUntilNine.map(cells::quadrant)

  override val rowSets = rows.map(NumbersSet.Companion::fromCells)
  override val colSets = columns.map(NumbersSet.Companion::fromCells)
  override val houseSets = houses.map(NumbersSet.Companion::fromCells)

  override val isSolved: Boolean
    get() {
      val row = rowSets.all { it.hasAllNumbers }
      val column = colSets.all { it.hasAllNumbers }
      val house = houseSets.all { it.hasAllNumbers }
      return row && column && house
    }

  override fun visitCells(visitor: (Board, BoardVisitor.Args) -> Board): Board {
    var board: Board = this
    forEachIndexed { index, cell ->
      board = visitor(board, getVisitorArgsForIndex(index, cell))
    }

    return board
  }

  override fun setCell(index: Int, cell: Cell): Board {
    return BoardImpl(cells.replace(index, cell))
  }

  fun getVisitorArgsForIndex(index: Int, cell: Cell): BoardVisitor.Args {
    val row = index / 9
    val col = index - (row * 9)
    val boxRow = row / 3
    val boxCol = col / 3
    val house = (boxRow * 3) + boxCol

    return BoardVisitor.Args(cell, index, rowSets[row], colSets[col], houseSets[house])
  }
}
