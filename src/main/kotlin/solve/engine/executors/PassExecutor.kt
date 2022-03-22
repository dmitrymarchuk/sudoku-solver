package solve.engine.executors

import model.board.Board
import solve.engine.SolveStep

sealed interface PassExecutor {
  operator fun invoke(board: Board): SolveStep.Change
}
