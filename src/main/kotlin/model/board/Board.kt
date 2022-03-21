package model.board

import model.cell.Cell
import mu.KotlinLogging
import util.assertNineSq
import util.quadrantsToRows
import util.rotateCCW

private val logger = KotlinLogging.logger {}

interface Board : List<Cell>, BoardVisitor {
  fun houses(type: HouseType): List<House>
  fun houseSets(type: HouseType): List<NumbersSet>

  fun setCell(index: Int, cell: Cell): Board

  val isSolved: Boolean

  companion object {
    fun getHouseIndexForCellIndex(type: HouseType, index: Int): Int {
      val row = index / 9
      val col = index - (row * 9)
      val boxRow = row / 3
      val boxCol = col / 3
      val block = (boxRow * 3) + boxCol
      return when (type) {
        HouseType.Row    -> row
        HouseType.Column -> col
        HouseType.Block  -> block
      }
    }

    fun fromHouse(type: HouseType, houses: List<House>) =
      when (type) {
        HouseType.Row    -> BoardImpl(houses.flatten())
        HouseType.Column -> BoardImpl(houses.rotateCCW().flatten())
        HouseType.Block  -> BoardImpl(houses.quadrantsToRows().flatten())
      }

    fun fromString(str: String): Board {
      logger.info { "Loading board $str" }
      str.length.assertNineSq()
      return BoardImpl(str.map(Cell::fromChar))
    }
  }
}
