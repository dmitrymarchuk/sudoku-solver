package solve

import model.board.Board
import model.board.BoardVisitor
import solve.engine.SolveStep

abstract class EachCellSolvePass(initialBoard: Board) : SolvePassBase(initialBoard) {
  protected val changedIndices = mutableListOf<Int>()

  protected open fun beforeVisitStart() {
    //do nothing
  }

  final override fun executeInternal(): SolveStep.Change.Cells {
    beforeVisitStart()
    initialBoard.visitCells(this::cellVisitor)

    return computeChange()
  }

  protected abstract fun cellVisitor(args: BoardVisitor.Args)

  protected abstract fun computeChange(): SolveStep.Change.Cells
}
