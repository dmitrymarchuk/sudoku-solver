package solve.engine.executors

import solve.SolvePassFactory
import model.board.Board
import model.cell.Cell
import mu.KotlinLogging
import solve.engine.SolveStep

private val logger = KotlinLogging.logger {}

class SinglePassExecutor(
  private val factory: SolvePassFactory,
) : PassExecutorBase() {
  override operator fun invoke(board: Board): SolveStep.Change.Cells {
    if (board.isSolved) return SolveStep.Change.Cells.empty(board)

    logger.info { "Begin ${factory.name}" }

    return factory(board).solve().also {
      val str = StringBuilder("End   ${factory.name}. ")
      if (it.board.isSolved) str.append("Board solved!")
      else if (it.noChanges) str.append("No changes")
      else str.append("Changed indices: ${it.changedIndices}")
      logger.info { str }
    }
  }

  override fun toString() = factory.name

  companion object {
    fun SolvePassFactory.toExecutor() =
      SinglePassExecutor(this)
  }
}
