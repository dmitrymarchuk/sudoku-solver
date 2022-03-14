package model

import model.ui.BoardModel
import model.ui.Cell
import util.groupBy9
import util.quadrant
import util.rotate
import util.transpose

class Board private constructor(cells: List<Int>) : List<Int> by cells {
  init {
    assert(cells.size == 9 * 9)
  }

  val rows = cells.groupBy9()
  val columns = rows.rotate()
  val houses = (0 until 9).map(cells::quadrant)

  val rowBlocks = rows.map(Block::fromList)
  val columnBlocks = columns.map(Block::fromList)
  val houseBlocks = houses.map(Block::fromList)

  val isSolved: Boolean
    get() {
      val row = rowBlocks.all { it.hasAllNumbers }
      val column = columnBlocks.all { it.hasAllNumbers }
      val house = houseBlocks.all { it.hasAllNumbers }
      return row && column && house
    }

  val boardModel: BoardModel by lazy {
    BoardModel(houses.map { house -> house.map { if (it == 0) Cell.Empty else Cell.Value(it) } })
  }

  companion object {
    fun fromNumbers(cells: List<Int>): Board {
      return Board(cells)
    }

    fun fromString(str: String): Board {
      assert(str.length == 9 * 9)
      return fromNumbers(str.map { it.digitToInt() })
    }
  }
}


