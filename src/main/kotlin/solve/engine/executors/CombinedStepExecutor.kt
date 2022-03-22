package solve.engine.executors

import model.board.Board
import solve.engine.SolveStep

class CombinedStepExecutor(val multiStepExecutor: MultiStepExecutor) :
  PassExecutorBase() {
  override operator fun invoke(board: Board): SolveStep.Change =
    multiStepExecutor(board).let {
      when (it) {
        is SolveStep.Change.Cells     -> it
        is SolveStep.Change.MultiStep -> it.combined()
      }
    }
}
