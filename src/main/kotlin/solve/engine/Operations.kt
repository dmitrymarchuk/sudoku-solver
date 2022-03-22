package solve.engine

import solve.SolvePassFactory
import solve.engine.executors.BoardSolvedCheckExecutor
import solve.engine.executors.CombinedStepExecutor
import solve.engine.executors.ExhaustingExecutor
import solve.engine.executors.MultiStepExecutor
import solve.engine.executors.PassExecutor
import solve.engine.executors.SinglePassExecutor

fun SolvePassFactory.checkSolved() = this.toExecutor()

fun PassExecutor.checkSolved() = BoardSolvedCheckExecutor(this)

fun SolvePassFactory.toExecutor() =
  SinglePassExecutor(this).checkSolved()

fun PassExecutor.exhausting() = ExhaustingExecutor(this)

infix fun SolvePassFactory.then(other: PassExecutor): MultiStepExecutor {
  return MultiStepExecutor(this.toExecutor(), other)
}

infix fun SolvePassFactory.combined(other: PassExecutor): CombinedStepExecutor {
  return CombinedStepExecutor(this then other)
}

infix fun SolvePassFactory.then(other: SolvePassFactory): MultiStepExecutor {
  return MultiStepExecutor(this.toExecutor(), other.toExecutor())
}

infix fun SolvePassFactory.combined(other: SolvePassFactory): CombinedStepExecutor {
  return CombinedStepExecutor(this then other)
}

infix fun PassExecutor.then(other: SolvePassFactory): MultiStepExecutor {
  return MultiStepExecutor(this, other.toExecutor())
}

infix fun PassExecutor.combined(other: SolvePassFactory): CombinedStepExecutor {
  return CombinedStepExecutor(this then other)
}

infix fun PassExecutor.then(other: PassExecutor): MultiStepExecutor {
  return MultiStepExecutor(this, other)
}

infix fun PassExecutor.combined(other: PassExecutor): CombinedStepExecutor {
  return CombinedStepExecutor(this then other)
}
