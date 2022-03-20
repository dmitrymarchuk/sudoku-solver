package model.board

import model.cell.Cell
import mu.KotlinLogging
import util.assertNineSq
import util.groupBy9
import util.replace
import util.rotateCW
import util.rowsToQuadrants

private val logger = KotlinLogging.logger {}

class BoardImpl(private val cells: List<Cell>) : Board, List<Cell> by cells {
  init {
    cells.size.assertNineSq()
  }

  val rows = cells.groupBy9()
  val cols = rows.rotateCW()
  val blocks = rows.rowsToQuadrants();

  val rowSets = rows.map(NumbersSet.Companion::fromCells)
  val colSets = cols.map(NumbersSet.Companion::fromCells)
  val blockSets = blocks.map(NumbersSet.Companion::fromCells)

  override fun house(type: HouseType) = when (type) {
    HouseType.Row    -> rows
    HouseType.Column -> cols
    HouseType.Block  -> blocks
  }

  override fun houseSet(type: HouseType) = when (type) {
    HouseType.Row    -> rowSets
    HouseType.Column -> colSets
    HouseType.Block  -> blockSets
  }

  init {
    logger.debug {
      "Board created.\n" + "\t${this::rowSets.name}=${rowSets}" + "\t${this::colSets.name}=${rowSets}" + "\t${this::blockSets.name}=${blockSets}"
    }
  }

  override val isSolved by lazy {
    val row = rowSets.all { it.hasAllNumbers }
    val col = colSets.all { it.hasAllNumbers }
    val house = blockSets.all { it.hasAllNumbers }
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

  fun getVisitorHouseIndexes(index: Int): Triple<Int, Int, Int> {
    val row = index / 9
    val col = index - (row * 9)
    val boxRow = row / 3
    val boxCol = col / 3
    val block = (boxRow * 3) + boxCol

    return Triple(row, col, block)
  }

  private fun getVisitorArgsForIndex(index: Int, cell: Cell): BoardVisitor.Args {
    val (row, col, block) = getVisitorHouseIndexes(index)
    val getHouseIndex = { type: HouseType ->
      when (type) {
        HouseType.Row    -> row
        HouseType.Column -> col
        HouseType.Block  -> block
      }
    }

    return object : BoardVisitor.Args {
      override val cell = cell
      override val index = index

      override fun set(type: HouseType) =
        this@BoardImpl.houseSet(type)[getHouseIndex(type)]

      override fun house(type: HouseType) =
        this@BoardImpl.house(type)[getHouseIndex(type)]

    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as BoardImpl

    if (cells != other.cells)
      return false

    return true
  }

  override fun hashCode(): Int {
    return cells.hashCode()
  }

}
