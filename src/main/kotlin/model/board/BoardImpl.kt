package model.board

import model.cell.Cell
import mu.KotlinLogging
import util.assertNineSq
import util.groupBy9
import util.quadrant
import util.replace
import util.rotate
import util.zeroUntilNine

private val logger = KotlinLogging.logger {}

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

  init {
    logger.debug {
      "Board created.\n" +
          "\t${this::rowSets.name}=${rowSets}" +
          "\t${this::colSets.name}=${rowSets}" +
          "\t${this::houseSets.name}=${houseSets}"
    }
  }

  override val isSolved by lazy {
    val row = rowSets.all { it.hasAllNumbers }
    val col = colSets.all { it.hasAllNumbers }
    val house = houseSets.all { it.hasAllNumbers }
    logger.debug { "Calculated isSolved for board $this: rows: $row, columns: $col, houses: $house" }
    row && col && house
  }

  override fun visitCells(visitor: (BoardVisitor.Args) -> Unit) {
    forEachIndexed { index, cell ->
      val args = getVisitorArgsForIndex(index, cell)
      logger.debug { "Visiting cell $args" }
      visitor(args)
    }
  }

  override fun setCell(index: Int, cell: Cell): Board {
    logger.debug { "Setting cell $cell at index $index" }
    return BoardImpl(cells.replace(index, cell))
  }

  fun getVisitorBlocksIndexes(index: Int): Triple<Int, Int, Int> {
    val row = index / 9
    val col = index - (row * 9)
    val boxRow = row / 3
    val boxCol = col / 3
    val house = (boxRow * 3) + boxCol

    return Triple(row, col, house)
  }

  fun getVisitorArgsForIndex(index: Int, cell: Cell): BoardVisitor.Args {
    val (row, col, house) = getVisitorBlocksIndexes(index)

    return BoardVisitor.Args(cell, index, rowSets[row], colSets[col], houseSets[house])
  }
}
