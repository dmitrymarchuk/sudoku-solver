package model.board

import model.cell.Cell
import mu.KotlinLogging
import util.assertNineSq
import util.quadrantsToRows
import util.rotateCCW

private val logger = KotlinLogging.logger {}

interface Board : List<Cell>, BoardVisitor {
  fun house(type: HouseType): List<List<Cell>>
  fun houseSet(type: HouseType): List<NumbersSet>

  fun setCell(index: Int, cell: Cell): Board

  val isSolved: Boolean

  companion object {
    fun fromHouse(type: HouseType, houses: List<List<Cell>>): Board {
      return when (type) {
        HouseType.Row    -> BoardImpl(houses.flatten())
        HouseType.Column -> fromHouse(HouseType.Row, houses.rotateCCW())
        HouseType.Block  -> fromHouse(HouseType.Row, houses.quadrantsToRows())
      }
    }

    fun fromString(str: String): Board {
      logger.info { "Loading board $str" }
      str.length.assertNineSq()
      return BoardImpl(
        str
          .replace('.', '0')
          .map { it.digitToInt() }
          .map { if (it == 0) Cell.Empty else Cell.Single(it) })
    }
  }
}
