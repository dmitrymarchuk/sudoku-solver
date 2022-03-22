package solve.engine.executors

import model.board.Board
import solve.engine.SolveStep

class BoardSolvedCheckExecutor(val executor: PassExecutor) : PassExecutorBase() {
  override fun invoke(board: Board): SolveStep.Change {
    if (board.isSolved) return SolveStep.Change.Cells.empty(board);
    return executor(board)
  }
}
