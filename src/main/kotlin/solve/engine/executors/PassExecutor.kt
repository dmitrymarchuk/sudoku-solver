package solve.engine.executors

import SolvePassFactory
import model.board.Board
import solve.engine.SolveStep

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
  override operator fun invoke(board: Board): SolveStep.Change {
    return SolveStep.Change.MultiStep.fromSequence(executor(board)) { step ->
      if (step.noChanges || step.board.isSolved)
        null
      else
        executor(step.board)
    }
  }
}
