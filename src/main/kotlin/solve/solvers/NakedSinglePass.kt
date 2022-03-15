package solve.solvers

import model.board.Board
import model.board.BoardVisitor
import model.cell.Cell
import model.cell.SubCell
import model.cell.ValueCell
import mu.KotlinLogging
import solve.EachCellSolvePass
import solve.Warning
import solve.WarningService

private val logger = KotlinLogging.logger {}

class NakedSinglePass : EachCellSolvePass() {
  override fun prepare(board: Board) {
    super.prepare(board)
    logger.info { "Looking for naked-single cells" }
  }

  override fun cellVisitor(board: Board, args: BoardVisitor.Args): Board {
    val (multiCell, index) = args

    if (multiCell is Cell.Multi) {
      val valueCells =
        multiCell
          .filterIsInstance<ValueCell>()
      val notCrossedOutValueCells = valueCells.filter { it !is SubCell.CrossedOut }
      if (valueCells == notCrossedOutValueCells) {
        WarningService.raiseWarning(Warning.AllPossibleCellsAreCrossedOut)
      }
      if (notCrossedOutValueCells.size == 1) {
        logger.debug { "Found naked-single cell $index: $multiCell" }
        return board.setCell(index, Cell.Single(notCrossedOutValueCells.first().value))
      }
    }

    return board
  }
}

