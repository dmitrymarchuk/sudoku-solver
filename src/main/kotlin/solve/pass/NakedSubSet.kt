package solve.pass

import solve.SolvePassFactory
import com.github.shiguruikai.combinatoricskt.combinations
import model.board.Board
import model.board.House
import model.board.HouseType
import model.cell.Cell
import model.cell.SubCell
import model.cell.ValueCell
import mu.KotlinLogging
import solve.EachHouseSolvePass

private val logger = KotlinLogging.logger {}

class NakedSubSet(
  type: HouseType,
  private val size: Int,
  initialBoard: Board,
) : EachHouseSolvePass(type, initialBoard) {
  init {
    size in 2..4
  }

  override fun transformHouse(house: House) =
    searchTransformCombinations(house)

  private fun searchTransformCombinations(house: House): House {
    house
      .multiCellsIndexed
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
    house: House,
    toCrossOut: Set<Int>,
    nakedSetIndexes: Set<Int>,
  ): House {
    val newCells = house.builder
    house.forEachIndexed { index, cell ->
      if (index !in nakedSetIndexes && cell is Cell.Multi) {
        newCells[index] = crossOutCell(cell, toCrossOut)
      }
    }

    return newCells.build()
  }

  private fun crossOutCell(cell: Cell.Multi, toCrossOut: Set<Int>): Cell.Multi =
    toCrossOut.fold(cell) { acc, number ->
      if (acc.getSubCell(number) is ValueCell)
        acc.setSubCell(number, SubCell.CrossedOut(number))
      else
        acc
    }

  companion object {
    fun factory(type: HouseType, size: Int) =
      SolvePassFactory("${NakedSubSet::class.simpleName}{$type, $size})") {
        NakedSubSet(type, size, it)
      }
  }
}
