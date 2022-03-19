package solve.engine

import model.board.Board
import mu.KotlinLogging
import solve.SolvePass
import solve.pass.MarkPossible
import solve.pass.NakedSingle

private val logger = KotlinLogging.logger {}

class SolveEngine(initialBoard: Board) {
  private var board = initialBoard

  fun getSolveSequence(): Sequence<SolveStep> = sequence {
    solve()
  }

  private suspend fun SequenceScope<SolveStep>.solve() {
    yield(SolveStep.Initial(board))
    if (board.isSolved) return

    val initialMarkStep = passYield(::MarkPossible)
    if (initialMarkStep.noChanges) {
      logger.error {
        "No changes after initial mark-possible pass. " + "Highly likely that sudoku is incorrect!"
      }
      return
    }

    do {
      if (multiStepYield(
          ::NakedSingle,
          ::MarkPossible
        ).let { it.noChanges || it.board.isSolved }
      ) break
    } while (true)
  }

  private fun pass(
    passFactory: (Board) -> SolvePass,
  ): SolveStep.Change = passFactory(board).execute().also { step ->
    if (!step.noChanges) {
      board = step.board
    }
  }

  private suspend fun SequenceScope<SolveStep>.passYield(
    passFactory: (Board) -> SolvePass,
  ): SolveStep.Change = pass(passFactory).also { step ->
    yield(step)
  }

  private fun multiStep(
    vararg factories: (Board) -> SolvePass,
  ) = SolveStep.Change
    .MultiStep(factories.drop(1).runningFold(pass(factories.first())) { lastStep, factory ->
      if (lastStep.noChanges || lastStep.board.isSolved) lastStep
      else pass(factory)
    })

  private suspend fun SequenceScope<SolveStep>.multiStepYield(
    vararg factories: (Board) -> SolvePass,
  ) = multiStep(*factories).also {
    yield(it)
  }
}
