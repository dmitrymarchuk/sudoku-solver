package solve.engine.executors

import model.board.Board
import solve.SolvePassFactory
import solve.engine.SolveStep
import solve.engine.executors.SinglePassExecutor.Companion.toExecutor

class MultiStepExecutor private constructor(
  val isCombined: Boolean,
  vararg val executors: PassExecutor,
) : PassExecutorBase() {

  override operator fun invoke(board: Board): SolveStep.Change {
    val iterator = executors.iterator()
    return SolveStep.Change.MultiStep.fromSequence(
      iterator.next().takeIf { iterator.hasNext() }?.invoke(board)
    ) {
      if (isCombined) {
        if (it.noChanges) return@fromSequence null
      }
      if (iterator.hasNext()) {
        val executor = iterator.next()
        val step = executor(it.board)
        step
      } else null
    }
  }

  companion object {
    fun multiple(vararg executors: PassExecutor) = MultiStepExecutor(false, *executors)
    fun multipleCombined(vararg executors: PassExecutor) =
      MultiStepExecutor(true, *executors)

    infix fun SolvePassFactory.combined(other: PassExecutor) =
      MultiStepExecutor(true, this.toExecutor(), other)

    infix fun SolvePassFactory.combined(other: SolvePassFactory) =
      MultiStepExecutor(true, this.toExecutor(), other.toExecutor())

    infix fun PassExecutor.combined(other: SolvePassFactory) =
      MultiStepExecutor(true, this, other.toExecutor())

    infix fun PassExecutor.combined(other: PassExecutor) =
      MultiStepExecutor(true, this, other)

    infix fun SolvePassFactory.then(other: PassExecutor) =
      MultiStepExecutor(false, this.toExecutor(), other)

    infix fun SolvePassFactory.then(other: SolvePassFactory) =
      MultiStepExecutor(false, this.toExecutor(), other.toExecutor())

    infix fun PassExecutor.then(other: SolvePassFactory) =
      MultiStepExecutor(false, this, other.toExecutor())

    infix fun PassExecutor.then(other: PassExecutor) =
      MultiStepExecutor(false, this, other)
  }
}
