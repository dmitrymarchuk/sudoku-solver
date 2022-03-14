package model

import util.groupBy9
import util.quadrant
import util.transpose

class Board(cells: List<Int>) : List<Int> by cells {
  val rows = cells.groupBy9()
  val columns = rows.transpose()

  val rowBlocks = rows.map(Block::fromList)
  val columnBlocks = rows.map(Block::fromList)
  val houseBlocks = (0 until 9).map(cells::quadrant)
}


