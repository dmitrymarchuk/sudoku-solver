package model

import model.ui.Cell
import util.groupBy9
import util.quadrant
import util.rotate

class Board private constructor(cells: List<Cell>) : List<Cell> by cells {
  init {
    assert(cells.size == 9 * 9)
  }

  val rows = cells.groupBy9()
  val columns = rows.rotate()
  val houses = (0 until 9).map(cells::quadrant)

  val rowBlocks = rows.map(Block::fromCells)
  val columnBlocks = columns.map(Block::fromCells)
  val houseBlocks = houses.map(Block::fromCells)

  val isSolved: Boolean
    get() {
      val row = rowBlocks.all { it.hasAllNumbers }
      val column = columnBlocks.all { it.hasAllNumbers }
      val house = houseBlocks.all { it.hasAllNumbers }
      return row && column && house
    }

  companion object {
    fun fromString(str: String): Board {
      assert(str.length == 9 * 9)
      return Board(str
        .map { it.digitToInt() }
        .map { if (it == 0) Cell.Empty else Cell.Value.Single(it) })
    }
  }
}


