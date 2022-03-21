package solve.engine

import SolvePassFactory
import model.board.Board

interface PassExecutor {
  operator fun invoke(board: Board): SolveStep.Change
}

class BoardSolvedCheckExecutor(private val executor: PassExecutor) : PassExecutor {
  override fun invoke(board: Board): SolveStep.Change {
    if (board.isSolved) return SolveStep.Change.Cells.empty(board);
    return executor(board)
  }
}

class SinglePassExecutor(private val factory: SolvePassFactory) : PassExecutor {
  override operator fun invoke(board: Board) = factory(board).solve()
}

class MultiStepExecutor(private vararg val executors: PassExecutor) : PassExecutor {
  override operator fun invoke(board: Board): SolveStep.Change {
    if (executors.isEmpty()) return SolveStep.Change.Cells(board, board, emptyList())

    return SolveStep.Change.MultiStep.fromSequence {
      var mutableBoard = board
      executors.forEach { executor ->
        val step = executor(mutableBoard)
        yield(step)
        mutableBoard = step.board
      }
    }
  }
}

class CombinedStepExecutor(private val multiStepExecutor: MultiStepExecutor) :
  PassExecutor {
  override operator fun invoke(board: Board): SolveStep.Change =
    multiStepExecutor(board).let {
      when (it) {
        is SolveStep.Change.Cells     -> it
        is SolveStep.Change.MultiStep -> it.combined()
      }
    }
}

class ExhaustingExecutor(private val executor: PassExecutor) : PassExecutor {
  override operator fun invoke(board: Board): SolveStep.Change.MultiStep {
    return SolveStep.Change.MultiStep.fromSequence {
      var mutableBoard = board
      do {
        if (mutableBoard.isSolved) break

        val step = executor(mutableBoard)
        mutableBoard = step.board
        if (step.noChanges)
          break

      } while (true)
    }
  }
}
