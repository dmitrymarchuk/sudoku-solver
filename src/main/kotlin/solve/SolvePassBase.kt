package solve

import model.board.Board
import mu.KotlinLogging
import solve.engine.SolveStep

abstract class SolvePassBase(protected val initialBoard: Board) :
  SolvePass {
  private var closed = false

  protected var board = initialBoard

  final override fun solve(): SolveStep.Change.Cells {
    if (closed) throw IllegalStateException("This solve pass has been closed!")

    return executeInternal().also { closed = true }
  }

  protected abstract fun executeInternal(): SolveStep.Change.Cells
}
