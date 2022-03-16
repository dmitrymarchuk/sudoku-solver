package solve.engine

import model.board.Board
import mu.KotlinLogging
import solve.EachCellSolvePass
import solve.SolvePass
import solve.pass.MarkPossible
import solve.pass.NakedSingle

private val logger = KotlinLogging.logger {}

class SolveEngine(initialBoard: Board) {
  private var board = initialBoard

  fun getSolveSequence(): Sequence<SolveStep> =
    sequence {
      solve()
    }

  private suspend fun SequenceScope<SolveStep>.solve() {
    yield(SolveStep.Initial(board))
    if (board.isSolved) return

    val initialMarkStep = eachCellPassYield(::MarkPossible)
    if (initialMarkStep == null) {
      logger.error {
        "No changes after initial mark-possible pass. " +
            "Highly likely that sudoku is incorrect!"
      }
      return
    }

    do {
      if (!multiStepYield(::NakedSingle, ::MarkPossible).isComplete)
        break
    } while (true)
  }

  @Suppress("UNCHECKED_CAST")
  private suspend fun <
      K : SolveStep.Change,
      T : EachCellSolvePass<K>,
      > SequenceScope<SolveStep>.eachCellPassYield(
    passFactory: (Board) -> T,
  ): K? =
    passYield(passFactory) as K?

  @Suppress("UNCHECKED_CAST")
  private fun <
      K : SolveStep.Change,
      T : EachCellSolvePass<K>,
      > eachCellPass(
    passFactory: (Board) -> T,
  ): K? =
    pass(passFactory) as K?

  private fun pass(
    passFactory: (Board) -> SolvePass,
  ): SolveStep.Change? =
    passFactory(board).execute().also { step ->
      if (step != null) {
        board = step.board
      }
    }

  private suspend fun SequenceScope<SolveStep>.passYield(
    passFactory: (Board) -> SolvePass,
  ): SolveStep.Change? =
    pass(passFactory).also { step ->
      yield(step ?: SolveStep.NoChange(board))
    }

  private fun multiStep(
    vararg factories: (Board) -> SolvePass,
  ) = SolveStep.Change.MultiStep(
    factories
      .drop(1)
      .runningFold(pass(factories.first())) { lastStep, factory ->
        pass(factory).takeIf { lastStep != null }
      }.filterNotNull())
    .let {
      MultiStepResult(factories.size == it.size, it)
    }

  private suspend fun SequenceScope<SolveStep>.multiStepYield(
    vararg factories: (Board) -> SolvePass,
  ) = multiStep(*factories).also {
    yield(it.multiStep)
  }

  private data class MultiStepResult(
    val isComplete: Boolean,
    val multiStep: SolveStep.Change.MultiStep,
  )
}

