package solve

import model.board.Board
import mu.KotlinLogging
import solve.engine.SolveStep

private val logger = KotlinLogging.logger {}

abstract class SolvePassBase(protected val initialBoard: Board) :
  SolvePass {
  private var closed = false

  protected var board = initialBoard

  final override fun execute(): SolveStep.Change {
    if (closed) throw IllegalStateException("This solve pass has been closed!")

    return executeInternal()
      .also {
        if (it.noChanges)
          logger.info { "No changes to the board" }
      }
      .also { closed = true }
  }

  protected abstract fun executeInternal(): SolveStep.Change
}
