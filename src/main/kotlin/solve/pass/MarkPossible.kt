package solve.pass

import model.board.Board
import model.board.BoardVisitor
import model.cell.Cell
import mu.KotlinLogging
import solve.EachCellSolvePass
import solve.SolvePassFactory
import solve.engine.SolveStep

private val logger = KotlinLogging.logger {}

class MarkPossible(initialBoard: Board) :
  EachCellSolvePass(initialBoard) {
  override fun cellVisitor(args: BoardVisitor.Args) {
    when (args.cell) {
      Cell.Empty, is Cell.Multi -> {
        val possible = args.numberSets
          .map { it.missingNumbers.toSet() }
          .reduce { acc, set -> acc.intersect(set) }
        val newCell = (if (args.cell is Cell.Empty) {
          Cell.Multi.Empty
        } else {
          args.cell as Cell.Multi
        }).setPossible(possible.toList())


        if (newCell != args.cell) {
          logger.debug { "Possible cell values changed for cell ${args.index}: $newCell" }
          board = board.setCell(args.index, newCell)
          changedIndices.add(args.index)
        }
      }
      is Cell.Single            -> Unit
    }
  }

  override fun computeChange(): SolveStep.Change.Cells {
    return SolveStep.Change.Cells(
      board,
      initialBoard,
      changedIndices)
  }

  companion object {
    val factory = SolvePassFactory(MarkPossible::class.simpleName!!) { MarkPossible(it) }
  }
}
