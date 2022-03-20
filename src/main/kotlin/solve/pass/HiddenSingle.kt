package solve.pass

import model.board.Board
import model.board.HouseType
import model.cell.Cell
import model.cell.SubCell
import model.cell.ValueCell
import solve.SolvePassBase
import solve.engine.SolveStep
import util.Indexed
import util.oneToNine
import util.toIndexed

typealias Histogram = Map<Int, List<Int>>

class HiddenSingle(
  private val type: HouseType,
  initialBoard: Board,
) : SolvePassBase(initialBoard) {
  override fun executeInternal(): SolveStep.Change {
    val house = initialBoard.house(type)
    val transformedHouse = house.mapIndexed(this::transform)

    return SolveStep.Change.Cells(
      Board.fromHouse(type, transformedHouse),
      initialBoard)
  }

  private fun transform(index: Int, cells: List<Cell>): List<Cell> {
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
}
