package solve.pass

import model.board.Board
import model.board.BoardVisitor
import model.cell.Cell
import mu.KotlinLogging
import solve.EachCellSolvePass
import solve.engine.SolveStep

private val logger = KotlinLogging.logger {}

class MarkPossible(initialBoard: Board) :
  EachCellSolvePass<SolveStep.Change.NonValueCellsChanged>(initialBoard) {
  override fun beforeVisitStart() {
    logger.info { "Calculating all possible cell values" }
  }

  override fun cellVisitor(args: BoardVisitor.Args) {
    val (cell, index, row, col, house) = args

    when (cell) {
      Cell.Empty, is Cell.Multi -> {
        val possible = listOf(row, col, house)
          .map { it.missingNumbers.toSet() }
          .reduce { acc, set -> acc.intersect(set) }
        val newCell = (if (cell is Cell.Empty) {
          Cell.Multi.Empty
        } else {
          cell as Cell.Multi
        }).setPossible(possible.toList())


        if (newCell != cell) {
          logger.debug { "Possible cell values changed for cell $index: $newCell" }
          board = board.setCell(index, newCell)
          changedIndices.add(index)
        }
      }
      is Cell.Single            -> Unit
    }
  }

  override fun computeChange(): SolveStep.Change.NonValueCellsChanged {
    return SolveStep.Change.NonValueCellsChanged(
      board,
      initialBoard,
      changedIndices)
  }
}
