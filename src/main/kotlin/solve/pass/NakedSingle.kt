package solve.pass

import model.board.Board
import model.board.BoardVisitor
import model.cell.Cell
import model.cell.SubCell
import model.cell.ValueCell
import mu.KotlinLogging
import solve.EachCellSolvePass
import solve.engine.SolveStep

private val logger = KotlinLogging.logger {}

class NakedSingle(initialBoard: Board) :
  EachCellSolvePass(initialBoard) {
  override fun beforeVisitStart() {
    logger.info { "Looking for naked-single cells" }
  }

  private var multiCellPresent = false

  override fun cellVisitor(args: BoardVisitor.Args) {
    val (multiCell, index) = args

    if (multiCell is Cell.Multi) {
      multiCellPresent = true
      val valueCells = multiCell.filterIsInstance<ValueCell>().toSet()
      val crossedOutCells = valueCells.filterIsInstance<SubCell.CrossedOut>().toSet()
      val notCrossedOutCells = valueCells - crossedOutCells
      if (valueCells == crossedOutCells) {
        logger.warn { "All possible cells are crossed out at index ${index}!" }
      }
      if (notCrossedOutCells.size == 1) {
        logger.debug { "Found naked-single cell $index: $multiCell" }
        changedIndices.add(index)
        board = board.setCell(index, Cell.Single(notCrossedOutCells.first().value))
      }
    }
  }

  override fun computeChange(): SolveStep.Change.Cells {
    if (!multiCellPresent) {
      logger.warn { "Naked-single pass executed when no Cell.Multi cells were present." }
    }
    return SolveStep.Change.Cells(board, initialBoard, changedIndices)
  }
}
