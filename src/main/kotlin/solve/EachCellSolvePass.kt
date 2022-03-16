package solve

import model.board.Board
import model.board.BoardVisitor
import mu.KotlinLogging
import solve.engine.SolveStep

private val logger = KotlinLogging.logger {}

abstract class EachCellSolvePass<T : SolveStep.Change>(protected val initialBoard: Board) :
  SolvePass {
  private var closed = false

  protected var board = initialBoard
  protected val changedIndices = mutableListOf<Int>()

  final override fun execute(): T? {
    if (closed) throw IllegalStateException("This solve pass has been closed!")

    beforeVisitStart()
    initialBoard.visitCells(this::cellVisitor)

    return computeChange()
      .takeUnless { changedIndices.isEmpty() }
      .also {
        if (it == null)
          logger.info { "No changes to the board" }
      }
      .also { this.close() }
  }

  final override fun close() {
    closed = true
  }

  protected open fun beforeVisitStart() {
    //do nothing
  }

  protected abstract fun cellVisitor(args: BoardVisitor.Args)

  protected abstract fun computeChange(): T
}
