package solve.pass

import model.board.Board
import model.board.HouseType
import model.cell.Cell
import model.cell.SubCell
import model.cell.ValueCell
import mu.KotlinLogging
import solve.EachHouseSolvePass
import util.filterIsInstanceIndexed
import util.oneToNine
import util.toIndexed

typealias Histogram = Map<Int, List<Int>>

private val logger = KotlinLogging.logger {}

class HiddenSingle(
  type: HouseType,
  initialBoard: Board,
) : EachHouseSolvePass(type, initialBoard) {
  override fun prepare() {
    logger.info { "Looking for hidden-single cells in ${type.toString().lowercase()}s" }
  }

  override fun transformHouse(house: List<Cell>): List<Cell> {
    val histogram = histogram(house)
    val newCells = house.toMutableList()

    histogram
      .entries
      .filter { it.value.size == 1 }
      .forEach { (number, list) ->
        val (cell) = list
        newCells[cell] = Cell.Single(number)
      }

    return newCells
  }

  @Suppress("UNCHECKED_CAST")
  private fun histogram(house: List<Cell>): Histogram {
    return oneToNine
      .associateWith { number ->
        house
          .toIndexed()
          .filterIsInstanceIndexed<Cell.Multi>()
          .filter { cell ->
            cell
              .value
              .getSubCell(number)
              .let { it is ValueCell && it !is SubCell.CrossedOut }
          }.map { it.index }
      }
  }

  companion object {
    fun rows(initialBoard: Board) =
      HiddenSingle(HouseType.Row, initialBoard)

    fun columns(initialBoard: Board) =
      HiddenSingle(HouseType.Column, initialBoard)

    fun blocks(initialBoard: Board) =
      HiddenSingle(HouseType.Block, initialBoard)
  }
}
