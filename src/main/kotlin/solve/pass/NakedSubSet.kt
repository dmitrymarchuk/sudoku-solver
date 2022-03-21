package solve.pass

import com.github.shiguruikai.combinatoricskt.combinations
import model.board.Board
import model.board.HouseType
import model.cell.Cell
import model.cell.SubCell
import model.cell.ValueCell
import mu.KotlinLogging
import solve.EachHouseSolvePass
import util.filterIsInstanceIndexed
import util.toIndexed

private val logger = KotlinLogging.logger {}

class NakedSubSet(
  type: HouseType,
  private val size: Int,
  initialBoard: Board,
) : EachHouseSolvePass(type, initialBoard) {
  init {
    size in 2..4
  }

  override fun prepare() {
    logger.info {
      "Looking for naked-single cell $size-size sets " +
          "in ${type.toString().lowercase()}s"
    }
  }

  override fun transformHouse(house: List<Cell>) =
    searchTransformCombinations(house)

  private fun searchTransformCombinations(house: List<Cell>): List<Cell> {
    house
      .toIndexed()
      .filterIsInstanceIndexed<Cell.Multi>()
      .combinations(size)
      .forEach { combination ->
        val possibleVariants =
          combination
            .map { it.value.possible }
            .flatten()
            .map { it.value }
            .toSet()
        if (combination.size == possibleVariants.size) {
          return crossOutAllOthers(
            house,
            possibleVariants,
            combination.map { it.index }.toSet())
        }
      }
    return house
  }

  private fun crossOutAllOthers(
    cells: List<Cell>,
    toCrossOut: Set<Int>,
    nakedSetIndexes: Set<Int>,
  ): List<Cell> {
    val newCells = cells.toMutableList()
    cells.forEachIndexed { index, cell ->
      if (index !in nakedSetIndexes && cell is Cell.Multi) {
        newCells[index] = crossOutCell(cell, toCrossOut)
      }
    }

    return newCells
  }

  private fun crossOutCell(cell: Cell.Multi, toCrossOut: Set<Int>): Cell.Multi =
    toCrossOut.fold(cell) { acc, number ->
      if (acc.getSubCell(number) is ValueCell)
        acc.setSubCell(number, SubCell.CrossedOut(number))
      else
        acc
    }
}
