package solve.pass

import model.board.Board
import model.board.HouseType
import model.cell.Cell
import model.cell.SubCell
import model.cell.ValueCell
import mu.KotlinLogging
import solve.EachHouseSolvePass
import solve.SolvePassBase
import solve.engine.SolveStep
import util.Indexed
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

  override fun transformHouse(cells: List<Cell>): List<Cell> {
    val histogram = histogram(cells)
    val newCells = cells.toMutableList()

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
    val multis = house
      .toIndexed()
      .filter { it.value is Cell.Multi } as List<Indexed<Cell.Multi>>

    return oneToNine
      .associateWith { number ->
        multis
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
