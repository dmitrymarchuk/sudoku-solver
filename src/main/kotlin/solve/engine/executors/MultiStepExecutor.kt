package solve.engine.executors

import model.board.Board
import solve.engine.SolveStep

class MultiStepExecutor(vararg val executors: PassExecutor) : PassExecutorBase() {
  override operator fun invoke(board: Board): SolveStep.Change {
    val iterator = executors.iterator()
    return SolveStep.Change.MultiStep.fromSequence(
      executors
        .firstOrNull()
        ?.invoke(board)) {
      if (iterator.hasNext()) {
        val executor = iterator.next()
        val step = executor(it.board)
        step
      } else null
    }
  }
}
