package solve.pass

import model.board.Board
import model.board.House
import model.board.HouseType
import model.cell.Cell
import model.cell.SubCell
import model.cell.ValueCell
import mu.KotlinLogging
import solve.EachHouseSolvePass
import solve.SolvePassFactory
import util.oneToNine

typealias Histogram = Map<Int, List<Int>>

private val logger = KotlinLogging.logger {}

class HiddenSingle(
  type: HouseType,
  initialBoard: Board,
) : EachHouseSolvePass(type, initialBoard) {
  override fun prepare() {
    logger.info { "Looking for hidden-single cells in ${type.toString().lowercase()}s" }
  }

  override fun transformHouse(house: House): House {
    val histogram = histogram(house)
    val newCells = house.builder

    histogram
      .entries
      .filter { it.value.size == 1 }
      .forEach { (number, list) ->
        val (cell) = list
        newCells[cell] = Cell.Single(number)
      }

    return newCells.build()
  }

  @Suppress("UNCHECKED_CAST")
  private fun histogram(house: House): Histogram {
    return oneToNine
      .associateWith { number ->
        house
          .multiCellsIndexed
          .filter { cell ->
            cell
              .value
              .getSubCell(number)
              .let { it is ValueCell && it !is SubCell.CrossedOut }
          }.map { it.index }
      }
  }

  companion object {
    fun factory(type: HouseType): SolvePassFactory =
      SolvePassFactory(HiddenSingle::class.simpleName!!) {
        HiddenSingle(type, it)
      }
  }
}
