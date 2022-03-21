package solve.pass

import SolvePassFactory
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

  override fun prepare() {
    logger.info {
      "Looking for naked-single cell $size-size sets " +
          "in ${type.toString().lowercase()}s"
    }
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
    fun factory(type: HouseType, size: Int): SolvePassFactory = { board: Board ->
      NakedSubSet(type, size, board)
    }

    fun rowPairs(board: Board) = NakedSubSet(HouseType.Row, 2, board)
    fun colPairs(board: Board) = NakedSubSet(HouseType.Column, 2, board)
    fun blockPairs(board: Board) = NakedSubSet(HouseType.Block, 2, board)
    fun rowTriples(board: Board) = NakedSubSet(HouseType.Row, 3, board)
    fun colTriples(board: Board) = NakedSubSet(HouseType.Column, 3, board)
    fun blockTriples(board: Board) = NakedSubSet(HouseType.Block, 3, board)
    fun rowQuadruples(board: Board) = NakedSubSet(HouseType.Row, 4, board)
    fun colQuadruples(board: Board) = NakedSubSet(HouseType.Column, 4, board)
    fun blockQuadruples(board: Board) = NakedSubSet(HouseType.Block, 4, board)
  }
}
