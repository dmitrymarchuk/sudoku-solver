package solve

import model.board.Board
import model.board.BoardVisitor

abstract class EachCellSolvePass : SolvePass {
  final override fun transform(board: Board): Board {
    return board.visitCells(this::cellVisitor)
  }

  protected open fun prepare(board: Board) {
    //do nothing
  }

  protected abstract fun cellVisitor(board: Board, args: BoardVisitor.Args): Board
}
