package solve.engine.executors

import model.board.Board
import solve.engine.SolveStep

class ExhaustingExecutor(val executor: PassExecutor) : PassExecutorBase() {
  override operator fun invoke(board: Board): SolveStep.Change {
    return SolveStep.Change.MultiStep.fromSequence(executor(board)) { step ->
      if (step.noChanges || step.board.isSolved)
        null
      else
        executor(step.board)
    }
  }
}
